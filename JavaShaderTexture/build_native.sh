
# if [[ "$buildexternalsfromsource" ]]; then
#     echo "Building external dependencies from source"
#     "$NDK_ROOT"/ndk-build -C "$APP_ANDROID_ROOT" $* \
#         "NDK_MODULE_PATH=${COCOS2DX_ROOT}:${COCOS2DX_ROOT}/cocos2dx/platform/third_party/android/source"
# else
#     echo "Using prebuilt externals"
#     "$NDK_ROOT"/ndk-build -C "$APP_ANDROID_ROOT" $* \
#         "NDK_MODULE_PATH=${COCOS2DX_ROOT}:${COCOS2DX_ROOT}/cocos2dx/platform/third_party/android/prebuilt"
# fi

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
APP_ANDROID_ROOT="$DIR"
"$NDK_ROOT"/ndk-build -C "$APP_ANDROID_ROOT"