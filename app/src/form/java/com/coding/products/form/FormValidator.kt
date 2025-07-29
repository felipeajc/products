package com.coding.products.form

import java.text.Normalizer
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Utility object for validating each field in the form.
 * Handles simple business rules + error messages via [FormValidationError].
 */
object FormValidator {

    /**
     * Checks if name is not blank.
     * Returns error if invalid, null if OK.
     */
    fun validateName(name: String): FormValidationError? {
        return if (name.isBlank()) FormValidationError.NameRequired else null
    }

    /**
     * Basic email validation.
     * Must match pattern like "something@domain.com".
     */
    fun validateEmail(email: String): FormValidationError? {
        val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
        return when {
            email.isBlank() -> FormValidationError.EmailRequired
            !email.matches(emailPattern) -> FormValidationError.EmailInvalid
            else -> null
        }
    }

    /**
     * Checks if the number is not blank and only contains digits.
     */
    fun validateNumber(phone: String): FormValidationError? {
        return when {
            phone.isBlank() -> FormValidationError.PhoneRequired
            !phone.all { it.isDigit() } -> FormValidationError.PhoneInvalid
            else -> null
        }
    }

    /**
     * Promo code must:
     * - Be all caps
     * - No accents
     * - 3 to 7 characters (letters or dash)
     */
    fun validatePromoCode(code: String): FormValidationError? {
        if (code.isBlank()) return FormValidationError.PromoRequired

        val normalized = removeAccents(code)
        val validChars = "^[A-Z\\-]{3,7}$".toRegex()

        return when {
            normalized != code -> FormValidationError.PromoAccentsNotAllowed
            !validChars.matches(code) -> FormValidationError.PromoInvalidFormat
            else -> null
        }
    }

    /**
     * Valid delivery date:
     * - Not null
     * - Not in future
     * - Not Monday (business logic)
     */
    fun validateDate(date: LocalDate?): FormValidationError? {
        if (date == null) return FormValidationError.DateRequired
        if (date.dayOfWeek == DayOfWeek.MONDAY) return FormValidationError.DateIsMonday
        if (date.isAfter(LocalDate.now())) return FormValidationError.DateInFuture
        return null
    }

    /**
     * Rating must be filled in.
     */
    fun validateRating(rating: String): FormValidationError? {
        return if (rating.isBlank()) FormValidationError.RatingRequired else null
    }

    /**
     * Removes accents from input.
     * Used to normalize promo code.
     */
    private fun removeAccents(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")
    }
}
