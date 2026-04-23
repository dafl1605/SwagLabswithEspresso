package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.southland.checkout.ui.common.EspressoSync
import com.southland.checkout.ui.locators.Locators

class CatalogScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): CatalogScreen {
        EspressoSync.waitForDesc(Locators.Catalog.SCREEN)
        return this
    }

    fun addFirstVisibleProductToCart(): CatalogScreen {
        checkNotNull(device.findObject(By.desc(Locators.Catalog.ADD_TO_CART_BUTTON))) { "No se encontró botón ADD TO CART" }
            .click()
        return this
    }

    fun openCart(): CatalogScreen {
        checkNotNull(device.findObject(By.desc(Locators.Catalog.CART_ICON))) { "No se encontró icono Cart" }.click()
        return this
    }
}
