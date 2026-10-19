package com.sbmatch.androtool;

public class TurboJPEG {
    static {
        System.loadLibrary("turbojpeg_jni");
    }

    // Native 方法声明
    public static native int initCompress();

    public static native int initDecompress();

    public static native byte[] compressRGB(byte[] rgbData, int width, int height, int quality);

    public static native byte[] decompressToRGB(byte[] jpegData);

    public static native int getJPEGSize(byte[] jpegData, int[] width, int[] height);

    public static native byte[] compressWithFormat(byte[] imageData, int width, int height,
            int pixelFormat, int quality, int subsamp);

    public static native void release();

}