package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import com.southland.checkout.ui.common.EspressoSync
import com.southland.checkout.ui.locators.Locators

class CheckoutOverviewScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): CheckoutOverviewScreen {
        EspressoSync.waitForDesc(Locators.Overview.SCREEN)
        return this
    }

    fun finish(): CheckoutOverviewScreen {
        device.findObject(By.desc(Locators.Overview.SCREEN))?.scroll(Direction.DOWN, 1.0f)
        checkNotNull(device.findObject(By.desc(Locators.Overview.FINISH_BUTTON))) { "No se encontró botón FINISH" }.click()
        return this
    }
}
