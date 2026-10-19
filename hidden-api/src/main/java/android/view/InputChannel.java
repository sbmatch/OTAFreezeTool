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

package android.view;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * An input channel specifies the file descriptors used to send input events to
 * a window in another process.  It is Parcelable so that it can be sent
 * to the process that is to receive events.  Only one thread should be reading
 * from an InputChannel at a time.
 * @hide
 */
public final class InputChannel implements Parcelable {
    private static final String TAG = "InputChannel";

    private static final boolean DEBUG = false;


    public static final Parcelable.Creator<InputChannel> CREATOR
            = new Parcelable.Creator<InputChannel>() {
        public InputChannel createFromParcel(Parcel source) {
            throw new RuntimeException("Stub!");
        }

        public InputChannel[] newArray(int size) {
            throw new RuntimeException("Stub!");
        }
    };

    private long mPtr; // used by native code

    /**
     * Creates an uninitialized input channel.
     * It can be initialized by reading from a Parcel or by transferring the state of
     * another input channel into this one.
     */

    public InputChannel() {
    }

    /**
     *  Set Native input channel object from native space.
     *  @param nativeChannel the native channel object.
     *
     *  @hide
     */
    private void setNativeInputChannel(long nativeChannel) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Creates a new input channel pair.  One channel should be provided to the input
     * dispatcher and the other to the application's input queue.
     * @param name The descriptive (non-unique) name of the channel pair.
     * @return A pair of input channels.  The first channel is designated as the
     * server channel and should be used to publish input events.  The second channel
     * is designated as the client channel and should be used to consume input events.
     */
    public static InputChannel[] openInputChannelPair(String name) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Gets the name of the input channel.
     * @return The input channel name.
     */
    public String getName() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Disposes the input channel.
     * Explicitly releases the reference this object is holding on the input channel.
     * When all references are released, the input channel will be closed.
     */
    public void dispose() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Release the Java objects hold over the native InputChannel. If other references
     * still exist in native-land, then the channel may continue to exist.
     */
    public void release() {
    }

    /**
     * Creates a copy of this instance to the outParameter. This is used to pass an input channel
     * as an out parameter in a binder call.
     * @param other The other input channel instance.
     */
    public void copyTo(InputChannel outParameter) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Duplicates the input channel.
     */
    public InputChannel dup() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }

    public void readFromParcel(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public String toString() {
        return getName();
    }

    public IBinder getToken() {
        throw new RuntimeException("Stub!");
    }
}
