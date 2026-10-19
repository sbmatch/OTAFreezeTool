/*
 * Copyright (C) 2022 The Android Open Source Project
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

import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.SurfaceControl;


import androidx.annotation.NonNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

/**
 * Handles display and layer captures for the system.
 *
 * @hide
 */
public class ScreenCapture {
    private static final String TAG = "ScreenCapture";


    /**
     * Parameters for a screen capture request.
     *
     * <p>This class encapsulates the various settings and configurations for a screen capture
     * class.
     */

    public static final class ScreenCaptureParams implements Parcelable {

        private ScreenCaptureParams(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        public static final Creator<ScreenCaptureParams> CREATOR = new Creator<ScreenCaptureParams>() {
            @Override
            public ScreenCaptureParams createFromParcel(Parcel in) {
                return new ScreenCaptureParams(in);
            }

            @Override
            public ScreenCaptureParams[] newArray(int size) {
                return new ScreenCaptureParams[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }
    }

    public static final class ScreenCaptureResult implements Parcelable {
        private final ColorSpace mColorSpace;
        private final HardwareBuffer mHardwareBuffer;

        /**
         * Returns the {@link ColorSpace} of the captured image.
         */
        public @NonNull ColorSpace getColorSpace() {
            return mColorSpace;
        }

        /**
         * Returns the {@link HardwareBuffer} containing the captured image data.
         */
        public @NonNull HardwareBuffer getHardwareBuffer() {
            return mHardwareBuffer;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            throw new RuntimeException("Stub!");
        }

        @NonNull
        public static final Parcelable.Creator<ScreenCaptureResult> CREATOR =
                new Parcelable.Creator<ScreenCaptureResult>() {
                    public ScreenCaptureResult createFromParcel(Parcel in) {
                        return new ScreenCaptureResult(in);
                    }

                    public ScreenCaptureResult[] newArray(int size) {
                        return new ScreenCaptureResult[size];
                    }
                };

        private ScreenCaptureResult(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        /** ScreenCaptureResult constructor. */
        public ScreenCaptureResult(
                @NonNull ColorSpace colorSpace, @NonNull HardwareBuffer hardwareBuffer) {
            mColorSpace = colorSpace;
            mHardwareBuffer = hardwareBuffer;
        }
    }

    /**
     * @param captureArgs     Arguments about how to take the screenshot
     * @param captureListener A listener to receive the screenshot callback
     * @hide
     */
    public static int captureDisplay(DisplayCaptureArgs captureArgs,
                                     ScreenCaptureListener captureListener) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Captures all the surfaces in a display and returns a {@link ScreenshotHardwareBuffer} with
     * the content.
     *
     * @hide
     */
    public static ScreenshotHardwareBuffer captureDisplay(
            DisplayCaptureArgs captureArgs) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Captures a layer and its children and returns a {@link HardwareBuffer} with the content.
     *
     * @param layer      The root layer to capture.
     * @param sourceCrop The portion of the root surface to capture; caller may pass in 'new
     *                   Rect()' or null if no cropping is desired. If the root layer does not
     *                   have a buffer or a crop set, then a non-empty source crop must be
     *                   specified.
     * @param frameScale The desired scale of the returned buffer; the raw screen will be scaled
     *                   up/down.
     * @return Returns a HardwareBuffer that contains the layer capture.
     * @hide
     */
    public static ScreenshotHardwareBuffer captureLayers(SurfaceControl layer, Rect sourceCrop,
                                                         float frameScale) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Captures a layer and its children and returns a {@link HardwareBuffer} with the content.
     *
     * @param layer      The root layer to capture.
     * @param sourceCrop The portion of the root surface to capture; caller may pass in 'new
     *                   Rect()' or null if no cropping is desired. If the root layer does not
     *                   have a buffer or a crop set, then a non-empty source crop must be
     *                   specified.
     * @param frameScale The desired scale of the returned buffer; the raw screen will be scaled
     *                   up/down.
     * @param format     The desired pixel format of the returned buffer.
     * @return Returns a HardwareBuffer that contains the layer capture.
     * @hide
     */
    public static ScreenshotHardwareBuffer captureLayers(SurfaceControl layer,
                                                          Rect sourceCrop, float frameScale, int format) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     */
    public static ScreenshotHardwareBuffer captureLayers(LayerCaptureArgs captureArgs) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Like {@link #captureLayers(SurfaceControl, Rect, float, int)} but with an array of layer
     * handles to exclude.
     *
     * @hide
     */
    public static ScreenshotHardwareBuffer captureLayersExcluding(SurfaceControl layer,
                                                                  Rect sourceCrop, float frameScale, int format, SurfaceControl[] exclude) {
        LayerCaptureArgs captureArgs = new LayerCaptureArgs.Builder(layer)
                .setSourceCrop(sourceCrop)
                .setFrameScale(frameScale)
                .setPixelFormat(format)
                .setExcludeLayers(exclude)
                .build();

        return captureLayers(captureArgs);
    }

    /**
     * @param captureArgs     Arguments about how to take the screenshot
     * @param captureListener A listener to receive the screenshot callback
     * @hide
     */
    public static int captureLayers(LayerCaptureArgs captureArgs,
                                    ScreenCaptureListener captureListener) {
        throw new RuntimeException("Stub!");
    }

    /**
     * A wrapper around HardwareBuffer that contains extra information about how to
     * interpret the screenshot HardwareBuffer.
     *
     * @hide
     */
    public static class ScreenshotHardwareBuffer {
        private final HardwareBuffer mHardwareBuffer;
        private final ColorSpace mColorSpace;
        private final boolean mContainsSecureLayers;
        private final boolean mContainsHdrLayers;

        public ScreenshotHardwareBuffer(HardwareBuffer hardwareBuffer, ColorSpace colorSpace,
                                        boolean containsSecureLayers, boolean containsHdrLayers) {
            mHardwareBuffer = hardwareBuffer;
            mColorSpace = colorSpace;
            mContainsSecureLayers = containsSecureLayers;
            mContainsHdrLayers = containsHdrLayers;
        }

        /**
         * Create ScreenshotHardwareBuffer from an existing HardwareBuffer object.
         *
         * @param hardwareBuffer       The existing HardwareBuffer object
         * @param dataspace            Dataspace describing the content.
         *                             {@see android.hardware.DataSpace}
         * @param containsSecureLayers Indicates whether this graphic buffer contains captured
         *                             contents of secure layers, in which case the screenshot
         *                             should not be persisted.
         * @param containsHdrLayers    Indicates whether this graphic buffer contains HDR content.
         */
        private static ScreenshotHardwareBuffer createFromNative(HardwareBuffer hardwareBuffer,
                                                                 int dataspace, boolean containsSecureLayers, boolean containsHdrLayers) {
            throw new RuntimeException("Stub!");
        }

        public ColorSpace getColorSpace() {
            return mColorSpace;
        }

        public HardwareBuffer getHardwareBuffer() {
            return mHardwareBuffer;
        }

        /**
         * Whether this screenshot contains secure layers
         */
        public boolean containsSecureLayers() {
            return mContainsSecureLayers;
        }

        /**
         * Returns whether the screenshot contains at least one HDR layer.
         * This information may be useful for informing the display whether this screenshot
         * is allowed to be dimmed to SDR white.
         */
        public boolean containsHdrLayers() {
            return mContainsHdrLayers;
        }

        /**
         * Copy content of ScreenshotHardwareBuffer into a hardware bitmap and return it.
         * Note: If you want to modify the Bitmap in software, you will need to copy the Bitmap
         * into
         * a software Bitmap using {@link Bitmap#copy(Bitmap.Config, boolean)}
         * <p>
         * CAVEAT: This can be extremely slow; avoid use unless absolutely necessary; prefer to
         * directly
         * use the {@link HardwareBuffer} directly.
         *
         * @return Bitmap generated from the {@link HardwareBuffer}
         */
        public Bitmap asBitmap() {
            throw new RuntimeException("Stub!");
        }
    }

    /**
     * A common arguments class used for various screenshot requests. This contains arguments that
     * are shared between {@link DisplayCaptureArgs} and {@link LayerCaptureArgs}
     *
     * @hide
     */
    public static class CaptureArgs implements Parcelable {
        public final int mPixelFormat;
        public final Rect mSourceCrop = new Rect();
        public final float mFrameScaleX;
        public final float mFrameScaleY;
        public final boolean mCaptureSecureLayers;
        public final boolean mAllowProtected;
        public final long mUid;
        public final boolean mGrayscale;
        final SurfaceControl[] mExcludeLayers;
        public final boolean mHintForSeamlessTransition;

        private CaptureArgs(CaptureArgs.Builder<? extends CaptureArgs.Builder<?>> builder) {
            mPixelFormat = builder.mPixelFormat;
            mSourceCrop.set(builder.mSourceCrop);
            mFrameScaleX = builder.mFrameScaleX;
            mFrameScaleY = builder.mFrameScaleY;
            mCaptureSecureLayers = builder.mCaptureSecureLayers;
            mAllowProtected = builder.mAllowProtected;
            mUid = builder.mUid;
            mGrayscale = builder.mGrayscale;
            mExcludeLayers = builder.mExcludeLayers;
            mHintForSeamlessTransition = builder.mHintForSeamlessTransition;
        }

        private CaptureArgs(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        /** Release any layers if set using {@link Builder#setExcludeLayers(SurfaceControl[])}. */
        public void release() {
            throw new RuntimeException("Stub!");
        }

        /**
         * Returns an array of {@link SurfaceControl -> mNativeObject} corresponding to
         * {@link #mExcludeLayers}. Used only in native code.
         */
        private long[] getNativeExcludeLayers() {
            throw new RuntimeException("Stub!");
        }

        /**
         * The Builder class used to construct {@link CaptureArgs}
         *
         * @param <T> A builder that extends {@link CaptureArgs.Builder}
         */
        public static class Builder<T extends CaptureArgs.Builder<T>> {
            private int mPixelFormat = PixelFormat.RGBA_8888;
            private final Rect mSourceCrop = new Rect();
            private float mFrameScaleX = 1;
            private float mFrameScaleY = 1;
            private boolean mCaptureSecureLayers;
            private boolean mAllowProtected;
            private long mUid = -1;
            private boolean mGrayscale;
            private SurfaceControl[] mExcludeLayers;
            private boolean mHintForSeamlessTransition;

            /**
             * Construct a new {@link CaptureArgs} with the set parameters. The builder remains
             * valid.
             */
            public CaptureArgs build() {
                return new CaptureArgs(this);
            }

            /**
             * The desired pixel format of the returned buffer.
             */
            public T setPixelFormat(int pixelFormat) {
                mPixelFormat = pixelFormat;
                return getThis();
            }

            /**
             * The portion of the screen to capture into the buffer. Caller may pass  in
             * 'new Rect()' or null if no cropping is desired.
             */
            public T setSourceCrop( Rect sourceCrop) {
                if (sourceCrop == null) {
                    mSourceCrop.setEmpty();
                } else {
                    mSourceCrop.set(sourceCrop);
                }
                return getThis();
            }

            /**
             * The desired scale of the returned buffer. The raw screen will be scaled up/down.
             */
            public T setFrameScale(float frameScale) {
                mFrameScaleX = frameScale;
                mFrameScaleY = frameScale;
                return getThis();
            }

            /**
             * The desired scale of the returned buffer, allowing separate values for x and y scale.
             * The raw screen will be scaled up/down.
             */
            public T setFrameScale(float frameScaleX, float frameScaleY) {
                mFrameScaleX = frameScaleX;
                mFrameScaleY = frameScaleY;
                return getThis();
            }

            /**
             * Whether to allow the screenshot of secure layers. Warning: This should only be done
             * if the content will be placed in a secure SurfaceControl.
             *
             * @see ScreenshotHardwareBuffer#containsSecureLayers()
             */
            public T setCaptureSecureLayers(boolean captureSecureLayers) {
                mCaptureSecureLayers = captureSecureLayers;
                return getThis();
            }

            /**
             * Whether to allow the screenshot of protected (DRM) content. Warning: The screenshot
             * cannot be read in unprotected space.
             *
             * @see HardwareBuffer#USAGE_PROTECTED_CONTENT
             */
            public T setAllowProtected(boolean allowProtected) {
                mAllowProtected = allowProtected;
                return getThis();
            }

            /**
             * Set the uid of the content that should be screenshot. The code will skip any surfaces
             * that don't belong to the specified uid.
             */
            public T setUid(long uid) {
                mUid = uid;
                return getThis();
            }

            /**
             * Set whether the screenshot should use grayscale or not.
             */
            public T setGrayscale(boolean grayscale) {
                mGrayscale = grayscale;
                return getThis();
            }

            /**
             * An array of {@link SurfaceControl} layer handles to exclude.
             */
            public T setExcludeLayers( SurfaceControl[] excludeLayers) {
                mExcludeLayers = excludeLayers;
                return getThis();
            }

            /**
             * Set whether the screenshot will be used in a system animation.
             * This hint is used for picking the "best" colorspace for the screenshot, in particular
             * for mixing HDR and SDR content.
             * E.g., hintForSeamlessTransition is false, then a colorspace suitable for file
             * encoding, such as BT2100, may be chosen. Otherwise, then the display's color space
             * would be chosen, with the possibility of having an extended brightness range. This
             * is important for screenshots that are directly re-routed to a SurfaceControl in
             * order to preserve accurate colors.
             */
            public T setHintForSeamlessTransition(boolean hintForSeamlessTransition) {
                mHintForSeamlessTransition = hintForSeamlessTransition;
                return getThis();
            }

            /**
             * Each sub class should return itself to allow the builder to chain properly
             */
            T getThis() {
                return (T) this;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }

        public static final Parcelable.Creator<CaptureArgs> CREATOR =
                new Parcelable.Creator<CaptureArgs>() {
                    @Override
                    public CaptureArgs createFromParcel(Parcel in) {
                        return new CaptureArgs(in);
                    }

                    @Override
                    public CaptureArgs[] newArray(int size) {
                        return new CaptureArgs[size];
                    }
                };
    }

    /**
     * The arguments class used to make display capture requests.
     *
     * @hide
     */
    public static class DisplayCaptureArgs extends CaptureArgs {
        private final IBinder mDisplayToken;
        private final int mWidth;
        private final int mHeight;

        private DisplayCaptureArgs(Builder builder) {
            super(builder);
            mDisplayToken = builder.mDisplayToken;
            mWidth = builder.mWidth;
            mHeight = builder.mHeight;
        }

        /**
         * The Builder class used to construct {@link DisplayCaptureArgs}
         */
        public static class Builder extends CaptureArgs.Builder<Builder> {
            private IBinder mDisplayToken;
            private int mWidth;
            private int mHeight;

            /**
             * Construct a new {@link LayerCaptureArgs} with the set parameters. The builder
             * remains valid.
             */
            public DisplayCaptureArgs build() {
                if (mDisplayToken == null) {
                    throw new IllegalStateException(
                            "Can't take screenshot with null display token");
                }
                return new DisplayCaptureArgs(this);
            }

            public Builder(IBinder displayToken) {
                setDisplayToken(displayToken);
            }

            /**
             * The display to take the screenshot of.
             */
            public Builder setDisplayToken(IBinder displayToken) {
                mDisplayToken = displayToken;
                return this;
            }

            /**
             * Set the desired size of the returned buffer. The raw screen  will be  scaled down to
             * this size
             *
             * @param width  The desired width of the returned buffer. Caller may pass in 0 if no
             *               scaling is desired.
             * @param height The desired height of the returned buffer. Caller may pass in 0 if no
             *               scaling is desired.
             */
            public Builder setSize(int width, int height) {
                mWidth = width;
                mHeight = height;
                return this;
            }

            @Override
            Builder getThis() {
                return this;
            }
        }
    }

    /**
     * The arguments class used to make layer capture requests.
     */
    public static class LayerCaptureArgs extends CaptureArgs {
        private final long mNativeLayer;
        private final boolean mChildrenOnly;

        private LayerCaptureArgs(Builder builder) {
            super(builder);
            throw new RuntimeException("Stub!");
        }

        /**
         * The Builder class used to construct {@link LayerCaptureArgs}
         */
        public static class Builder extends CaptureArgs.Builder<Builder> {
            private SurfaceControl mLayer;
            private boolean mChildrenOnly = true;

            /**
             * Construct a new {@link LayerCaptureArgs} with the set parameters. The builder
             * remains valid.
             */
            public LayerCaptureArgs build() {
                if (mLayer == null) {
                    throw new IllegalStateException(
                            "Can't take screenshot with null layer");
                }
                return new LayerCaptureArgs(this);
            }

            public Builder(SurfaceControl layer, CaptureArgs args) {
                setLayer(layer);
                setPixelFormat(args.mPixelFormat);
                setSourceCrop(args.mSourceCrop);
                setFrameScale(args.mFrameScaleX, args.mFrameScaleY);
                setCaptureSecureLayers(args.mCaptureSecureLayers);
                setAllowProtected(args.mAllowProtected);
                setUid(args.mUid);
                setGrayscale(args.mGrayscale);
                setExcludeLayers(args.mExcludeLayers);
                setHintForSeamlessTransition(args.mHintForSeamlessTransition);
            }

            public Builder(SurfaceControl layer) {
                setLayer(layer);
            }

            /**
             * The root layer to capture.
             */
            public Builder setLayer(SurfaceControl layer) {
                mLayer = layer;
                return this;
            }

            /**
             * Whether to include the layer itself in the screenshot or just the children and their
             * descendants.
             */
            public Builder setChildrenOnly(boolean childrenOnly) {
                mChildrenOnly = childrenOnly;
                return this;
            }

            @Override
            Builder getThis() {
                return this;
            }
        }
    }

    /**
     * The object used to receive the results when invoking screen capture requests via
     * {@link #captureDisplay(DisplayCaptureArgs, ScreenCaptureListener)} or
     * {@link #captureLayers(LayerCaptureArgs, ScreenCaptureListener)}
     *
     * This listener can only be used for a single call to capture content call.
     */
    public static class ScreenCaptureListener implements Parcelable {
        final long mNativeObject;

        /**
         * @param consumer The callback invoked when the screen capture is complete.
         */
        public ScreenCaptureListener(ObjIntConsumer<ScreenshotHardwareBuffer> consumer) {
            throw new RuntimeException("Stub!");
        }

        public ScreenCaptureListener(Consumer<ScreenshotHardwareBuffer> consumer){
            throw new RuntimeException("Stub!");
        }

        private ScreenCaptureListener(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }

        public static final Parcelable.Creator<ScreenCaptureListener> CREATOR =
                new Parcelable.Creator<ScreenCaptureListener>() {
                    @Override
                    public ScreenCaptureListener createFromParcel(Parcel in) {
                        return new ScreenCaptureListener(in);
                    }

                    @Override
                    public ScreenCaptureListener[] newArray(int size) {
                        return new ScreenCaptureListener[0];
                    }
                };
    }

    /**
     * A helper method to handle the async screencapture callbacks synchronously. This should only
     * be used if the screencapture caller doesn't care that it blocks waiting for a screenshot.
     *
     * @return a {@link SynchronousScreenCaptureListener} that should be used for capture
     * calls into SurfaceFlinger.
     */
    public static SynchronousScreenCaptureListener createSyncCaptureListener() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Helper class to synchronously get the {@link ScreenshotHardwareBuffer} when calling
     * {@link #captureLayers(LayerCaptureArgs, ScreenCaptureListener)} or
     * {@link #captureDisplay(DisplayCaptureArgs, ScreenCaptureListener)}
     */
    public abstract static class SynchronousScreenCaptureListener extends ScreenCaptureListener {
        SynchronousScreenCaptureListener(ObjIntConsumer<ScreenshotHardwareBuffer> consumer) {
            super(consumer);
        }

        /**
         * Get the {@link ScreenshotHardwareBuffer} synchronously. This can be null if the
         */
        
        public abstract ScreenshotHardwareBuffer getBuffer();
    }
}