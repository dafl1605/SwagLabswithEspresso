package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.southland.checkout.ui.common.EspressoSync

class CartScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): CartScreen {
        EspressoSync.waitForDesc("test-Cart Content")
        return this
    }

    fun checkout(): CartScreen {
        checkNotNull(device.findObject(By.desc("test-CHECKOUT"))) { "No se encontró botón CHECKOUT" }.click()
        return this
    }
}
