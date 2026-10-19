package com.sbmatch.androtool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Optional;
import java.util.Arrays;
import java.util.HashSet;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.util.JumpParameter;
import com.kongzue.baseframework.util.Permission;
import com.kongzue.baseframework.util.OnActivityPermissionCallBack;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.content.res.AssetManager;
import android.util.*;
import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settingslib.widget.*;
import com.sbmatch.androtool.utils.*;
import miui.util.*;
import android.miui.*;
import android.view.View.OnClickListener;
// import rikka.shizuku.SystemServiceHelper;

public class Main extends BaseActivity implements OnClickListener {

    Toolbar toolbar;
    LinearLayout contentLayout;
    AppViewModel appViewModel;
    IInterface vendor_perfservice;
    final StringBuilder output = new StringBuilder();

    public Main() {
        setLayout(R.layout.main);
        setDarkStatusBarTheme(true);
        setNavigationBarBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    // 此处用于绑定布局、View初始化等操作
    public void initViews() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

        // 让 Activity 先画出 AppBar/背景，再创建较重的 Preference 列表，
        // 避免冷启动时首帧被列表构建拖住。
        findViewById(R.id.preferenceContainer).post(() -> {
            if (getSupportFragmentManager().findFragmentById(R.id.preferenceContainer) == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.preferenceContainer, new PreferenceMainFragment())
                        .commit();
            }
        });
    }

    /** 所有 Preference 的事件统一回调到这里 */
    void onPreferenceEvent(String name, Object value) {
        String message = value == null ? name : name + " => " + value;
        Log.d("PreferenceDemo", message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    // 请在此编写初始化操作，例如读取数据等，以及对 UI 组件进行赋值
    public void initDatas(JumpParameter parameter) {
        // vendor_perfservice =
        // getServiceInterface(android.os.ServiceManager.getService("vendor.perfservice"),
        // "com.qualcomm.qti.IPerfManager");


        try {
            //Object securitymgr = getServiceInterface(ServiceManager.getService("security"), "miui.security.ISecurityManager");
            //Boolean blocked = ReflectionUtils.callMethod(securitymgr, "checkSmsBlocked", Boolean.class, (Intent) new Intent("android.provider.Telephony.SMS_DELIVER"));
        } catch (Throwable e) {
            LogUtils.e("AndroidRuntime", Log.getStackTraceString(e));
        }
        
    }

    @Override
    // 此处为组件绑定功能事件、回调等方法
    public void setEvents() {

        /**
         * perfSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> { if (isChecked) { // 使用
         * 0xFFF 表示最高频率，强制跑满 int[] resources = new int[]{ 0x40C00000, 0x1, // SCHEDBOOST 开启
         * 0x40800200, 0xFFF, // PRIME 核心 → 最高频 0x40800300, 0xFFF, // TITANIUM 核心 → 最高频 0x40800000,
         * 0xFFF, // BIG 核心 → 最高频 0x40800100, 0xFFF, // LITTLE 核心 → 最高频 0x41844000, 0x927C0, // CPU
         * LLCC 带宽 0x4303C000, 0x1A0FE0, // LLCC DDR 带宽 0x43458000, 0x1B8A00 // L3 缓存频率 };
         *
         * <p>int lockAcquireResult = ReflectionUtils.tryCallMethod( vendor_perfservice,
         * "perfLockAcquire", Integer.class, 0, // handle 0, // 0 = 无限期锁定 resources ).get();
         * toast("锁定性能: " + lockAcquireResult); } else { int releaseResult =
         * ReflectionUtils.tryCallMethod( vendor_perfservice, "perfLockRelease",
         * Integer.class).get(); toast("释放性能锁: " + releaseResult); } });
         */
    }

    @Override
    public void onClick(View view) {}

    public static IInterface getServiceInterface(IBinder binder, String type) {
        try {
            Method asInterfaceMethod = Class.forName(type + "$Stub").getMethod("asInterface", IBinder.class);
            return (IInterface) asInterfaceMethod.invoke(null, binder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getIdString() {
        PackageManager pm = getPackageManager();

        int uid = android.os.Process.myUid();

        // 读 /proc/self/status 拿 gid / groups
        String gid = "?";
        String groups = "";
        String status = FilesUtils.readFromFile("/proc/self/status");
        if (status != null) {
            for (String line : status.split("\n")) {
                if (line.startsWith("Gid:")) {
                    String[] p = line.trim().split("\\s+");
                    if (p.length >= 2) gid = p[1];
                } else if (line.startsWith("Groups:")) {
                    String[] p = line.trim().split("\\s+");
                    StringBuilder g = new StringBuilder();
                    for (int i = 1; i < p.length; i++) {
                        if (i > 1) g.append(',');
                        g.append(p[i]);
                    }
                    groups = g.toString();
                }
            }
        }

        return String.format(
                "uid=%d(%s) gid=%s(%s) groups=%s context=%s",
                uid, pm.getNameForUid(uid).split(":")[0],
                gid, pm.getNameForUid(Integer.parseInt(gid.trim())).split(":")[0],
                groups,
                FilesUtils.readFromFile("/proc/self/attr/current").trim()
        );
    }


    /**
     * 用 SettingsBasePreferenceFragment 承载 SettingsLib 的各种 Preference 组件，
     * expressive 主题下会自动使用 SettingsPreferenceGroupAdapter，把相邻的普通
     * Preference 绘制成圆角分组卡片。
     * 事件统一回调到 {@link #onPreferenceEvent(String, Object)}。
     */
    public static class PreferenceDemoFragment extends SettingsBasePreferenceFragment {

        private void report(String event, Object value) {
            if (getActivity() instanceof Main) {
                ((Main) getActivity()).onPreferenceEvent(event, value);
            }
        }

        /** 只写日志、不弹 Toast，用于验证 getter/方法返回值 */
        private void check(String name, Object value) {
            Log.d("PreferenceDemo", "[api] " + name + " => " + value);
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            Context context = requireContext();
            PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
            setPreferenceScreen(screen);
           

            Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_md_mic);

            // 演示页面按“应用信息（应用详情）”的真实场景组织：
            // 顶部是应用页头，下面是通知、权限、存储等常用条目。

            // ---------- AppHeaderPreference ----------
            AppHeaderPreference appHeader = new AppHeaderPreference(context);
            appHeader.setKey("demo_app_header");
            appHeader.setTitle("录音机");
            appHeader.setSummary("版本 2.4.0 (128) · 12.6 MB");
            appHeader.setSecondSummary("来自系统应用商店");
            appHeader.setIcon(icon);
            appHeader.setIsInstantApp(false);
            screen.addPreference(appHeader);

            // ---------- AppPreference ----------
            AppPreference appPreference = new AppPreference(context);
            appPreference.setKey("demo_app");
            appPreference.setTitle("通知");
            appPreference.setSummary("允许通知、悬浮通知和桌面角标");
            appPreference.setIcon(R.drawable.ic_md_notifications);
            // AppPreference 本身不带右侧控件，手动挂一个箭头 widget
            appPreference.setWidgetLayoutResource(R.layout.widget_preference_arrow);
            appPreference.setOnPreferenceClickListener(preference -> {
                report("打开通知设置", null);
                return true;
            });
            screen.addPreference(appPreference);

            // ---------- AppSwitchPreference ----------
            AppSwitchPreference appSwitch = new AppSwitchPreference(context);
            appSwitch.setKey("demo_app_switch");
            appSwitch.setTitle("自动更新");
            appSwitch.setSummary("仅通过 Wi-Fi 自动更新应用");
            appSwitch.setIcon(R.drawable.ic_md_autorenew);
            appSwitch.setChecked(true);
            appSwitch.setSummaryOn("已开启自动更新");
            appSwitch.setSummaryOff("已关闭自动更新");
            appSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                report("自动更新", newValue);
                return true;
            });
            screen.addPreference(appSwitch);

            // ---------- TwoTargetPreference ----------
            TwoTargetPreference twoTargetMedium = new TwoTargetPreference(context);
            twoTargetMedium.setKey("demo_two_target_medium");
            twoTargetMedium.setTitle("权限");
            twoTargetMedium.setSummary("相机、麦克风和存储权限");
            twoTargetMedium.setIcon(R.drawable.ic_md_admin_panel_settings);
            twoTargetMedium.setOnPreferenceClickListener(preference -> {
                report("打开权限管理", null);
                return true;
            });
            screen.addPreference(twoTargetMedium);

            // ---------- TwoTargetPreference + 齿轮（复刻 AOSP GearPreference） ----------
            TwoTargetGearPreference twoTargetGear = new TwoTargetGearPreference(context);
            twoTargetGear.setKey("demo_two_target_gear");
            twoTargetGear.setTitle("存储和缓存");
            twoTargetGear.setSummary("已使用 3.2 GB，共 8 GB");
            twoTargetGear.setIcon(R.drawable.ic_md_storage);
            twoTargetGear.setOnPreferenceClickListener(preference -> {
                report("打开存储详情", null);
                return true;
            });
            twoTargetGear.setOnGearClickListener(
                    preference -> report("打开存储设置", null));
            screen.addPreference(twoTargetGear);

              // ---------- MainSwitchPreference ----------
            MainSwitchPreference mainSwitch = new MainSwitchPreference(context);
            mainSwitch.setKey("demo_main_switch");
            mainSwitch.setTitle("允许后台活动");
            mainSwitch.setSummary("关闭后，应用在后台可能无法接收消息");
            mainSwitch.setChecked(true);
            mainSwitch.setIconSpaceReserved(true);
            mainSwitch.addOnSwitchChangeListener(
                    (buttonView, isChecked) -> check("MainSwitchPreference.addOnSwitchChangeListener",
                            isChecked));
            CompoundButton.OnCheckedChangeListener removedListener =
                    (buttonView, isChecked) -> check("MainSwitchPreference 已移除的 listener", isChecked);
            mainSwitch.addOnSwitchChangeListener(removedListener);
            mainSwitch.removeOnSwitchChangeListener(removedListener);
            mainSwitch.updateStatus(mainSwitch.isChecked());
            mainSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                report("允许后台活动", newValue);
                return true;
            });
            screen.addPreference(mainSwitch);


            // ---------- ButtonPreference ----------
            ButtonPreference buttonPreference = new ButtonPreference(context);
            buttonPreference.setKey("demo_button");
            buttonPreference.setTitle("清除缓存 (128 MB)");
            buttonPreference.setIcon(R.drawable.ic_md_delete);
            buttonPreference.setGravity(Gravity.CENTER);
            buttonPreference.setEnabled(true);
            buttonPreference.setOnClickListener(view -> {
                report("已清除缓存", null);
                check("ButtonPreference.getButton()", buttonPreference.getButton());
                check("ButtonPreference.getTitle()", buttonPreference.getTitle());
            });
            buttonPreference.setOnPreferenceClickListener(preference -> {
                report("点击清除缓存条目", null);
                return true;
            });
            screen.addPreference(buttonPreference);

            BannerMessagePreference bannerLow = new BannerMessagePreference(context);
            // 隐藏左上角默认的警告图标（null 会回退到 ic_warning，这里用透明 drawable）
            bannerLow.setIcon(new ColorDrawable(Color.TRANSPARENT));
            bannerLow.setKey("demo_banner_low");
            bannerLow.setTitle("有新版本可用");
            bannerLow.setSubtitle("2.5.0 版本已发布，包含性能优化和问题修复。");
            bannerLow.setAttentionLevel(BannerMessagePreference.AttentionLevel.LOW);
            bannerLow.setPositiveButtonText("立即更新");
            bannerLow.setPositiveButtonVisible(true);
            bannerLow.setPositiveButtonOnClickListener(view -> report("开始更新", null));
            screen.addPreference(bannerLow);
            
          
            // ---------- CardPreference ----------
            // 没有自定义公开 API；dismissible 是 private 且无 setter，默认不显示关闭按钮
            CardPreference card = new CardPreference(context);
            card.setKey("demo_card");
            card.setTitle("关于此应用");
            card.setSummary("版本 2.4.0 · 开发者：系统工具");
            card.setIcon(R.drawable.ic_md_info);
            card.setOnPreferenceClickListener(preference -> {
                report("打开关于页面", null);
                return true;
            });
            screen.addPreference(card);

            // ---------- ExpandablePreference ----------
            ExpandablePreference expandable = new ExpandablePreference(context);
            expandable.setKey("demo_expandable");
            expandable.setTitle("高级设置");
            expandable.setSummary("电池优化、后台限制和系统权限");
            expandable.setIcon(R.drawable.ic_md_tune);
            Preference childA = new Preference(context);
            childA.setKey("demo_expandable_child_a");
            childA.setTitle("电池优化");
            childA.setSummary("未优化：允许应用在后台运行");
            Preference childB = new Preference(context);
            childB.setKey("demo_expandable_child_b");
            childB.setTitle("修改系统设置");
            childB.setSummary("已允许");
            // PreferenceGroup 必须先挂到 screen 上（获得 PreferenceManager），
            // 之后才能添加子 Preference，否则 addPreference() 会 NPE。
            screen.addPreference(expandable);
            expandable.addPreference(childA);
            expandable.addPreference(childB);
            check("ExpandablePreference.isExpanded()", expandable.isExpanded());

            // ---------- FooterPreference ----------
            FooterPreference footer = new FooterPreference(context);
            footer.setKey("demo_footer");
            footer.setTitle("应用数据由系统统一管理");
            footer.setSummary("了解应用数据、权限和存储空间的管理方式。");
            footer.setIcon(R.drawable.ic_md_info);
            footer.setIconVisibility(View.VISIBLE);
            footer.setContentDescription("应用数据管理说明");
            footer.setLearnMoreText("了解详情");
            footer.setLearnMoreAction(view -> report("打开应用数据说明", null));
            check("FooterPreference.getSummary()", footer.getSummary());
            check("FooterPreference.getContentDescription()", footer.getContentDescription());
            screen.addPreference(footer);


            

            // ---------- SliderPreference ----------
            SliderPreference slider = new SliderPreference(context);
            slider.setKey("demo_slider");
            // 演示页不持久化：历史保存的值如果不满足 stepSize 的整数倍，
            // Material Slider 会直接抛 IllegalStateException
            slider.setPersistent(false);
            slider.setTitle("通知音量");
            slider.setSummary("调整此应用通知的提示音量");
            slider.setIcon(R.drawable.ic_md_volume_up);
            slider.setMax(100);
            slider.setMin(0);
            slider.setSliderIncrement(10);
            slider.setAdjustable(true);
            slider.setUpdatesContinuously(true);
            slider.setShowSliderValue(true);
            slider.setLabelFormater(value -> Math.round(value) + "%");
            slider.setOnPreferenceChangeListener((preference, newValue) -> {
                report("通知音量", newValue);
                return true;
            });
            screen.addPreference(slider);
            // 挂到 hierarchy 之后再设值，避免被 onSetInitialValue 覆盖
            slider.setValue(40);
            check("SliderPreference.getMin()", slider.getMin());
            check("SliderPreference.getMax()", slider.getMax());
            check("SliderPreference.getValue()", slider.getValue());
            check("SliderPreference.getSliderIncrement()", slider.getSliderIncrement());
            check("SliderPreference.isAdjustable()", slider.isAdjustable());
            check("SliderPreference.getUpdatesContinuously()", slider.getUpdatesContinuously());
            check("SliderPreference.getShowSliderValue()", slider.getShowSliderValue());

           
            StatusBannerPreference statusLow = new StatusBannerPreference(context);
            statusLow.setKey("demo_status_banner_low");
            statusLow.setTitle("已是最新版本");
            statusLow.setSummary("当前版本已包含最新安全补丁。");
            statusLow.setIcon(R.drawable.ic_md_check_circle);
            statusLow.setIconLevel(StatusBannerPreference.BannerStatus.LOW);
            statusLow.setButtonLevel(StatusBannerPreference.BannerStatus.LOW);
            statusLow.setButtonText(R.string.status_action_check_update);
            statusLow.setButtonOnClickListener(view -> report("检查更新", null));
            screen.addPreference(statusLow);

         


            // ---------- ZeroStatePreference ----------
            // 没有自定义公开 API；图标 tint 在构造时读取 icon，
            // 而公开 API 只能在构造后 setIcon，所以这里展示的是未 tint 的图标
            ZeroStatePreference zeroState = new ZeroStatePreference(context);
            zeroState.setKey("demo_zero_state");
            zeroState.setTitle("没有更多设置项");
            zeroState.setSummary("已显示此应用的全部设置。");
            zeroState.setIcon(R.drawable.ic_md_info);
            screen.addPreference(zeroState);
            
            

        }
    }

}