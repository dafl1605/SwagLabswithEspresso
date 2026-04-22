package com.southland.checkout.config

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.json.JSONException
import org.json.JSONObject

data class TestConfig(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val postalCode: String,
    val expectedCompletionTitle: String,
) {
    fun validate(): TestConfig {
        require(username.isNotBlank()) { "Invalid value for key 'username' in test_config.json: value must not be blank." }
        require(password.isNotBlank()) { "Invalid value for key 'password' in test_config.json: value must not be blank." }
        require(firstName.isNotBlank()) { "Invalid value for key 'firstName' in test_config.json: value must not be blank." }
        require(lastName.isNotBlank()) { "Invalid value for key 'lastName' in test_config.json: value must not be blank." }
        require(postalCode.isNotBlank()) { "Invalid value for key 'postalCode' in test_config.json: value must not be blank." }
        require(expectedCompletionTitle.isNotBlank()) {
            "Invalid value for key 'expectedCompletionTitle' in test_config.json: value must not be blank."
        }
        return this
    }
}

object TestConfigLoader {
    private const val CONFIG_FILE = "test_config.json"

    fun load(context: Context = InstrumentationRegistry.getInstrumentation().context): TestConfig {
        val jsonText = context.assets.open(CONFIG_FILE).bufferedReader().use { it.readText() }

        val root = runCatching { JSONObject(jsonText) }
            .getOrElse { throwable ->
                throw IllegalArgumentException("Invalid JSON format in $CONFIG_FILE.", throwable)
            }

        return TestConfig(
            username = root.getRequiredString("username"),
            password = root.getRequiredString("password"),
            firstName = root.getRequiredString("firstName"),
            lastName = root.getRequiredString("lastName"),
            postalCode = root.getRequiredString("postalCode"),
            expectedCompletionTitle = root.getRequiredString("expectedCompletionTitle"),
        ).validate()
    }

    private fun JSONObject.getRequiredString(key: String): String {
        return runCatching {
            if (!has(key)) {
                throw NoSuchElementException("Missing required key '$key' in $CONFIG_FILE.")
            }

            getString(key)
        }.getOrElse { throwable ->
            when (throwable) {
                is NoSuchElementException -> throw IllegalArgumentException(throwable.message, throwable)
                is JSONException -> throw IllegalArgumentException(
                    "Invalid or non-string value for key '$key' in $CONFIG_FILE.",
                    throwable,
                )
                else -> throw IllegalArgumentException(
                    "Unable to read key '$key' in $CONFIG_FILE.",
                    throwable,
                )
            }
        }
    }
}
