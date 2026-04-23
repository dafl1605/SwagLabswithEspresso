package com.southland.checkout.ui.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.southland.checkout.ui.common.EspressoSync
import com.southland.checkout.ui.locators.Locators

class LoginScreen {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitReady(): LoginScreen {
        val loginVisible = device.wait(Until.hasObject(By.desc(Locators.Login.SCREEN)), 20_000)
        val productsVisible = device.hasObject(By.desc(Locators.Catalog.SCREEN))
        check(loginVisible || productsVisible) {
            "No se encontró Login ni Products tras el lanzamiento"
        }
        return this
    }

    fun login(username: String, password: String): LoginScreen {
        if (device.hasObject(By.desc(Locators.Login.USERNAME))) {
            device.findObject(By.desc(Locators.Login.USERNAME))?.text = username
            device.findObject(By.desc(Locators.Login.PASSWORD))?.text = password
            checkNotNull(device.findObject(By.desc(Locators.Login.LOGIN_BUTTON))) { "No se encontró botón LOGIN" }.click()
        }
        return this
    }

    fun assertErrorMessage(expected: String): LoginScreen {
        checkNotNull(device.findObject(By.textContains(expected))) {
            "No se encontró el error de login esperado '$expected'"
        }
        return this
    }

    fun assertStillOnLogin(): LoginScreen {
        EspressoSync.waitForDesc(Locators.Login.SCREEN)
        return this
    }

    fun assertLoggedIn(): LoginScreen {
        EspressoSync.waitForDesc(Locators.Catalog.SCREEN)
        return this
    }
}
