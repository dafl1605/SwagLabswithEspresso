package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.southland.checkout.ui.common.EspressoSync
import com.southland.checkout.ui.locators.Locators

class CartScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): CartScreen {
        EspressoSync.waitForDesc(Locators.Cart.SCREEN)
        return this
    }

    fun checkout(): CartScreen {
        checkNotNull(device.findObject(By.desc(Locators.Cart.CHECKOUT_BUTTON))) { "No se encontró botón CHECKOUT" }.click()
        return this
    }
}
