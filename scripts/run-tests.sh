#!/usr/bin/env bash
set -euo pipefail

DEVICE_ID="${1:-emulator-5554}"

if ! adb devices | rg -q "^${DEVICE_ID}[[:space:]]+device$"; then
  echo "Dispositivo ${DEVICE_ID} no disponible en adb devices."
  exit 1
fi

adb -s "${DEVICE_ID}" shell pm list packages | rg -q "com.swaglabsmobileapp" || {
  echo "El paquete com.swaglabsmobileapp no está instalado en ${DEVICE_ID}."
  exit 1
}

if [[ -x "./gradlew" ]]; then
  ./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.southland.checkout.e2e.E2ECheckoutTest
else
  gradle :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.southland.checkout.e2e.E2ECheckoutTest
fi
