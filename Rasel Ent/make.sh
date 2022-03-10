#!/bin/bash
 
set -e
 
AAPT="/opt/android-sdk/build-tools/24.0.3/aapt"
DX="/opt/android-sdk/build-tools/24.0.3/dx"
ZIPALIGN="/opt/android-sdk/build-tools/24.0.3/zipalign"
APKSIGNER="/opt/android-sdk/build-tools/24.0.3/apksigner"
PLATFORM="/opt/android-sdk/platforms/android-24/android.jar"
 
echo "Cleaning..."
rm -rf obj/*
rm -rf src/in/ignas/raselEntAdmin/R.java
rm -rf bin/*
 
echo "Generating R.java file..."
$AAPT package -f -m -J src -M AndroidManifest.xml -S res -I $PLATFORM
 
echo "Compiling..."
javac -d obj -classpath src -bootclasspath $PLATFORM -source 1.7 -target 1.7 src/in/ignas/raselEntAdmin/MainActivity.java
javac -d obj -classpath src -bootclasspath $PLATFORM -source 1.7 -target 1.7 src/in/ignas/raselEntAdmin/R.java
 
echo "Translating in Dalvik bytecode..."
$DX --dex --output=classes.dex obj
 
echo "Making APK..."
$AAPT package -f -m -F bin/faranew.unaligned.apk -M AndroidManifest.xml -S res -I $PLATFORM
$AAPT add bin/faranew.unaligned.apk classes.dex
 
echo "Aligning and signing APK..."
$ZIPALIGN -f 4 bin/faranew.unaligned.apk bin/faranew.apk
$APKSIGNER sign --ks mykey.keystore bin/faranew.apk