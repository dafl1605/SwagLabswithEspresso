package com.southland.checkout.ui.locators

/**
 * Contrato de localizadores de UI usado por las pruebas de instrumentation.
 *
 * Estos valores representan el contrato de UI con el APK (contentDescription y textos críticos)
 * y deben mantenerse sincronizados con la app para evitar falsos negativos en los tests.
 */
object Locators {
    object Login {
        const val SCREEN = "test-Login"
        const val USERNAME = "test-Username"
        const val PASSWORD = "test-Password"
        const val LOGIN_BUTTON = "test-LOGIN"
    }

    object Catalog {
        const val SCREEN = "test-PRODUCTS"
        const val ADD_TO_CART_BUTTON = "test-ADD TO CART"
        const val CART_ICON = "test-Cart"
    }

    object Cart {
        const val SCREEN = "test-Cart Content"
        const val CHECKOUT_BUTTON = "test-CHECKOUT"
        const val ITEM_DESC_CONTAINS = "test-Item"
    }

    object CheckoutInfo {
        const val SCREEN = "test-Checkout: Your Info"
        const val FIRST_NAME = "test-First Name"
        const val LAST_NAME = "test-Last Name"
        const val ZIP_POSTAL_CODE = "test-Zip/Postal Code"
        const val CONTINUE_BUTTON = "test-CONTINUE"
    }

    object Overview {
        const val SCREEN = "test-CHECKOUT: OVERVIEW"
        const val FINISH_BUTTON = "test-FINISH"
    }

    object Complete {
        const val TITLE_TEXT = "CHECKOUT: COMPLETE!"
    }
}
