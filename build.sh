#!/bin/bash

# Настройки
SDK_PLATFORM="/data/data/com.termux/files/usr/share/java/android.jar"
PACKAGE_PATH="com/mindspan/app"

echo "[1/5] Очистка старых файлов..."
rm -rf build MindSpan.apk
mkdir -p build/gen build/obj build/bin

echo "[2/5] Обработка ресурсов (AAPT)..."
aapt package -f -m -J build/gen -S app/src/main/res -M app/src/main/AndroidManifest.xml -I $SDK_PLATFORM

echo "[3/5] Компиляция Java (ECJ)..."
ecj -d build/obj -source 1.8 -target 1.8 -cp $SDK_PLATFORM \
    build/gen/$PACKAGE_PATH/R.java \
    app/src/main/java/$PACKAGE_PATH/MainActivity.java

echo "[4/5] Конвертация в DEX (DX)..."
dx --dex --output=build/bin/classes.dex build/obj

echo "[5/5] Сборка и подпись APK..."
aapt package -f -M app/src/main/AndroidManifest.xml -S app/src/main/res -A app/src/main/assets -I $SDK_PLATFORM -F build/bin/MindSpan.unsigned.apk build/bin
# Добавляем классы в архив
cd build/bin && aapt add MindSpan.unsigned.apk classes.dex && cd ../..
# Подписываем (пароль по умолчанию: 123456)
apksigner debug build/bin/MindSpan.unsigned.apk MindSpan.apk

echo "--------------------------------"
echo "ГОТОВО! Файл: MindSpan.apk"
