/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.android.internal.app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public class AlertController {

    public AlertController(Context context, DialogInterface di, Window window) {
        throw new RuntimeException("Stub!");
    }

    public void setCustomTitle(View customTitleView) {
        throw new RuntimeException("Stub!");
    }

    public void setView(int layoutResId) {
        throw new RuntimeException("Stub!");
    }

    public void setView(View view) {
        throw new RuntimeException("Stub!");
    }

    public void setButton(int whichButton, CharSequence text,
                         DialogInterface.OnClickListener listener, Message msg){
        throw new RuntimeException("Stub!");
    }



    public static class AlertParams {
        public final Context mContext;
        public CharSequence mTitle;
        public View mCustomTitleView;
        public CharSequence mMessage;
        public CharSequence mPositiveButtonText;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mNegativeButtonText;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public boolean mCancelable;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public CharSequence[] mItems;
        public ListAdapter mAdapter;
        public DialogInterface.OnClickListener mOnClickListener;
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;

        public AlertParams(Context context) {
            throw new RuntimeException("Stub!");
        }
    }
}
