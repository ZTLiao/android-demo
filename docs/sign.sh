keytool -genkey -keystore my-release-key.keystore -alias my_alias -keyalg RSA -keysize 4096 -validity 10000

zipalign -p -f -v 4 app-debug.apk aligned.apk

apksigner sign  --ks my-release-key.keystore --ks-key-alias my_alias --ks-pass pass:123456 --in ./aligned.apk --out ./check_in_native_signed.apk

adb install --abi armeabi-v7a check_in_native_signed.apk