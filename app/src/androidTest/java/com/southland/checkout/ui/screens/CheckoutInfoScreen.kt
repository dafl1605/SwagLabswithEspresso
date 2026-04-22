package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.southland.checkout.ui.common.EspressoSync

class CheckoutInfoScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): CheckoutInfoScreen {
        EspressoSync.waitForDesc("test-Checkout: Your Info")
        return this
    }

    fun fillData(firstName: String, lastName: String, zip: String): CheckoutInfoScreen {
        device.findObject(By.desc("test-First Name"))?.text = firstName
        device.findObject(By.desc("test-Last Name"))?.text = lastName
        device.findObject(By.desc("test-Zip/Postal Code"))?.text = zip
        return this
    }

    fun continueCheckout(): CheckoutInfoScreen {
        checkNotNull(device.findObject(By.desc("test-CONTINUE"))) { "No se encontró botón CONTINUE" }.click()
        return this
    }
}
