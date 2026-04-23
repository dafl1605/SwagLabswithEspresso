package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.southland.checkout.ui.common.EspressoSync
import com.southland.checkout.ui.locators.Locators

class CheckoutInfoScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): CheckoutInfoScreen {
        EspressoSync.waitForDesc(Locators.CheckoutInfo.SCREEN)
        return this
    }

    fun fillData(firstName: String, lastName: String, zip: String): CheckoutInfoScreen {
        device.findObject(By.desc(Locators.CheckoutInfo.FIRST_NAME))?.text = firstName
        device.findObject(By.desc(Locators.CheckoutInfo.LAST_NAME))?.text = lastName
        device.findObject(By.desc(Locators.CheckoutInfo.ZIP_POSTAL_CODE))?.text = zip
        return this
    }

    fun continueCheckout(): CheckoutInfoScreen {
        checkNotNull(device.findObject(By.desc(Locators.CheckoutInfo.CONTINUE_BUTTON))) { "No se encontró botón CONTINUE" }.click()
        return this
    }
}
