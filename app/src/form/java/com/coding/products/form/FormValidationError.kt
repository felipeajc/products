package com.coding.products.form

import android.content.Context
import com.coding.products.R

sealed class FormValidationError {
    data object NameRequired : FormValidationError()
    data object EmailRequired : FormValidationError()
    data object EmailInvalid : FormValidationError()
    data object PhoneRequired : FormValidationError()
    data object PhoneInvalid : FormValidationError()
    data object PromoRequired : FormValidationError()
    data object PromoAccentsNotAllowed : FormValidationError()
    data object PromoInvalidFormat : FormValidationError()
    data object DateRequired : FormValidationError()
    data object DateInFuture : FormValidationError()
    data object DateIsMonday : FormValidationError()
    data object RatingRequired : FormValidationError()
}

fun FormValidationError?.asString(context: Context): String? {
    return when (this) {
        FormValidationError.NameRequired -> context.getString(R.string.error_name_required)
        FormValidationError.EmailRequired -> context.getString(R.string.error_email_required)
        FormValidationError.EmailInvalid -> context.getString(R.string.error_email_invalid)
        FormValidationError.PhoneRequired -> context.getString(R.string.error_number_required)
        FormValidationError.PhoneInvalid -> context.getString(R.string.error_number_digits_only)
        FormValidationError.PromoRequired -> context.getString(R.string.error_promo_required)
        FormValidationError.PromoAccentsNotAllowed -> context.getString(R.string.error_promo_no_accents)
        FormValidationError.PromoInvalidFormat -> context.getString(R.string.error_promo_invalid_format)
        FormValidationError.DateRequired -> context.getString(R.string.error_date_required)
        FormValidationError.DateInFuture -> context.getString(R.string.error_date_future)
        FormValidationError.DateIsMonday -> context.getString(R.string.error_date_monday)
        FormValidationError.RatingRequired -> context.getString(R.string.error_rating_required)
        null -> null
    }
}
