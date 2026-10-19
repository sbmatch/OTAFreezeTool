/*
 * Copyright (C) 2024 The Android Open Source Project
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

package com.android.settingslib.widget

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import androidx.annotation.DrawableRes
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceGroup
import androidx.preference.PreferenceGroupAdapter
import androidx.preference.PreferenceViewHolder
import com.android.settingslib.widget.theme.R

/**
 * A custom adapter for displaying settings preferences in a list, handling rounded corners for
 * preference items within a group.
 */
@SuppressLint("RestrictedApi")
open class SettingsPreferenceGroupAdapter(preferenceGroup: PreferenceGroup) :
    PreferenceGroupAdapter(preferenceGroup) {

    private val mPreferenceGroup = preferenceGroup
    private var mRoundCornerMappingList: ArrayList<Int> = ArrayList()

    private var mNormalPaddingStart = 0
    private var mGroupPaddingStart = 0
    private var mNormalPaddingEnd = 0
    private var mGroupPaddingEnd = 0
    private var mBottomPadding = 0
    @DrawableRes private var mLegacyBackgroundRes: Int

    private val mHandler = Handler(Looper.getMainLooper())

    private val syncRunnable = Runnable { updatePreferences() }

    init {
        val context = preferenceGroup.context
        mNormalPaddingStart =
            context.resources.getDimensionPixelSize(R.dimen.settingslib_expressive_space_small1)
        mGroupPaddingStart = mNormalPaddingStart * 2
        mNormalPaddingEnd =
            context.resources.getDimensionPixelSize(R.dimen.settingslib_expressive_space_small1)
        mGroupPaddingEnd = mNormalPaddingEnd * 2
        mBottomPadding =
            context.resources.getDimensionPixelSize(R.dimen.settingslib_expressive_space_small1)
        val outValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue,
            true, /* resolveRefs */
        )
        mLegacyBackgroundRes = outValue.resourceId
        updatePreferences()
    }

    @SuppressLint("RestrictedApi")
    override fun onPreferenceHierarchyChange(preference: Preference) {
        super.onPreferenceHierarchyChange(preference)

        // Post after super class has posted their sync runnable to update preferences.
        mHandler.removeCallbacks(syncRunnable)
        mHandler.post(syncRunnable)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: PreferenceViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        // ExpandablePreference 展开/收起后行数会变化，异步的 updatePreferences()
        // 可能还没跑到，导致按旧位置算出圆角/内边距，出现条目跑到卡片外面或向左偏移。
        // 绑定前发现映射长度和当前行数不一致就同步重建一次。
        if (mRoundCornerMappingList.size != itemCount) {
            mappingPreferenceGroup(mRoundCornerMappingList, mPreferenceGroup)
        }
        updateBackground(holder, position)
    }

    private fun updatePreferences() {
        // 只重算圆角/内边距映射，不主动 notifyDataSetChanged()。
        // super.updatePreferences() 在可见列表变化时已经会通知 RecyclerView，
        // 而且我们的 syncRunnable 是排在它之后执行的，重新绑定前映射已经是最新的。
        // 如果这里再 notify 一次，Expandable 展开时整页会被重绑两遍，造成明显卡顿。
        mRoundCornerMappingList = ArrayList()
        mappingPreferenceGroup(mRoundCornerMappingList, mPreferenceGroup)
    }

    @SuppressLint("RestrictedApi")
    private fun mappingPreferenceGroup(cornerStyles: MutableList<Int>, group: PreferenceGroup) {
        cornerStyles.clear()
        cornerStyles.addAll(MutableList(itemCount) { 0 })

        // the first item in to group
        var startIndex = -1
        // the last item in the group
        var endIndex = -1
        var currentParent: PreferenceGroup? = group
        for (i in 0 until itemCount) {
            when (val pref = getItem(i)) {
                // the preference has round corner background, so we don't need to handle it.
                is GroupSectionDividerMixin -> {
                    cornerStyles[i] = 0
                    startIndex = -1
                    endIndex = -1
                }

                // PreferenceCategory should not have round corner background.
                is PreferenceCategory -> {
                    cornerStyles[i] = 0
                    startIndex = -1
                    endIndex = -1
                    currentParent = pref
                }

                // ExpandablePreference is PreferenceGroup but it should handle round corner
                is Expandable -> {
                    // When ExpandablePreference is expanded, we treat is as the first item.
                    if (pref.isExpanded()) {
                        currentParent = pref as? PreferenceGroup
                        startIndex = i
                        cornerStyles[i] = cornerStyles[i] or ROUND_CORNER_TOP or ROUND_CORNER_CENTER
                        endIndex = -1
                    } else {
                        // When collapsed it is a standalone row and still needs a card,
                        // otherwise the whole row has no rounded background.
                        currentParent = pref as? PreferenceGroup
                        startIndex = -1
                        endIndex = -1
                        cornerStyles[i] = cornerStyles[i] or ROUND_CORNER_TOP or
                                ROUND_CORNER_CENTER or ROUND_CORNER_BOTTOM
                    }
                }

                else -> {
                    val parent = pref?.parent

                    // item in the group should have round corner background.
                    cornerStyles[i] = cornerStyles[i] or ROUND_CORNER_CENTER
                    if (parent === currentParent) {
                        // find the first item in the group
                        if (startIndex == -1) {
                            startIndex = i
                            cornerStyles[i] = cornerStyles[i] or ROUND_CORNER_TOP
                        }

                        // find the last item in the group, if we find the new last item, we should
                        // remove the old last item round corner.
                        if (endIndex == -1 || endIndex < i) {
                            if (endIndex != -1) {
                                cornerStyles[endIndex] =
                                    cornerStyles[endIndex] and ROUND_CORNER_BOTTOM.inv()
                            }
                            endIndex = i
                            cornerStyles[i] = cornerStyles[i] or ROUND_CORNER_BOTTOM
                        }
                    } else {
                        // this item is new group, we should reset the index.
                        currentParent = parent
                        startIndex = i
                        cornerStyles[i] = cornerStyles[i] or ROUND_CORNER_TOP
                        endIndex = i
                        cornerStyles[i] = cornerStyles[i] or ROUND_CORNER_BOTTOM
                    }
                }
            }
        }
    }

    /** handle roundCorner background */
    private fun updateBackground(holder: PreferenceViewHolder, position: Int) {
        val context = holder.itemView.context
        @DrawableRes
        val backgroundRes =
            when (SettingsThemeHelper.isExpressiveTheme(context)) {
                true -> getRoundCornerDrawableRes(position, isSelected = false)
                else -> mLegacyBackgroundRes
            }

        val v = holder.itemView
        val isExpressive = SettingsThemeHelper.isExpressiveTheme(context)
        // 记录 itemView 首次绑定时的原始上下 padding（部分布局自带 padding）。
        // RecyclerView 复用行视图时不能沿用上一行留下的 padding。
        val originalPadding = (v.getTag(R.id.settingslib_group_original_padding) as? IntArray)
            ?: intArrayOf(v.paddingTop, v.paddingBottom).also {
                v.setTag(R.id.settingslib_group_original_padding, it)
            }
        // Update background first. View.setBackgroundDrawable() applies the drawable's
        // own padding to the view, and the bottom-corner drawable carries a 16dp bottom
        // padding. Setting the background after the padding would wipe out the 32dp
        // start/end group padding on the last row of every card, making that row shift
        // left and look like it is outside the card.
        //
        // backgroundRes == 0 means the item draws its own background (CardPreference,
        // StatusBannerPreference, banners, etc. implement GroupSectionDividerMixin).
        // Do not clear it: several of those layouts use MaterialCardView as the root,
        // and setBackgroundResource(0) would remove the card background entirely.
        if (!isExpressive || backgroundRes != 0) {
            v.setBackgroundResource(backgroundRes)
        }
        // Update padding
        if (isExpressive) {
            val paddingStart = if (backgroundRes == 0) mNormalPaddingStart else mGroupPaddingStart
            val paddingEnd = if (backgroundRes == 0) mNormalPaddingEnd else mGroupPaddingEnd

            // 带 bottom 圆角的行需要额外的底部内边距，对应卡片底部 16dp 的视觉缩进。
            // 包括分组最后一行，以及四角都圆的单条目卡片（BarChart、收起的 Expandable）。
            val cornerType = mRoundCornerMappingList.getOrNull(position) ?: 0
            val isBottomRow = (cornerType and ROUND_CORNER_BOTTOM) != 0
            val paddingTop = originalPadding[0]
            val paddingBottom = originalPadding[1] + if (isBottomRow) mBottomPadding else 0

            v.setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
        }
    }

    @DrawableRes
    protected fun getRoundCornerDrawableRes(position: Int, isSelected: Boolean): Int {
        return getRoundCornerDrawableRes(position, isSelected, false)
    }

    @DrawableRes
    protected fun getRoundCornerDrawableRes(
        position: Int,
        isSelected: Boolean,
        isHighlighted: Boolean,
    ): Int {
        val cornerType = mRoundCornerMappingList[position]

        if ((cornerType and ROUND_CORNER_CENTER) == 0) {
            return 0
        }

        return when {
            (cornerType and ROUND_CORNER_TOP) != 0 && (cornerType and ROUND_CORNER_BOTTOM) == 0 -> {
                // the first
                if (isSelected) R.drawable.settingslib_round_background_top_selected
                else if (isHighlighted) R.drawable.settingslib_round_background_top_highlighted
                else R.drawable.settingslib_round_background_top
            }

            (cornerType and ROUND_CORNER_BOTTOM) != 0 && (cornerType and ROUND_CORNER_TOP) == 0 -> {
                // the last
                if (isSelected) R.drawable.settingslib_round_background_bottom_selected
                else if (isHighlighted) R.drawable.settingslib_round_background_bottom_highlighted
                else R.drawable.settingslib_round_background_bottom
            }

            (cornerType and ROUND_CORNER_TOP) != 0 && (cornerType and ROUND_CORNER_BOTTOM) != 0 -> {
                // the only one preference
                if (isSelected) R.drawable.settingslib_round_background_selected
                else if (isHighlighted) R.drawable.settingslib_round_background_highlighted
                else R.drawable.settingslib_round_background
            }

            else -> {
                // in the center
                if (isSelected) R.drawable.settingslib_round_background_center_selected
                else if (isHighlighted) R.drawable.settingslib_round_background_center_highlighted
                else R.drawable.settingslib_round_background_center
            }
        }
    }

    companion object {
        private const val ROUND_CORNER_CENTER: Int = 1
        private const val ROUND_CORNER_TOP: Int = 1 shl 1
        private const val ROUND_CORNER_BOTTOM: Int = 1 shl 2
    }
}
