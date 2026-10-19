package com.sbmatch.androtool;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.widget.SettingsBasePreferenceFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 开源许可页：读取构建时生成的 assets/open_source_licenses.json，
 * 每个依赖库一行 Preference，点击打开对应的许可证地址。
 */
public class OpenSourceLicensesFragment extends SettingsBasePreferenceFragment {

    private static final String TAG = "OpenSourceLicenses";
    private static final String ASSET_NAME = "open_source_licenses.json";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = requireContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
        setPreferenceScreen(screen);

        List<LicenseEntry> entries = loadEntries(context);
        if (entries.isEmpty()) {
            Preference empty = new Preference(context);
            empty.setKey("licenses_empty");
            empty.setTitle("无法读取依赖许可信息");
            empty.setSelectable(false);
            screen.addPreference(empty);
            return;
        }

        for (LicenseEntry entry : entries) {
            Preference preference = new Preference(context);
            preference.setKey("license_" + entry.groupId + ":" + entry.artifactId);
            preference.setTitle(entry.artifactId);
            preference.setSummary(entry.license + " · " + entry.version);
            if (entry.url != null && !entry.url.isEmpty()) {
                preference.setOnPreferenceClickListener(pref -> {
                    openUrl(entry.url);
                    return true;
                });
            }
            screen.addPreference(preference);
        }
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
            collapsingToolbar.setTitle("开源许可");
        }

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

    private List<LicenseEntry> loadEntries(Context context) {
        List<LicenseEntry> result = new ArrayList<>();
        try (InputStream in = context.getAssets().open(ASSET_NAME)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            JSONArray array = new JSONArray(out.toString("UTF-8"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                LicenseEntry entry = new LicenseEntry();
                entry.groupId = object.optString("groupId");
                entry.artifactId = object.optString("artifactId");
                entry.version = object.optString("version");
                entry.license = object.optString("license");
                entry.url = object.optString("url");
                if (!entry.artifactId.isEmpty()) {
                    result.add(entry);
                }
            }
        } catch (Throwable t) {
            Log.w(TAG, "Failed to load " + ASSET_NAME, t);
        }
        return result;
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

    private static class LicenseEntry {
        String groupId;
        String artifactId;
        String version;
        String license;
        String url;
    }
}
