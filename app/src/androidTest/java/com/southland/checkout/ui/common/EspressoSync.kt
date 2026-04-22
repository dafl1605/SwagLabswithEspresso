package com.southland.checkout.ui.common

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until

object EspressoSync {
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun waitForDesc(desc: String, timeoutMs: Long = 10_000) {
        check(device.wait(Until.hasObject(By.desc(desc)), timeoutMs)) {
            "No apareció descriptor '$desc' en $timeoutMs ms"
        }
    }

    fun waitForText(text: String, timeoutMs: Long = 10_000) {
        check(device.wait(Until.hasObject(By.text(text)), timeoutMs)) {
            "No apareció texto '$text' en $timeoutMs ms"
        }
    }
}
