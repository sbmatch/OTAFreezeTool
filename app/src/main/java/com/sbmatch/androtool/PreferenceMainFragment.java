package com.sbmatch.androtool;

import android.app.admin.DevicePolicyManager;
import android.app.admin.FreezePeriod;
import android.app.admin.IDevicePolicyManager;
import android.app.admin.SystemUpdatePolicy;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.widget.AppHeaderPreference;
import com.android.settingslib.widget.AppSwitchPreference;
import com.android.settingslib.widget.AppPreference;
import com.android.settingslib.widget.CardPreference;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.SettingsBasePreferenceFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.rosan.dhizuku.api.Dhizuku;
import com.rosan.dhizuku.api.DhizukuRequestPermissionListener;
import com.sbmatch.androtool.utils.ExecutorUtils;

import org.luckypray.dexkit.DexKitBridge;
import org.luckypray.dexkit.query.FindMethod;
import org.luckypray.dexkit.query.matchers.MethodMatcher;
import org.luckypray.dexkit.result.MethodDataList;

import java.lang.reflect.Field;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Collections;
import java.util.List;

import dalvik.system.PathClassLoader;

/**
 * 入口页： 1. Device Owner（Dhizuku）授权状态卡片 2. 系统更新应用信息（大图标/名称/包名） 3. 冻结系统更新开关（DhizukuBinderWrapper 包装
 * device_policy，以 Device Owner 权限调用）
 */
public class PreferenceMainFragment extends SettingsBasePreferenceFragment {

    private static final String TAG = "PreferenceMain";

    private CardPreference ownerCard;
    private AppHeaderPreference updaterHeader;
    private AppSwitchPreference freezeSwitch;

    private ApplicationInfo updaterApp;
    private String updaterVersion;
    private boolean dhizukuReady;
    private boolean permissionGranted;
    private boolean updaterSupported;

    /** 等待授权完成后要继续执行的开关操作 */
    private volatile Boolean pendingFreezeToggle;

    private boolean receiverRegistered;

    /** 与 Android Settings 一样，监听系统更新策略变化广播，而不是轮询。 */
    private final BroadcastReceiver policyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DevicePolicyManager.ACTION_SYSTEM_UPDATE_POLICY_CHANGED
                    .equals(intent.getAction())) {
                refreshFreezeState();
            }
        }
    };

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = requireContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
        setPreferenceScreen(screen);

        ownerCard = new CardPreference(context);
        ownerCard.setKey("device_owner_status");
        ownerCard.setIcon(R.drawable.ic_md_verified_user);
        ownerCard.setSelectable(false);
        screen.addPreference(ownerCard);

        updaterHeader = new AppHeaderPreference(context);
        updaterHeader.setKey("updater_header");
        updaterHeader.setTitle("正在查找系统更新应用…");
        updaterHeader.setIsInstantApp(false);
        screen.addPreference(updaterHeader);

        freezeSwitch = new AppSwitchPreference(context);
        freezeSwitch.setKey("freeze_system_updates");
        freezeSwitch.setTitle("冻结系统更新");
        freezeSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
            if (!(newValue instanceof Boolean)) {
                return false;
            }
            boolean enable = (Boolean) newValue;
            if (!hasDhizukuPermission()) {
                // 记住这次操作，拿到授权后继续执行
                pendingFreezeToggle = enable;
                requestPermissionIfNeeded();
                return false;
            }
            return toggleFreeze(enable);
        });
        screen.addPreference(freezeSwitch);

        AppPreference aboutPreference = new AppPreference(context);
        aboutPreference.setKey("about_perf");
        aboutPreference.setTitle("关于");
        //aboutPreference.setIcon(R.drawable.ic_md_info);
        //appPreference.setWidgetLayoutResource(R.layout.widget_preference_arrow);
        aboutPreference.setOnPreferenceClickListener(p -> {
            openAbout();
            return true;
        });
        screen.addPreference(aboutPreference);

        FooterPreference footer = new FooterPreference(context);
        footer.setKey("freeze_period_footer");
        footer.setTitle("");
        footer.setIcon(R.drawable.ic_md_alert_circle_outline);
        footer.setIconVisibility(android.view.View.VISIBLE);
        footer.setLearnMoreText("了解更新冻结期详情");
        footer.setLearnMoreAction(view -> openFreezePeriodDoc());
        screen.addPreference(footer);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fragment 切换动画期间两个页面会重叠，给页面一个不透明背景，避免文字互相透出。
        view.setBackgroundResource(R.drawable.bg_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        CollapsingToolbarLayout collapsingToolbar =
                requireActivity().findViewById(R.id.collapsingToolbarLayout);
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle(getString(R.string.app_name));
        }

        // 主页没有返回箭头（从关于页返回时关掉 Home-as-Up）
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        detectAsync();
        registerPolicyReceiver();
    }

    /** 打开“关于”页（全部由 SettingsLib 的 expressive 组件搭建）。 */
    private void openAbout() {
        if (!isAdded()) {
            return;
        }
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(R.id.preferenceContainer, new AboutFragment())
                .addToBackStack("about")
                .commit();
    }

    @Override
    public void onPause() {
        unregisterPolicyReceiver();
        super.onPause();
    }

    private boolean hasDhizukuPermission() {
        try {
            return Dhizuku.isPermissionGranted();
        } catch (Throwable t) {
            return false;
        }
    }

    private void registerPolicyReceiver() {
        if (receiverRegistered || !isAdded()) {
            return;
        }
        try {
            IntentFilter filter = new IntentFilter(
            DevicePolicyManager.ACTION_SYSTEM_UPDATE_POLICY_CHANGED);
            ContextCompat.registerReceiver(requireContext(), policyReceiver, filter,
                    ContextCompat.RECEIVER_NOT_EXPORTED);
            receiverRegistered = true;
        } catch (Throwable t) {
            Log.w(TAG, "register policy receiver failed", t);
        }
    }

    private void unregisterPolicyReceiver() {
        if (!receiverRegistered || !isAdded()) {
            return;
        }
        try {
            requireContext().unregisterReceiver(policyReceiver);
        } catch (Throwable ignored) {
        }
        receiverRegistered = false;
    }

    private void detectAsync() {
        if (!isAdded()) {
            return;
        }
        Context appContext = requireContext().getApplicationContext();
        ExecutorUtils.submit(() -> {
            boolean init = false;
            boolean granted = false;
            try {
                init = Dhizuku.init(appContext);
                if (init) {
                    granted = Dhizuku.isPermissionGranted();
                }
            } catch (Throwable t) {
                Log.w(TAG, "Dhizuku init/permission check failed", t);
            }

            ApplicationInfo app = findUpdaterApp(appContext);
            String version = app == null ? null : getVersionName(appContext, app.packageName);
            boolean supported = app != null && checkFreezeApi(app);

            final boolean initResult = init;
            final boolean grantedResult = granted;
            final ApplicationInfo appResult = app;
            final String versionResult = version;
            final boolean supportedResult = supported;

            if (!isAdded()) {
                return;
            }
            requireActivity().runOnUiThread(() -> applyDetection(
            initResult, grantedResult, appResult, versionResult, supportedResult));
        });
    }

    private void applyDetection(boolean init, boolean granted, ApplicationInfo app,
            String version, boolean supported) {
        if (!isAdded()) {
            return;
        }
        dhizukuReady = init;
        permissionGranted = granted;
        updaterApp = app;
        updaterVersion = version;
        updaterSupported = supported;

        if (app != null) {
            PackageManager pm = requireContext().getPackageManager();
            updaterHeader.setTitle(pm.getApplicationLabel(app));
            String summary = app.packageName
                    + (TextUtils.isEmpty(version) ? "" : " | " + version);
            updaterHeader.setSummary(summary);
            try {
                updaterHeader.setIcon(app.loadIcon(pm));
            } catch (Throwable ignored) {
            }
        } else {
            updaterHeader.setTitle("未找到系统更新应用");
            updaterHeader.setSummary(null);
            updaterHeader.setIcon(R.drawable.rounded_view);
        }

        // 标题直接反映 Dhizuku 授权状态，不再设置描述
        ownerCard.setTitle(granted ? "已授权" : "未授权");
        ownerCard.setIcon(granted
                ? R.drawable.ic_md_verified_user
                : R.drawable.ic_md_emoticon_dead_outline);

        if (!init) {
            // 不主动禁用：点击开关时由监听器触发 Dhizuku 初始化/权限申请
            freezeSwitch.setSummary("需要 Dhizuku");
        } else if (app == null) {
            freezeSwitch.setEnabled(false);
            freezeSwitch.setSummary("未找到系统更新应用");
        } else if (!supported) {
            freezeSwitch.setEnabled(false);
            freezeSwitch.setSummary("目标应用不支持冻结");
        } else {
            freezeSwitch.setEnabled(true);
            refreshFreezeState();
        }
    }

    private boolean toggleFreeze(boolean enable) {
        try {
            DevicePolicyManager dpm = getWrappedDevicePolicyManager();
            if (dpm == null) {
                freezeSwitch.setSummary("无法获取 device_policy 服务");
                return false;
            }
            ComponentName admin = Dhizuku.getOwnerComponent(requireContext());
            if (admin == null) {
                freezeSwitch.setSummary("未找到 Device Owner");
                return false;
            }
            if (enable) {
                SystemUpdatePolicy policy = SystemUpdatePolicy.createPostponeInstallPolicy()
                        .setFreezePeriods(buildMaxFreezePeriods());

                dpm.setSystemUpdatePolicy(admin, policy);
            } else {
                dpm.setSystemUpdatePolicy(admin, null);
                IDevicePolicyManager raw = getWrappedIDevicePolicyManager();
                if (raw != null) {
                    try {
                        raw.clearSystemUpdatePolicyFreezePeriodRecord();
                    } catch (Throwable ignored) {
                    }
                }
            }
            refreshFreezeState();
            return true;
        } catch (Throwable t) {
            Log.w(TAG, "toggle freeze failed", t);
            freezeSwitch.setSummary("设置失败：" + t.getMessage());
            return false;
        }
    }

    /** 根据当前 SystemUpdatePolicy 是否存在冻结期更新开关和描述。 存在冻结期时显示下一次结束日期。 */
    private void refreshFreezeState() {
        FreezePeriod period = getActiveFreezePeriod();
        boolean enabled = period != null;
        if (freezeSwitch.isChecked() != enabled) {
            freezeSwitch.setChecked(enabled);
        }
        String summary = enabled ? "到期时间：" + freezeExpiry(period) : "未设置冻结";
        if (!TextUtils.equals(freezeSwitch.getSummary(), summary)) {
            freezeSwitch.setSummary(summary);
        }
    }

    private FreezePeriod getActiveFreezePeriod() {
        try {
            DevicePolicyManager dpm = getWrappedDevicePolicyManager();
            if (dpm == null) {
                return null;
            }
            SystemUpdatePolicy policy = dpm.getSystemUpdatePolicy();
            if (policy == null
                    || policy.getFreezePeriods() == null
                    || policy.getFreezePeriods().isEmpty()) {
                return null;
            }
            return policy.getFreezePeriods().get(0);
        } catch (Throwable t) {
            Log.w(TAG, "read freeze state failed", t);
            return null;
        }
    }

    /** 冻结期从今天开始，长度为 FREEZE_PERIOD_MAX_LENGTH - 1， 若跨年则截断到 SENTINEL_YEAR 的 12 月 31 日。 */
    @android.annotation.SuppressLint("BlockedPrivateApi")
    private static List<FreezePeriod> buildMaxFreezePeriods() {
        int sentinelYear = 2000;
        int maxLength = 90;
        try {
            Field field = FreezePeriod.class.getDeclaredField("SENTINEL_YEAR");
            field.setAccessible(true);
            sentinelYear = field.getInt(null);
        } catch (Throwable ignored) {
        }
        try {
            Field field = SystemUpdatePolicy.class
                    .getDeclaredField("FREEZE_PERIOD_MAX_LENGTH");
            field.setAccessible(true);
            maxLength = field.getInt(null);
        } catch (Throwable ignored) {
        }

        LocalDate today = LocalDate.now();
        LocalDate startSentinel = LocalDate.of(sentinelYear,
                today.getMonth(), today.getDayOfMonth());
        LocalDate endSentinel = startSentinel.plusDays(maxLength - 1L);
        if (endSentinel.getYear() > sentinelYear) {
            endSentinel = LocalDate.of(sentinelYear, 12, 31);
        }

        FreezePeriod period = new FreezePeriod(
        MonthDay.of(today.getMonth(), today.getDayOfMonth()),
        MonthDay.of(endSentinel.getMonth(), endSentinel.getDayOfMonth()));
        return Collections.singletonList(period);
    }

    /** 冻结期按年循环，计算结束 MonthDay 的下一次日期（年-月-日）。 */
    private static String freezeExpiry(FreezePeriod period) {
        MonthDay end = period.getEnd();
        LocalDate today = LocalDate.now();
        LocalDate expiry;
        try {
            expiry = end.atYear(today.getYear());
            if (expiry.isBefore(today)) {
                expiry = end.atYear(today.getYear() + 1);
            }
        } catch (DateTimeException e) {
            // 2 月 29 日等非每年存在的日期，退回 2 月 28 日
            expiry = LocalDate.of(today.getYear(), 2, 28);
            if (expiry.isBefore(today)) {
                expiry = LocalDate.of(today.getYear() + 1, 2, 28);
            }
        }
        return expiry.toString();
    }

    /**
     * 从 Device Owner（Dhizuku）的 Context 取 DevicePolicyManager， 再把它的 mService 替换成
     * DhizukuBinderWrapper 包装后的 binder。
     */
    @android.annotation.SuppressLint("BlockedPrivateApi")
    private DevicePolicyManager getWrappedDevicePolicyManager() {
        if (!isAdded()) {
            return null;
        }
        Context context = requireContext();
        ComponentName admin = Dhizuku.getOwnerComponent(context);
        if (admin == null) {
            return null;
        }
        try {
            Context ownerContext = context.createPackageContext(
                    admin.getPackageName(), Context.CONTEXT_IGNORE_SECURITY);
            DevicePolicyManager dpm = (DevicePolicyManager) ownerContext
                            .getSystemService(Context.DEVICE_POLICY_SERVICE);
            IBinder binder = ServiceManager.getService(Context.DEVICE_POLICY_SERVICE);
            if (dpm == null || binder == null) {
                return null;
            }
            IDevicePolicyManager wrapped = IDevicePolicyManager.Stub
                    .asInterface(Dhizuku.binderWrapper(binder));
            Field field = DevicePolicyManager.class.getDeclaredField("mService");
            field.setAccessible(true);
            field.set(dpm, wrapped);
            return dpm;
        } catch (Throwable t) {
            Log.w(TAG, "wrap DevicePolicyManager failed", t);
            return null;
        }
    }

    private IDevicePolicyManager getWrappedIDevicePolicyManager() {
        IBinder binder = ServiceManager.getService(Context.DEVICE_POLICY_SERVICE);
        if (binder == null) {
            return null;
        }
        return IDevicePolicyManager.Stub.asInterface(Dhizuku.binderWrapper(binder));
    }

    private void openFreezePeriodDoc() {
        if (!isAdded()) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
            Uri.parse("https://android-docs.cn/work/dpc/system-updates#freeze-periods"));
            startActivity(intent);
        } catch (Throwable t) {
            Log.w(TAG, "open doc failed", t);
        }
    }

    private void requestPermissionIfNeeded() {
        if (!isAdded()) {
            return;
        }
        if (hasDhizukuPermission()) {
            detectAsync();
            completePendingToggle();
            return;
        }

        Context appContext = requireContext().getApplicationContext();
        ExecutorUtils.submit(() -> {
            boolean init = false;
            try {
                init = Dhizuku.init(appContext);
            } catch (Throwable t) {
                Log.w(TAG, "Dhizuku.init failed", t);
            }
            if (!init) {
                pendingFreezeToggle = null;
                return;
            }
            if (!isAdded()) {
                return;
            }
            requireActivity().runOnUiThread(() -> {
                if (hasDhizukuPermission()) {
                    detectAsync();
                    completePendingToggle();
                    return;
                }
                try {
                    Dhizuku.requestPermission(new DhizukuRequestPermissionListener() {
                        @Override
                        public void onRequestPermission(int grantResult) {
                            if (!isAdded()) {
                                return;
                            }
                            requireActivity().runOnUiThread(() -> {
                                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                                    // 立即更新卡片，避免等待异步检测
                                    ownerCard.setTitle("DeviceOwner 已授权");
                                    ownerCard.setIcon(R.drawable.ic_md_verified_user);
                                    detectAsync();
                                    completePendingToggle();
                                } else {
                                    pendingFreezeToggle = null;
                                }
                            });
                        }
                    });
                } catch (Throwable t) {
                    Log.w(TAG, "request Dhizuku permission failed", t);
                    pendingFreezeToggle = null;
                }
            });
        });
    }

    /** 授权成功后继续执行用户之前点击开关想做的操作。 */
    private void completePendingToggle() {
        Boolean pending = pendingFreezeToggle;
        pendingFreezeToggle = null;
        if (pending != null && isAdded()) {
            toggleFreeze(pending);
        }
    }

    /** 除 "*.updater" 之外的已知系统更新应用包名 */
    private static final String[] EXTRA_UPDATER_PACKAGES = {
            "com.oplus.ota",
            "com.hihonor.ouc",
    };

    private static boolean isUpdaterPackage(String pkg) {
        // 必须是 com 开头且以 .updater 结尾，或命中已知包名白名单
        if (pkg.startsWith("com") && pkg.endsWith(".updater")) {
            return true;
        }
        for (String known : EXTRA_UPDATER_PACKAGES) {
            if (known.equals(pkg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找系统更新应用： 1. 查询声明了 ACTION_MAIN + CATEGORY_DEFAULT 的 Activity 2. 包名以 ".updater"
     * 结尾，或命中已知包名白名单（如 com.oplus.ota） 3. 必须是系统应用
     */
    private static ApplicationInfo findUpdaterApp(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        if (activities == null) {
            return null;
        }
        for (ResolveInfo info : activities) {
            if (info.activityInfo == null || info.activityInfo.applicationInfo == null) {
                continue;
            }
            String pkg = info.activityInfo.packageName;
            if (pkg == null || !isUpdaterPackage(pkg)) {
                continue;
            }
            ApplicationInfo app = info.activityInfo.applicationInfo;
            boolean system = (app.flags & ApplicationInfo.FLAG_SYSTEM) != 0
                    || (app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0;
            if (system) {
                return app;
            }
        }
        return null;
    }

    private static String getVersionName(Context context, String packageName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return info.versionName;
        } catch (Throwable t) {
            return null;
        }
    }

    /** 用 DexKit 扫描目标应用，检查是否调用了 android.app.admin.SystemUpdatePolicy#getFreezePeriods()。 */
    private static boolean checkFreezeApi(ApplicationInfo app) {
        // 创建目标应用的 ClassLoader，DexKit 通过它扫描目标 APK 的 dex
        PathClassLoader updaterLoader = null;
        try {
            updaterLoader = new PathClassLoader(app.sourceDir, app.nativeLibraryDir,
            ClassLoader.getSystemClassLoader());
        } catch (Throwable t) {
            Log.w(TAG, "create updater classloader failed", t);
        }

        try {
            System.loadLibrary("dexkit");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "libdexkit unavailable", e);
            return false;
        }

        try (DexKitBridge bridge = updaterLoader != null
                ? DexKitBridge.create(updaterLoader, false)
                : DexKitBridge.create(app.sourceDir)) {
            if (bridge == null) {
                return false;
            }
            // 支持冻结期，或至少会读取 policyType（Honor 等只认 policyType）
            return hasSystemUpdatePolicyCall(bridge,
                    "Landroid/app/admin/SystemUpdatePolicy;->"
                            + "getFreezePeriods()Ljava/util/List;")
                    || hasSystemUpdatePolicyCall(bridge,
                    "Landroid/app/admin/SystemUpdatePolicy;->getPolicyType()I");
        } catch (Throwable t) {
            Log.w(TAG, "DexKit check failed", t);
            return false;
        }
    }

    private static boolean hasSystemUpdatePolicyCall(DexKitBridge bridge,
            String methodDescriptor) {
        MethodDataList methods = bridge.findMethod(FindMethod.create().matcher(
                MethodMatcher.create().addInvoke(methodDescriptor)));
        return methods != null && !methods.isEmpty();
    }
}
