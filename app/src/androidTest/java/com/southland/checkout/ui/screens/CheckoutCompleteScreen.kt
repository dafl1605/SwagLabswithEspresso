package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.southland.checkout.ui.common.EspressoSync

class CheckoutCompleteScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): CheckoutCompleteScreen {
        EspressoSync.waitForText("CHECKOUT: COMPLETE!")
        return this
    }

    fun assertCompletionMessage(expected: String): CheckoutCompleteScreen {
        checkNotNull(device.findObject(By.text(expected))) {
            "No se encontró mensaje de confirmación '$expected'"
        }
        return this
    }
}
