/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settingslib.widget.preference.app.R;
/**
 * The SwitchPreference for the pages need to show apps icon.
 */
public class AppSwitchPreference extends SwitchPreferenceCompat {

    public AppSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public AppSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AppSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppSwitchPreference(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (SettingsThemeHelper.isExpressiveTheme(context)) {
            setLayoutResource(
                    com.android.settingslib.widget.theme.R.layout
                            .settingslib_expressive_preference);
            // The expressive layout only provides an empty widget_frame. Without an explicit
            // widgetLayout the preference theme's switchPreferenceCompatStyle wins, which is
            // the legacy AppCompat SwitchCompat rather than the Material 3 MaterialSwitch.
            setWidgetLayoutResource(
                    com.android.settingslib.widget.theme.R.layout
                            .settingslib_expressive_preference_switch);
        } else {
            setLayoutResource(R.layout.preference_app);
        }
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        final View switchView = holder.findViewById(androidx.preference.R.id.switchWidget);
        if (switchView != null) {
            final View rootView = switchView.getRootView();
            rootView.setFilterTouchesWhenObscured(true);
        }
    }
}
