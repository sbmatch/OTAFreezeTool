LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := hidden_api_bypass
LOCAL_SRC_FILES := hiddenapibypass.cpp
LOCAL_LDLIBS    := -llog

include $(BUILD_SHARED_LIBRARY)
