package com.southland.checkout.e2e

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.southland.checkout.config.TestConfigLoader
import com.southland.checkout.ui.screens.CartScreen
import com.southland.checkout.ui.screens.CatalogScreen
import com.southland.checkout.ui.screens.CheckoutCompleteScreen
import com.southland.checkout.ui.screens.CheckoutInfoScreen
import com.southland.checkout.ui.screens.CheckoutOverviewScreen
import com.southland.checkout.ui.screens.LoginScreen
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class E2ECheckoutTest {
    private val loginScreen = LoginScreen()
    private val catalogScreen = CatalogScreen()
    private val cartScreen = CartScreen()
    private val infoScreen = CheckoutInfoScreen()
    private val overviewScreen = CheckoutOverviewScreen()
    private val completeScreen = CheckoutCompleteScreen()

    @Before
    fun launchTargetApp() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.executeShellCommand("am force-stop com.swaglabsmobileapp")
        device.executeShellCommand("monkey -p com.swaglabsmobileapp -c android.intent.category.LAUNCHER 1")
    }

    @Test
    fun checkoutHappyPath_standardUser() {
        val config = TestConfigLoader.load()

        loginScreen.waitReady()
            .login(config.username, config.password)

        catalogScreen.waitReady()
            .addFirstVisibleProductToCart()
            .openCart()

        cartScreen.waitReady()
            .checkout()

        infoScreen.waitReady()
            .fillData(config.firstName, config.lastName, config.postalCode)
            .continueCheckout()

        overviewScreen.waitReady()
            .finish()

        completeScreen.waitReady()
            .assertCompletionMessage(config.expectedCompletionTitle)
    }
}
