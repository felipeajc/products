package com.coding.products.form

import android.content.Context
import com.coding.products.R
import java.text.Normalizer
import java.time.DayOfWeek
import java.time.LocalDate

object FormValidator {

    fun validateName(context: Context, name: String): String? {
        return if (name.isBlank()) context.getString(R.string.error_name_required) else null
    }

    fun validateEmail(context: Context, email: String): String? {
        val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
        return when {
            email.isBlank() -> context.getString(R.string.error_email_required)
            !email.matches(emailPattern) -> context.getString(R.string.error_email_invalid)
            else -> null
        }
    }

    fun validateNumber(context: Context, number: String): String? {
        return when {
            number.isBlank() -> context.getString(R.string.error_number_required)
            !number.all { it.isDigit() } -> context.getString(R.string.error_number_digits_only)
            else -> null
        }
    }

    fun validatePromoCode(context: Context, code: String): String? {
        if (code.isBlank()) return context.getString(R.string.error_promo_required)

        val normalized = removeAccents(code)
        val validChars = "^[A-Z\\-]{3,7}$".toRegex()

        return when {
            normalized != code -> context.getString(R.string.error_promo_no_accents)
            !validChars.matches(code) -> context.getString(R.string.error_promo_invalid_format)
            else -> null
        }
    }

    fun validateDate(context: Context, date: LocalDate?): String? {
        return when {
            date == null -> context.getString(R.string.error_date_required)
            date.dayOfWeek == DayOfWeek.MONDAY -> context.getString(R.string.error_date_monday)
            date.isAfter(LocalDate.now()) -> context.getString(R.string.error_date_future)
            else -> null
        }
    }

    fun validateRating(context: Context, rating: String?): String? {
        return if (rating.isNullOrBlank()) context.getString(R.string.error_rating_required) else null
    }

    private fun removeAccents(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")
    }
}
