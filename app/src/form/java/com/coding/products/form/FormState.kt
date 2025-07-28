package com.coding.products.form

import java.time.LocalDate

data class FormState(
    val name: String = "",
    val nameError: String? = null,

    val email: String = "",
    val emailError: String? = null,

    val phone: String = "",
    val phoneError: String? = null,

    val promoCode: String = "",
    val promoCodeError: String? = null,

    val deliveryDate: LocalDate? = null,
    val deliveryDateError: String? = null,

    val rating: String = "",
    val ratingError: String? = null,

    val isSubmitting: Boolean = false,
    val formSubmittedSuccessfully: Boolean = false
)
