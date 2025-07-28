package com.coding.products.form

import java.time.LocalDate

/**
 * Tracks the state of the form screen — inputs, errors, loading state, etc.
 *
 * All fields have a value and a nullable error (null means no error).
 *
 * @param name User's name input.
 * @param nameError Error message for name input (or null if valid).
 *
 * @param email User's email input.
 * @param emailError Error for email input.
 *
 * @param phone User's phone input.
 * @param phoneError Error for phone input.
 *
 * @param promoCode Optional promo code input.
 * @param promoCodeError Error for promo code.
 *
 * @param deliveryDate Selected delivery date (nullable).
 * @param deliveryDateError Error for delivery date.
 *
 * @param rating User's rating selection.
 * @param ratingError Error for rating.
 *
 * @param isSubmitting Whether the form is currently being submitted.
 * @param formSubmittedSuccessfully True if submission succeeded.
 */
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
