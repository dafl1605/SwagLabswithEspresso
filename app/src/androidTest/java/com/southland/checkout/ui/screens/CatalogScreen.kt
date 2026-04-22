package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.southland.checkout.ui.common.EspressoSync

class CatalogScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): CatalogScreen {
        EspressoSync.waitForDesc("test-PRODUCTS")
        return this
    }

    fun addFirstVisibleProductToCart(): CatalogScreen {
        checkNotNull(device.findObject(By.desc("test-ADD TO CART"))) { "No se encontró botón ADD TO CART" }
            .click()
        return this
    }

    fun openCart(): CatalogScreen {
        checkNotNull(device.findObject(By.desc("test-Cart"))) { "No se encontró icono Cart" }.click()
        return this
    }
}
