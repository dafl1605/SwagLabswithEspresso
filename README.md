# Checkout Framework Espresso (APK)

Android UI automation framework for a technical challenge using **Kotlin + Espresso + POM**, targeting an APK installed on a device.

## Covered objective

Automate an end-to-end checkout flow:
`login -> catalog -> add to cart -> checkout info -> overview -> finish -> confirmation`.

## Requirements

- JDK 17+
- Android SDK configured (`ANDROID_HOME` or `ANDROID_SDK_ROOT`)
- [adb](https://developer.android.com/tools/adb) on your `PATH`
- [ripgrep](https://github.com/BurntSushi/ripgrep) (`rg`) — required by `scripts/run-tests.sh`
- A device or emulator visible in `adb devices` in state `device` (pass its id to the script; default is `emulator-5554`)
- Target app installed: package `com.swaglabsmobileapp` (Sauce Labs / Swag Labs APK)

## Framework structure

| Layer | Location | Purpose |
|---|---|---|
| E2E orchestration | `app/src/androidTest/.../e2e` | Full flow without embedded locators |
| Screen Objects | `app/src/androidTest/.../ui/screens` | Screen and action abstraction |
| Synchronization | `app/src/androidTest/.../ui/common/EspressoSync.kt` | Explicit visibility waits |
| Test Data | `app/src/androidTest/assets/test_config.json` + `config/TestConfig.kt` | Externalized and typed data |
| Execution | `scripts/run-tests.sh` | Reproducible CLI command |

## Stable selectors used

`contentDescription` with the APK `test-*` prefix is used:
- Login: `test-Username`, `test-Password`, `test-LOGIN`
- Catalog: `test-PRODUCTS`, `test-ADD TO CART`, `test-Cart`
- Cart: `test-Cart Content`, `test-CHECKOUT`
- Checkout info: `test-Checkout: Your Info`, `test-First Name`, `test-Last Name`, `test-Zip/Postal Code`, `test-CONTINUE`
- Overview: `test-CHECKOUT: OVERVIEW`, `test-FINISH`
- Confirmation: `CHECKOUT: COMPLETE!` text and final message

## Locator policy and maintenance

Locators are centralized in these Screen Objects:
- `app/src/androidTest/java/com/southland/checkout/ui/screens/LoginScreen.kt`
- `app/src/androidTest/java/com/southland/checkout/ui/screens/CatalogScreen.kt`
- `app/src/androidTest/java/com/southland/checkout/ui/screens/CartScreen.kt`
- `app/src/androidTest/java/com/southland/checkout/ui/screens/CheckoutInfoScreen.kt`
- `app/src/androidTest/java/com/southland/checkout/ui/screens/CheckoutOverviewScreen.kt`
- `app/src/androidTest/java/com/southland/checkout/ui/screens/CheckoutCompleteScreen.kt`

**Stable locator policy**
- Prioritize contract-based `resource-id` or `contentDescription` (`test-*`) over visible text.
- Do not duplicate locators in E2E tests; all UI references must go through Screen Objects.
- Treat locator changes as UI contract changes and review them in PRs.

**Common risks when names or attributes change**
- Renaming `id`/`content-desc` breaks critical flows (login, add to cart, checkout).
- Copy/text changes can create false negatives if selectors depend on text.
- Reusing locators across different views can produce ambiguous matches and flakiness.
- Silent APK changes can degrade coverage without early failure if UI smoke checks are missing.

**Recommended mitigation**
- Version locator changes per APK release and document them in PRs.
- Run UI contract smoke checks per screen on every build.
- Use controlled fallback only during version transitions (`id -> content-desc -> text`) with logs.
- If the UI contract fails, block promotion until Screen Objects are updated.

## How to run the tests

Run all commands from the project root `SwagLabswithEspresso` so `./gradlew` (or the wrapper) resolves correctly.

### 1. Environment

- Set `ANDROID_HOME` or `ANDROID_SDK_ROOT` to your Android SDK.
- Ensure `adb` works: `adb devices` — you should see your emulator or USB device with status `device`.

### 2. Install the app under test

The instrumentation tests drive the **installed** Swag Labs app, not a build from this repo. Install the official APK (e.g. from Sauce Labs) on the same device/emulator you will use for tests:

```bash
adb -s <device_id> install -r /path/to/SwagLabs.apk
```

Confirm the package is present:

```bash
adb -s <device_id> shell pm list packages | rg swaglabs
```

### 3. Run the E2E suite (recommended)

The script checks that the device is connected, that `com.swaglabsmobileapp` is installed, then runs `E2ECheckoutTest` only.

```bash
cd SwagLabswithEspresso
chmod +x scripts/run-tests.sh
./scripts/run-tests.sh              # default device: emulator-5554
./scripts/run-tests.sh emulator-5556   # or pass another id from `adb devices`
```

### 4. Same task without the script (Gradle)

From `SwagLabswithEspresso`:

```bash
./gradlew :app:connectedDebugAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.southland.checkout.e2e.E2ECheckoutTest
```

- If the [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) is in the repo (`gradlew` + `gradle/wrapper/`), use `./gradlew` as above.
- If `gradlew` is missing, install Gradle locally and use `gradle` with the same task, or generate the wrapper once with `gradle wrapper` and commit the wrapper files for reproducible runs on other machines.

### 5. Reports

After a run, open the HTML report:

`app/build/reports/androidTests/connected/debug/index.html`

## Challenge assumptions and notes

1. The APK and working flow detected on the emulator correspond to `com.swaglabsmobileapp`.
2. A happy path was implemented with `standard_user / secret_sauce` in external configuration.
3. Checkout test data is not hardcoded in the test (it is loaded from JSON assets).
4. The design prioritizes maintainability (POM, layer separation, explicit waits).
