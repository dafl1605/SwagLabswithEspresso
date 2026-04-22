package com.southland.checkout.config

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.json.JSONObject

data class CheckoutHappyPathConfig(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val postalCode: String,
    val expectedCompletionTitle: String,
)

data class InvalidLoginConfig(
    val username: String,
    val password: String,
    val expectedErrorMessage: String,
)

data class RequiredFieldsConfig(
    val firstName: String,
    val lastName: String,
    val postalCode: String,
    val expectedErrorMessage: String,
)

object TestConfigLoader {
    private const val HAPPY_PATH_FILE = "checkout_happy_path.json"
    private const val INVALID_LOGIN_FILE = "login_invalid.json"
    private const val REQUIRED_FIELDS_FILE = "checkout_required_fields_empty.json"

    fun loadCheckoutHappyPath(
        context: Context = InstrumentationRegistry.getInstrumentation().context,
    ): CheckoutHappyPathConfig {
        val root = readJson(context, HAPPY_PATH_FILE)
        return CheckoutHappyPathConfig(
            username = root.getString("username"),
            password = root.getString("password"),
            firstName = root.getString("firstName"),
            lastName = root.getString("lastName"),
            postalCode = root.getString("postalCode"),
            expectedCompletionTitle = root.getString("expectedCompletionTitle"),
        )
    }

    fun loadInvalidLogin(
        context: Context = InstrumentationRegistry.getInstrumentation().context,
    ): InvalidLoginConfig {
        val root = readJson(context, INVALID_LOGIN_FILE)
        return InvalidLoginConfig(
            username = root.getString("username"),
            password = root.getString("password"),
            expectedErrorMessage = root.getString("expectedErrorMessage"),
        )
    }

    fun loadRequiredFields(
        context: Context = InstrumentationRegistry.getInstrumentation().context,
    ): RequiredFieldsConfig {
        val root = readJson(context, REQUIRED_FIELDS_FILE)
        return RequiredFieldsConfig(
            firstName = root.getString("firstName"),
            lastName = root.getString("lastName"),
            postalCode = root.getString("postalCode"),
            expectedErrorMessage = root.getString("expectedErrorMessage"),
        )
    }

    private fun readJson(context: Context, fileName: String): JSONObject {
        val jsonText = context.assets.open(fileName).bufferedReader().use { it.readText() }
        return JSONObject(jsonText)
    }
}
