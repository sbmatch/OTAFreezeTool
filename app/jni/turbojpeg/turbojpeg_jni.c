#include <jni.h>
#include "turbojpeg.h"
#include <stdlib.h>
#include <string.h>
#include <android/log.h>

#define LOG_TAG "TurboJPEG"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// 全局引用缓存
static tjhandle compressHandle = NULL;
static tjhandle decompressHandle = NULL;

/**
 * 初始化 TurboJPEG 压缩器
 * 
 * @param env JNI 环境
 * @param clazz 类对象
 * @return 0 成功，-1 失败
 */
JNIEXPORT jint JNICALL
Java_com_sbmatch_androtool_TurboJPEG_initCompress(JNIEnv *env, jclass clazz) {
    if (compressHandle != NULL) {
        tj3Destroy(compressHandle);
        compressHandle = NULL;
    }
    
    compressHandle = tj3Init(TJINIT_COMPRESS);
    if (compressHandle == NULL) {
        LOGE("Failed to initialize compressor: %s", tj3GetErrorStr(NULL));
        return -1;
    }
    
    // 设置默认参数
    tj3Set(compressHandle, TJPARAM_QUALITY, 85);
    tj3Set(compressHandle, TJPARAM_SUBSAMP, TJSAMP_420);
    tj3Set(compressHandle, TJPARAM_BOTTOMUP, 0);
    
    LOGI("Compressor initialized successfully");
    return 0;
}

/**
 * 初始化 TurboJPEG 解压器
 */
JNIEXPORT jint JNICALL
Java_com_sbmatch_androtool_TurboJPEG_initDecompress(JNIEnv *env, jclass clazz) {
    if (decompressHandle != NULL) {
        tj3Destroy(decompressHandle);
        decompressHandle = NULL;
    }
    
    decompressHandle = tj3Init(TJINIT_DECOMPRESS);
    if (decompressHandle == NULL) {
        LOGE("Failed to initialize decompressor: %s", tj3GetErrorStr(NULL));
        return -1;
    }
    
    LOGI("Decompressor initialized successfully");
    return 0;
}

/**
 * 压缩 RGB 图像为 JPEG
 * 
 * @param env JNI 环境
 * @param clazz 类对象
 * @param rgbData RGB 数据（24位，R G B 顺序）
 * @param width 图像宽度
 * @param height 图像高度
 * @param quality JPEG 质量 (1-100)
 * @return JPEG 数据字节数组
 */
JNIEXPORT jbyteArray JNICALL
Java_com_sbmatch_androtool_TurboJPEG_compressRGB(JNIEnv *env, jclass clazz,
                                                   jbyteArray rgbData,
                                                   jint width,
                                                   jint height,
                                                   jint quality) {
    if (compressHandle == NULL) {
        LOGE("Compressor not initialized");
        return NULL;
    }
    
    // 获取 RGB 数据
    jsize rgbLen = (*env)->GetArrayLength(env, rgbData);
    jbyte *rgbBytes = (*env)->GetByteArrayElements(env, rgbData, NULL);
    if (rgbBytes == NULL) {
        LOGE("Failed to get RGB data");
        return NULL;
    }
    
    // 设置质量
    if (quality > 0 && quality <= 100) {
        tj3Set(compressHandle, TJPARAM_QUALITY, quality);
    }
    
    // JPEG 输出缓冲区
    unsigned char *jpegBuf = NULL;
    size_t jpegSize = 0;
    
    // 执行压缩
    int result = tj3Compress8(
        compressHandle,
        (const unsigned char *)rgbBytes,  // 源数据
        width,                            // 宽度
        0,                                // pitch (0 = width * pixelSize)
        height,                           // 高度
        TJPF_RGB,                         // 像素格式
        &jpegBuf,                         // JPEG 输出缓冲区
        &jpegSize                         // JPEG 大小
    );
    
    (*env)->ReleaseByteArrayElements(env, rgbData, rgbBytes, JNI_ABORT);
    
    if (result != 0) {
        LOGE("Compression failed: %s", tj3GetErrorStr(compressHandle));
        if (jpegBuf != NULL) {
            tj3Free(jpegBuf);
        }
        return NULL;
    }
    
    // 创建 Java 字节数组
    jbyteArray jpegArray = (*env)->NewByteArray(env, (jsize)jpegSize);
    if (jpegArray == NULL) {
        LOGE("Failed to create byte array");
        tj3Free(jpegBuf);
        return NULL;
    }
    
    (*env)->SetByteArrayRegion(env, jpegArray, 0, (jsize)jpegSize,
                               (const jbyte *)jpegBuf);
    
    tj3Free(jpegBuf);
    LOGI("Compressed %dx%d image to %zu bytes", width, height, jpegSize);
    
    return jpegArray;
}

/**
 * 解压 JPEG 为 RGB 图像
 * 
 * @param env JNI 环境
 * @param clazz 类对象
 * @param jpegData JPEG 数据
 * @return RGB 数据数组 (24位)
 */
JNIEXPORT jbyteArray JNICALL
Java_com_sbmatch_androtool_TurboJPEG_decompressToRGB(JNIEnv *env, jclass clazz,
                                                       jbyteArray jpegData) {
    if (decompressHandle == NULL) {
        LOGE("Decompressor not initialized");
        return NULL;
    }
    
    // 获取 JPEG 数据
    jsize jpegLen = (*env)->GetArrayLength(env, jpegData);
    jbyte *jpegBytes = (*env)->GetByteArrayElements(env, jpegData, NULL);
    if (jpegBytes == NULL) {
        LOGE("Failed to get JPEG data");
        return NULL;
    }
    
    // 读取 JPEG 头信息
    int result = tj3DecompressHeader(
        decompressHandle,
        (const unsigned char *)jpegBytes,
        (size_t)jpegLen
    );
    
    if (result != 0) {
        LOGE("Failed to read JPEG header: %s", tj3GetErrorStr(decompressHandle));
        (*env)->ReleaseByteArrayElements(env, jpegData, jpegBytes, JNI_ABORT);
        return NULL;
    }
    
    // 获取图像尺寸
    int width = tj3Get(decompressHandle, TJPARAM_JPEGWIDTH);
    int height = tj3Get(decompressHandle, TJPARAM_JPEGHEIGHT);
    
    if (width <= 0 || height <= 0) {
        LOGE("Invalid image dimensions: %dx%d", width, height);
        (*env)->ReleaseByteArrayElements(env, jpegData, jpegBytes, JNI_ABORT);
        return NULL;
    }
    
    // 分配 RGB 缓冲区 (3 bytes per pixel)
    int rgbSize = width * height * 3;
    unsigned char *rgbBuf = (unsigned char *)malloc(rgbSize);
    if (rgbBuf == NULL) {
        LOGE("Failed to allocate RGB buffer");
        (*env)->ReleaseByteArrayElements(env, jpegData, jpegBytes, JNI_ABORT);
        return NULL;
    }
    
    // 执行解压
    result = tj3Decompress8(
        decompressHandle,
        (const unsigned char *)jpegBytes,  // JPEG 数据
        (size_t)jpegLen,                   // JPEG 大小
        rgbBuf,                            // 输出 RGB 缓冲区
        0,                                 // pitch (0 = width * pixelSize)
        TJPF_RGB                           // 输出像素格式
    );
    
    (*env)->ReleaseByteArrayElements(env, jpegData, jpegBytes, JNI_ABORT);
    
    if (result != 0) {
        LOGE("Decompression failed: %s", tj3GetErrorStr(decompressHandle));
        free(rgbBuf);
        return NULL;
    }
    
    // 创建 Java 字节数组
    jbyteArray rgbArray = (*env)->NewByteArray(env, rgbSize);
    if (rgbArray == NULL) {
        LOGE("Failed to create RGB byte array");
        free(rgbBuf);
        return NULL;
    }
    
    (*env)->SetByteArrayRegion(env, rgbArray, 0, rgbSize,
                               (const jbyte *)rgbBuf);
    
    free(rgbBuf);
    LOGI("Decompressed %dx%d JPEG image", width, height);
    
    return rgbArray;
}

/**
 * 解压 JPEG 并获取尺寸信息（不实际解压）
 * 
 * @param env JNI 环境
 * @param clazz 类对象
 * @param jpegData JPEG 数据
 * @param width 输出宽度指针
 * @param height 输出高度指针
 * @return 0 成功，-1 失败
 */
JNIEXPORT jint JNICALL
Java_com_sbmatch_androtool_TurboJPEG_getJPEGSize(JNIEnv *env, jclass clazz,
                                                   jbyteArray jpegData,
                                                   jintArray widthResult,
                                                   jintArray heightResult) {
    if (decompressHandle == NULL) {
        LOGE("Decompressor not initialized");
        return -1;
    }
    
    jsize jpegLen = (*env)->GetArrayLength(env, jpegData);
    jbyte *jpegBytes = (*env)->GetByteArrayElements(env, jpegData, NULL);
    if (jpegBytes == NULL) {
        LOGE("Failed to get JPEG data");
        return -1;
    }
    
    int result = tj3DecompressHeader(
        decompressHandle,
        (const unsigned char *)jpegBytes,
        (size_t)jpegLen
    );
    
    (*env)->ReleaseByteArrayElements(env, jpegData, jpegBytes, JNI_ABORT);
    
    if (result != 0) {
        LOGE("Failed to read JPEG header: %s", tj3GetErrorStr(decompressHandle));
        return -1;
    }
    
    int width = tj3Get(decompressHandle, TJPARAM_JPEGWIDTH);
    int height = tj3Get(decompressHandle, TJPARAM_JPEGHEIGHT);
    
    // 设置返回值
    jint *widthArr = (*env)->GetIntArrayElements(env, widthResult, NULL);
    jint *heightArr = (*env)->GetIntArrayElements(env, heightResult, NULL);
    
    if (widthArr != NULL && heightArr != NULL) {
        widthArr[0] = width;
        heightArr[0] = height;
    }
    
    if (widthArr != NULL) {
        (*env)->ReleaseIntArrayElements(env, widthResult, widthArr, 0);
    }
    if (heightArr != NULL) {
        (*env)->ReleaseIntArrayElements(env, heightResult, heightArr, 0);
    }
    
    LOGI("JPEG size: %dx%d", width, height);
    return 0;
}

/**
 * 释放 TurboJPEG 资源
 */
JNIEXPORT void JNICALL
Java_com_sbmatch_androtool_TurboJPEG_release(JNIEnv *env, jclass clazz) {
    if (compressHandle != NULL) {
        tj3Destroy(compressHandle);
        compressHandle = NULL;
    }
    
    if (decompressHandle != NULL) {
        tj3Destroy(decompressHandle);
        decompressHandle = NULL;
    }
    
    LOGI("TurboJPEG resources released");
}

/**
 * 带错误处理的压缩函数（支持更多格式）
 */
JNIEXPORT jbyteArray JNICALL
Java_com_sbmatch_androtool_TurboJPEG_compressWithFormat(JNIEnv *env, jclass clazz,
                                                         jbyteArray imageData,
                                                         jint width,
                                                         jint height,
                                                         jint pixelFormat,
                                                         jint quality,
                                                         jint subsamp) {
    if (compressHandle == NULL) {
        LOGE("Compressor not initialized");
        return NULL;
    }
    
    jsize dataLen = (*env)->GetArrayLength(env, imageData);
    jbyte *dataBytes = (*env)->GetByteArrayElements(env, imageData, NULL);
    if (dataBytes == NULL) {
        LOGE("Failed to get image data");
        return NULL;
    }
    
    // 设置参数
    tj3Set(compressHandle, TJPARAM_QUALITY, quality);
    tj3Set(compressHandle, TJPARAM_SUBSAMP, subsamp);
    
    unsigned char *jpegBuf = NULL;
    size_t jpegSize = 0;
    
    int result = tj3Compress8(
        compressHandle,
        (const unsigned char *)dataBytes,
        width,
        0,
        height,
        pixelFormat,
        &jpegBuf,
        &jpegSize
    );
    
    (*env)->ReleaseByteArrayElements(env, imageData, dataBytes, JNI_ABORT);
    
    if (result != 0) {
        LOGE("Compression failed: %s", tj3GetErrorStr(compressHandle));
        if (jpegBuf != NULL) tj3Free(jpegBuf);
        return NULL;
    }
    
    jbyteArray resultArray = (*env)->NewByteArray(env, (jsize)jpegSize);
    if (resultArray != NULL) {
        (*env)->SetByteArrayRegion(env, resultArray, 0, (jsize)jpegSize,
                                   (const jbyte *)jpegBuf);
    }
    
    tj3Free(jpegBuf);
    return resultArray;
}