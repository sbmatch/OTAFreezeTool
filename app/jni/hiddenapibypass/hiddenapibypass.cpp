//
// Created by sbmatch on 2025/9/11.
//
#include <jni.h>
#include <android/log.h>
#include <android/api-level.h>

#define LOG_TAG "HiddenApiBypass"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

/**
 * Apply hidden API exemptions via VMRuntime.setHiddenApiExemptions().
 * Called from JNI — JNI calls bypass Android hidden API restrictions.
 *
 * Uses "L" as the exemption prefix to cover ALL Java object types
 * (every JNI field/method signature starts with "L" for non-primitive types).
 */
static bool applyHiddenApiExemptions(JNIEnv *env) {
    jclass vmRuntimeClass = env->FindClass("dalvik/system/VMRuntime");
    if (vmRuntimeClass == nullptr) {
        env->ExceptionClear();
        return false;
    }

    jmethodID getRuntime = env->GetStaticMethodID(
        vmRuntimeClass, "getRuntime", "()Ldalvik/system/VMRuntime;");
    if (getRuntime == nullptr) {
        env->ExceptionClear();
        return false;
    }

    jobject runtime = env->CallStaticObjectMethod(vmRuntimeClass, getRuntime);
    if (runtime == nullptr || env->ExceptionCheck()) {
        env->ExceptionClear();
        return false;
    }

    jmethodID setExemptions = env->GetMethodID(
        vmRuntimeClass, "setHiddenApiExemptions", "([Ljava/lang/String;)V");
    if (setExemptions == nullptr) {
        env->ExceptionClear();
        return false;
    }

    // "L" prefix matches all non-primitive JNI type signatures (all classes)
    jclass stringClass = env->FindClass("java/lang/String");
    jobjectArray exemptions = env->NewObjectArray(1, stringClass, nullptr);
    jstring prefix = env->NewStringUTF("L");
    env->SetObjectArrayElement(exemptions, 0, prefix);
    env->DeleteLocalRef(prefix);

    env->CallVoidMethod(runtime, setExemptions, exemptions);
    env->DeleteLocalRef(exemptions);

    if (env->ExceptionCheck()) {
        env->ExceptionClear();
        return false;
    }

    return true;
}


static JNINativeMethod gMethods[] = {
    
};

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void * /*reserved*/) {
    JNIEnv *env = nullptr;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    // Apply exemptions immediately on library load — before any Java code runs.
    // This is critical: ContentProviders initialize before Application.onCreate(),
    // so waiting for Java to call addHiddenApiExemptions may be too late.
    if (android_get_device_api_level() >= 28) {
        if (applyHiddenApiExemptions(env)) {
            LOGI("Hidden API exemptions applied in JNI_OnLoad");
        } else {
            LOGE("Could not apply exemptions in JNI_OnLoad, will retry on Java call");
        }
    }

    jclass clazz = env->FindClass("com/sbmatch/androtool/utils/MagicHelper");
    if (clazz == nullptr) {
        env->ExceptionClear();
        LOGI("Target class not found — skipping registration");
        return JNI_VERSION_1_6;
    }

    if (env->RegisterNatives(clazz, gMethods, sizeof(gMethods) / sizeof(gMethods[0])) < 0) {
        env->ExceptionClear();
        LOGE("RegisterNatives failed");
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}
