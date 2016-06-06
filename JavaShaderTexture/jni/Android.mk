LOCAL_PATH:= $(call my-dir)  
  
include $(CLEAR_VARS)  
  
LOCAL_MODULE    := libmathfunc 
LOCAL_CFLAGS    := -Werror  
LOCAL_SRC_FILES := \
MathData.cpp
LOCAL_LDLIBS    := -llog
  
include $(BUILD_SHARED_LIBRARY)