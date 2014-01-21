LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_STATIC_JAVA_LIBRARIES := android-support-v13
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v4

LOCAL_RESOURCE_DIR := $(addprefix $(LOCAL_PATH)/, res)
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_SRC_FILES += \
        src/com/google/android/apps/dashclock/api/IDashClockDataProvider.aidl \
        src/com/google/android/apps/dashclock/api/internal/IExtensionHost.aidl \
        src/com/google/android/apps/dashclock/api/internal/IExtension.aidl

LOCAL_PACKAGE_NAME := DashClock

include $(BUILD_PACKAGE)

# Use the following include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
