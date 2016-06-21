@echo off
set DIR=%~dp0

echo   APP_ANDROID_ROOT    = %DIR%
echo   ANDROID_NDK_ROOT    = %NDK%

"%NDK%"/ndk-build -C %DIR%