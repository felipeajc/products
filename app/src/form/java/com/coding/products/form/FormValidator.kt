package com.coding.products.form

import java.text.Normalizer
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Utility object for validating each field in the form.
 * Handles simple business rules and returns [FormValidationError] when invalid.
 */
object FormValidator {

    /**
     * Validates the name field.
     * @return [FormValidationError.NameRequired] if blank, otherwise null.
     */
    fun validateName(name: String): FormValidationError? {
        val nameError = if (name.isBlank()) FormValidationError.NameRequired else null
        return nameError
    }

    /**
     * Validates the email format and presence.
     * @return appropriate [FormValidationError] or null if valid.
     */
    fun validateEmail(email: String): FormValidationError? {
        val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()

        val emailError = when {
            email.isBlank() -> FormValidationError.EmailRequired
            !email.matches(emailPattern) -> FormValidationError.EmailInvalid
            else -> null
        }

        return emailError
    }

    /**
     * Validates phone number format.
     * @return [FormValidationError.PhoneRequired], PhoneInvalid, or null.
     */
    fun validateNumber(phone: String): FormValidationError? {
        val phoneError = when {
            phone.isBlank() -> FormValidationError.PhoneRequired
            !phone.all { it.isDigit() } -> FormValidationError.PhoneInvalid
            else -> null
        }

        return phoneError
    }

    /**
     * Validates the promo code format.
     * - Must be all caps.
     * - No accents.
     * - 3 to 7 characters (A-Z or '-').
     */
    fun validatePromoCode(code: String): FormValidationError? {
        val normalizedCode = removeAccents(code)
        val isValidPattern = "^[A-Z\\-]{3,7}$".toRegex().matches(code)

        val promoCodeError = when {
            code.isBlank() -> FormValidationError.PromoRequired
            normalizedCode != code -> FormValidationError.PromoAccentsNotAllowed
            !isValidPattern -> FormValidationError.PromoInvalidFormat
            else -> null
        }

        return promoCodeError
    }

    /**
     * Validates the selected delivery date.
     * - Must not be null, in the future, or a Monday.
     */
    fun validateDate(date: LocalDate?): FormValidationError? {
        val dateError = when {
            date == null -> FormValidationError.DateRequired
            date.dayOfWeek == DayOfWeek.MONDAY -> FormValidationError.DateIsMonday
            date.isAfter(LocalDate.now()) -> FormValidationError.DateInFuture
            else -> null
        }

        return dateError
    }

    /**
     * Validates that a rating has been selected.
     */
    fun validateRating(rating: String): FormValidationError? {
        val ratingError = if (rating.isBlank()) FormValidationError.RatingRequired else null
        return ratingError
    }

    /**
     * Removes accents from a given string.
     */
    private fun removeAccents(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")
    }
}
