# Checkout Framework Espresso (APK)

Android UI automation framework for a technical challenge using **Kotlin + Espresso + POM**, targeting an APK installed on a device.

## Covered objective

Automate an end-to-end checkout flow:
`login -> catalog -> add to cart -> checkout info -> overview -> finish -> confirmation`.

## Requirements

- JDK 17+
- Android SDK configured (`ANDROID_HOME` or `ANDROID_SDK_ROOT`)
- A device visible in `adb devices` (use its `device_id` as the script parameter; `emulator-5554` is only an example)
- Target APK installed: `com.swaglabsmobileapp`

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

## CLI execution

```bash
cd SwagLabswithEspresso
chmod +x scripts/run-tests.sh
./scripts/run-tests.sh <device_id>
```

Direct alternative:

```bash
./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.southland.checkout.e2e.E2ECheckoutTest
```

AndroidTest report:
`app/build/reports/androidTests/connected/debug/index.html`

## Challenge assumptions and notes

1. The APK and working flow detected on the emulator correspond to `com.swaglabsmobileapp`.
2. A happy path was implemented with `standard_user / secret_sauce` in external configuration.
3. Checkout test data is not hardcoded in the test (it is loaded from JSON assets).
4. The design prioritizes maintainability (POM, layer separation, explicit waits).
