package com.sbmatch.androtool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.widget.AppHeaderPreference;
import com.android.settingslib.widget.AppPreference;
import com.android.settingslib.widget.SettingsBasePreferenceFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * “关于”页：全部用 SettingsLib 的 expressive 组件搭建。
 *
 * <ul>
 *     <li>AppHeaderPreference：应用图标 + 名称 + 版本</li>
 *     <li>PreferenceCategory + Preference：开源许可入口</li>
 * </ul>
 */
public class AboutFragment extends SettingsBasePreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = requireContext();
        PackageManager pm = context.getPackageManager();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
        setPreferenceScreen(screen);

        ApplicationInfo appInfo = context.getApplicationInfo();
        String version = getVersionName(context);

        AppHeaderPreference header = new AppHeaderPreference(context);
        header.setKey("about_header");
        header.setTitle(appInfo.loadLabel(pm));
        header.setSummary("版本 " + version);
        try {
            header.setIcon(appInfo.loadIcon(pm));
        } catch (Throwable ignored) {
        }
        header.setIsInstantApp(false);
        screen.addPreference(header);

        PreferenceCategory developerCategory = new PreferenceCategory(context);
        developerCategory.setKey("about_developer_category");
        developerCategory.setTitle("开发者");
        screen.addPreference(developerCategory);

        AppPreference developerPreference = new AppPreference(context);
        developerPreference.setKey("about_developer");
        developerPreference.setTitle("sbmatch");
        developerPreference.setSummary("主要开发者");
        // 头像保持 40dp，同时用更宽的图标框换出右间距
        developerPreference.setLayoutResource(R.layout.about_developer_preference);
        developerPreference.setIcon(roundedAvatar(context));
        developerPreference.setOnPreferenceClickListener(preference -> {
            openUrl("https://github.com/sbmatch");
            return true;
        });
        developerCategory.addPreference(developerPreference);

        // 单独的 PreferenceCategory 会让 license 自成一个卡片分组
        PreferenceCategory licenseCategory = new PreferenceCategory(context);
        licenseCategory.setKey("about_license_category");
        licenseCategory.setTitle("许可信息");
        screen.addPreference(licenseCategory);

        Preference licensePreference = new Preference(context);
        licensePreference.setKey("about_license");
        licensePreference.setTitle("开源许可");
        licensePreference.setSummary("查看依赖库与许可证");
        licensePreference.setOnPreferenceClickListener(preference -> {
            openLicenses();
            return true;
        });
        licenseCategory.addPreference(licensePreference);
    }

    /** 进入依赖库/许可证列表页。 */
    private void openLicenses() {
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
                .replace(R.id.preferenceContainer, new OpenSourceLicensesFragment())
                .addToBackStack("licenses")
                .commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundResource(R.drawable.bg_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        CollapsingToolbarLayout collapsingToolbar =
                requireActivity().findViewById(R.id.collapsingToolbarLayout);
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle("关于");
        }

        // 直接拿 Activity 的 ActionBar，打开标准的 Home-as-Up 返回箭头
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 开发者的头像是普通照片，直接放进图标位是方的。
     * AppPreference 本身不会裁剪图标（应用图标看起来是圆的，是 launcher 给的
     * adaptive icon 形状），这里按 25% 圆角处理一下，接近系统 app 图标的观感。
     *
     * 图标间距由 R.layout.about_developer_preference 的 48dp 图标框负责，
     * 头像本身保持铺满 40dp。
     */
    private Drawable roundedAvatar(Context context) {
        Drawable drawable = AppCompatResources.getDrawable(context, R.drawable.developer_sbmatch);
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (bitmap != null) {
                RoundedBitmapDrawable rounded =
                        RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                rounded.setCornerRadius(bitmap.getWidth() * 0.25f);
                rounded.setAntiAlias(true);
                return rounded;
            }
        }
        return drawable;
    }

    private void openUrl(String url) {
        if (!isAdded()) {
            return;
        }
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Throwable t) {
            Toast.makeText(requireContext(), "无法打开链接", Toast.LENGTH_SHORT).show();
        }
    }

    private String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return info.versionName == null ? "未知" : info.versionName;
        } catch (Throwable t) {
            return "未知";
        }
    }

}
