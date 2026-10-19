/*
 * Copyright (C) 2010 The Android Open Source Project
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

package android.os;

/**
 * @hide
 */
public final class RemoteCallback implements Parcelable {

    public interface OnResultListener {
        void onResult(Bundle result);
    }

    private final OnResultListener mListener;
    private final Handler mHandler;
    private final IRemoteCallback mCallback;

    public RemoteCallback(OnResultListener listener) {
        this(listener, null);
    }

    public RemoteCallback(OnResultListener listener, Handler handler) {
        throw new RuntimeException("Stub!");
    }

    RemoteCallback(Parcel parcel) {
        throw new RuntimeException("Stub!");
    }

    public void sendResult(final Bundle result) {
        // Do local dispatch
        throw new RuntimeException("Stub!");
    }

    /** @hide */
    public IRemoteCallback getInterface() {
        return mCallback;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStrongBinder(mCallback.asBinder());
    }

    public static final Parcelable.Creator<RemoteCallback> CREATOR
            = new Parcelable.Creator<RemoteCallback>() {
        public RemoteCallback createFromParcel(Parcel parcel) {
            return new RemoteCallback(parcel);
        }

        public RemoteCallback[] newArray(int size) {
            return new RemoteCallback[size];
        }
    };
}
