package com.sbmatch.androtool;

import android.content.Context;
import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.widget.TwoTargetPreference;

/**
 * 复刻 AOSP Settings 的 GearPreference：一个带第二点击区的偏好项。
 *
 * <p>继承 SettingsLib 的 {@link TwoTargetPreference}，把齿轮布局 inflate 到
 * 竖线右侧的 {@code @android:id/widget_frame}。整行（itemView）仍然走 Preference
 * 自己的点击逻辑；齿轮 View 单独设置 OnClickListener，因此两边互不影响。
 */
public class TwoTargetGearPreference extends TwoTargetPreference {

    /** 齿轮被点击时的回调。 */
    public interface OnGearClickListener {
        void onGearClick(TwoTargetGearPreference preference);
    }

    private OnGearClickListener mOnGearClickListener;

    public TwoTargetGearPreference(Context context) {
        super(context);
    }

    public void setOnGearClickListener(OnGearClickListener listener) {
        mOnGearClickListener = listener;
        notifyChanged();
    }

    @Override
    protected int getSecondTargetResId() {
        return R.layout.widget_two_target_gear;
    }

    @Override
    protected boolean shouldHideSecondTarget() {
        // 没有设置齿轮监听时，和 AOSP 一样把竖线和右侧区域一起隐藏。
        return mOnGearClickListener == null;
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        final View gear = holder.findViewById(R.id.settings_button);
        if (gear != null) {
            gear.setOnClickListener(mOnGearClickListener == null
                    ? null
                    : view -> mOnGearClickListener.onGearClick(this));
        }
    }
}
