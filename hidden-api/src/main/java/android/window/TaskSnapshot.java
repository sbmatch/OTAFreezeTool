/*
 * Copyright (C) 2020 The Android Open Source Project
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

package android.window;

import android.content.ComponentName;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.WindowInsetsController;

/**
 * Represents a task snapshot.
 */
public class TaskSnapshot implements Parcelable {
    // Identifier of this snapshot
    private final long mId;
    // The elapsed real time (in nanoseconds) when this snapshot was captured, not intended for use outside the
    // process in which the snapshot was taken (ie. this is not parceled)
    private final long mCaptureTime;
    // Top activity in task when snapshot was taken
    private final ComponentName mTopActivityComponent;
    private final HardwareBuffer mSnapshot;
    /** Indicates whether task was in landscape or portrait */
    private final int mOrientation;
    private final int mRotation;
    /** The size of the snapshot before scaling */
    private final Point mTaskSize;
    private final Rect mContentInsets;
    private final Rect mLetterboxInsets;
    // Whether this snapshot is a down-sampled version of the high resolution snapshot, used
    // mainly for loading snapshots quickly from disk when user is flinging fast
    private final boolean mIsLowResolution;
    // Whether or not the snapshot is a real snapshot or an app-theme generated snapshot due to
    // the task having a secure window or having previews disabled
    private final boolean mIsRealSnapshot;
    private final int mWindowingMode;
    private final int mAppearance;
    private final boolean mIsTranslucent;
    private final boolean mHasImeSurface;
    private final int mUiMode;
    // Must be one of the named color spaces, otherwise, always use SRGB color space.
    private final ColorSpace mColorSpace;
    private int mInternalReferences;

    /** This snapshot object is being broadcast. */
    public static final int REFERENCE_BROADCAST = 1;
    /** This snapshot object is in the cache. */
    public static final int REFERENCE_CACHE = 1 << 1;
    /** This snapshot object is being persistent. */
    public static final int REFERENCE_PERSIST = 1 << 2;
    /** This snapshot object is being used for content suggestion. */
    public static final int REFERENCE_CONTENT_SUGGESTION = 1 << 3;
    
    public TaskSnapshot(long id, long captureTime,
                        ComponentName topActivityComponent, HardwareBuffer snapshot,
                        ColorSpace colorSpace, int orientation, int rotation, Point taskSize,
                        Rect contentInsets, Rect letterboxInsets, boolean isLowResolution,
                        boolean isRealSnapshot, int windowingMode,
                        int appearance, boolean isTranslucent,
                        boolean hasImeSurface, int uiMode) {
        throw new RuntimeException("Stub!");
    }

    private TaskSnapshot(Parcel source) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @return Identifier of this snapshot.
     */
    public long getId() {
        return mId;
    }

    /**
     * @return The elapsed real time (in nanoseconds) when this snapshot was captured. This time is
     * only valid in the process where this snapshot was taken.
     */
    public long getCaptureTime() {
        return mCaptureTime;
    }

    /**
     * @return The top activity component for the task at the point this snapshot was taken.
     */
    public ComponentName getTopActivityComponent() {
        return mTopActivityComponent;
    }

    /**
     * @return The graphic buffer representing the screenshot.
     *
     * Note: Prefer {@link #getHardwareBuffer}, which returns the internal object. This version
     * creates a new object.
     */

//    public GraphicBuffer getSnapshot() {
//        throw new RuntimeException("Stub!");
//    }

    /**
     * @return The hardware buffer representing the screenshot.
     */
    public HardwareBuffer getHardwareBuffer() {
        return mSnapshot;
    }

    /**
     * @return The color space of hardware buffer representing the screenshot.
     */
    public ColorSpace getColorSpace() {
        return mColorSpace;
    }

    /**
     * @return The screen orientation the screenshot was taken in.
     */

    public int getOrientation() {
        return mOrientation;
    }

    /**
     * @return The screen rotation the screenshot was taken in.
     */
    public int getRotation() {
        return mRotation;
    }

    /**
     * @return The size of the task at the point this snapshot was taken.
     */
    public Point getTaskSize() {
        return mTaskSize;
    }

    /**
     * @return The system/content insets on the snapshot. These can be clipped off in order to
     *         remove any areas behind system bars in the snapshot.
     */
    public Rect getContentInsets() {
        return mContentInsets;
    }

    /**
     * @return The letterbox insets on the snapshot. These can be clipped off in order to
     *         remove any letterbox areas in the snapshot.
     */
    public Rect getLetterboxInsets() {
        return mLetterboxInsets;
    }

    /**
     * @return Whether this snapshot is a down-sampled version of the full resolution.
     */
    public boolean isLowResolution() {
        return mIsLowResolution;
    }

    /**
     * @return Whether or not the snapshot is a real snapshot or an app-theme generated snapshot
     * due to the task having a secure window or having previews disabled.
     */

    public boolean isRealSnapshot() {
        return mIsRealSnapshot;
    }

    /**
     * @return Whether or not the snapshot is of a translucent app window (non-fullscreen or has
     * a non-opaque pixel format).
     */
    public boolean isTranslucent() {
        return mIsTranslucent;
    }

    /**
     * @return Whether or not the snapshot has the IME surface.
     */
    public boolean hasImeSurface() {
        return mHasImeSurface;
    }

    /**
     * @return The windowing mode of the task when this snapshot was taken.
     */
    public int getWindowingMode() {
        return mWindowingMode;
    }

    /**
     * @return The {@link WindowInsetsController -> Appearance} flags for the top most visible
     *         fullscreen window at the time that the snapshot was taken.
     */
    public int getAppearance() {
        return mAppearance;
    }

    /**
     * @return The uiMode the screenshot was taken in.
     */
    public int getUiMode() {
        return mUiMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Adds a reference when the object is held somewhere.
     * Only used in core.
     */
    public synchronized void addReference(int usage) {
        mInternalReferences |= usage;
    }

    /**
     * Removes a reference when the object is not held from somewhere. The snapshot will be closed
     * once the reference becomes zero.
     * Only used in core.
     */
    public synchronized void removeReference(int usage) {
        throw new RuntimeException("Stub!");
    }

    public static final Creator<TaskSnapshot> CREATOR = new Creator<TaskSnapshot>() {
        public TaskSnapshot createFromParcel(Parcel source) {
            return new TaskSnapshot(source);
        }
        public TaskSnapshot[] newArray(int size) {
            return new TaskSnapshot[size];
        }
    };

    /** Builder for a {@link TaskSnapshot} object */
    public static final class Builder {
        private long mId;
        private long mCaptureTime;
        private ComponentName mTopActivity;
        private HardwareBuffer mSnapshot;
        private ColorSpace mColorSpace;
        private int mOrientation;
        private int mRotation;
        private Point mTaskSize;
        private Rect mContentInsets;
        private Rect mLetterboxInsets;
        private boolean mIsRealSnapshot;
        private int mWindowingMode;
        private int mAppearance;
        private boolean mIsTranslucent;
        private boolean mHasImeSurface;
        private int mPixelFormat;
        private int mUiMode;

        public Builder setId(long id) {
            mId = id;
            return this;
        }

        public Builder setCaptureTime(long captureTime) {
            mCaptureTime = captureTime;
            return this;
        }

        public Builder setTopActivityComponent(ComponentName name) {
            mTopActivity = name;
            return this;
        }

        public Builder setSnapshot(HardwareBuffer buffer) {
            mSnapshot = buffer;
            return this;
        }

        public Builder setColorSpace(ColorSpace colorSpace) {
            mColorSpace = colorSpace;
            return this;
        }

        public Builder setOrientation(int orientation) {
            mOrientation = orientation;
            return this;
        }

        public Builder setRotation(int rotation) {
            mRotation = rotation;
            return this;
        }

        /**
         * Sets the original size of the task
         */
        public Builder setTaskSize(Point size) {
            mTaskSize = size;
            return this;
        }

        public Builder setContentInsets(Rect contentInsets) {
            mContentInsets = contentInsets;
            return this;
        }

        public Builder setLetterboxInsets(Rect letterboxInsets) {
            mLetterboxInsets = letterboxInsets;
            return this;
        }

        public Builder setIsRealSnapshot(boolean realSnapshot) {
            mIsRealSnapshot = realSnapshot;
            return this;
        }

        public Builder setWindowingMode(int windowingMode) {
            mWindowingMode = windowingMode;
            return this;
        }

        public Builder setAppearance(int appearance) {
            mAppearance = appearance;
            return this;
        }

        public Builder setIsTranslucent(boolean isTranslucent) {
            mIsTranslucent = isTranslucent;
            return this;
        }

        /**
         * Sets the IME visibility when taking the snapshot of the task.
         */
        public Builder setHasImeSurface(boolean hasImeSurface) {
            mHasImeSurface = hasImeSurface;
            return this;
        }

        /**
         * Sets the original uiMode while capture
         */
        public Builder setUiMode(int uiMode) {
            mUiMode = uiMode;
            return this;
        }

        public int getPixelFormat() {
            return mPixelFormat;
        }

        public Builder setPixelFormat(int pixelFormat) {
            mPixelFormat = pixelFormat;
            return this;
        }

        public TaskSnapshot build() {
            return new TaskSnapshot(
                    mId,
                    mCaptureTime,
                    mTopActivity,
                    mSnapshot,
                    mColorSpace,
                    mOrientation,
                    mRotation,
                    mTaskSize,
                    mContentInsets,
                    mLetterboxInsets,
                    // When building a TaskSnapshot with the Builder class, isLowResolution
                    // is always false. Low-res snapshots are only created when loading from
                    // disk.
                    false /* isLowResolution */,
                    mIsRealSnapshot,
                    mWindowingMode,
                    mAppearance,
                    mIsTranslucent,
                    mHasImeSurface,
                    mUiMode);

        }
    }
}
