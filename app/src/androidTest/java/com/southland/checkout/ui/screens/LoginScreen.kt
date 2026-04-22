package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.southland.checkout.ui.common.EspressoSync

class LoginScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): LoginScreen {
        val loginVisible = device.wait(Until.hasObject(By.desc("test-Login")), 20_000)
        val productsVisible = device.hasObject(By.desc("test-PRODUCTS"))
        check(loginVisible || productsVisible) {
            "No se encontró Login ni Products tras el lanzamiento"
        }
        return this
    }

    fun login(username: String, password: String): LoginScreen {
        if (device.hasObject(By.desc("test-Username"))) {
            device.findObject(By.desc("test-Username"))?.text = username
            device.findObject(By.desc("test-Password"))?.text = password
            checkNotNull(device.findObject(By.desc("test-LOGIN"))) { "No se encontró botón LOGIN" }.click()
            EspressoSync.waitForDesc("test-PRODUCTS")
        }
        return this
    }
}
