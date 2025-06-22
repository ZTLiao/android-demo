LOCAL_PATH       :=  $(call my-dir)
include              $(CLEAR_VARS)
LOCAL_MODULE     :=  testRun
LOCAL_SRC_FILES  :=  testRun.c
LOCAL_CFLAGS += -pie -fPIE
LOCAL_LDFLAGS += -pie -fPIE
LOCAL_ARM_MODE := arm

# include              $(BUILD_SHARED_LIBRARY)

include $(BUILD_EXECUTABLE)