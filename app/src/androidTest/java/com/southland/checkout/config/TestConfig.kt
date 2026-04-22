package com.southland.checkout.config

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.json.JSONObject

data class TestConfig(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val postalCode: String,
    val expectedCompletionTitle: String,
)

object TestConfigLoader {
    private const val CONFIG_FILE = "test_config.json"

    fun load(context: Context = InstrumentationRegistry.getInstrumentation().context): TestConfig {
        val jsonText = context.assets.open(CONFIG_FILE).bufferedReader().use { it.readText() }
        val root = JSONObject(jsonText)
        return TestConfig(
            username = root.getString("username"),
            password = root.getString("password"),
            firstName = root.getString("firstName"),
            lastName = root.getString("lastName"),
            postalCode = root.getString("postalCode"),
            expectedCompletionTitle = root.getString("expectedCompletionTitle"),
        )
    }
}
