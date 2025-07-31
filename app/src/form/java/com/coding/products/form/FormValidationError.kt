package com.coding.products.form

import androidx.annotation.StringRes
import com.coding.products.R

sealed class FormValidationError(@StringRes val messageRes: Int) {
    data object NameRequired : FormValidationError(R.string.error_name_required)
    data object EmailRequired : FormValidationError(R.string.error_email_required)
    data object EmailInvalid : FormValidationError(R.string.error_email_invalid)
    data object PhoneRequired : FormValidationError(R.string.error_number_required)
    data object PhoneInvalid : FormValidationError(R.string.error_number_digits_only)
    data object PromoRequired : FormValidationError(R.string.error_promo_required)
    data object PromoAccentsNotAllowed : FormValidationError(R.string.error_promo_no_accents)
    data object PromoInvalidFormat : FormValidationError(R.string.error_promo_invalid_format)
    data object DateRequired : FormValidationError(R.string.error_date_required)
    data object DateInFuture : FormValidationError(R.string.error_date_future)
    data object DateIsMonday : FormValidationError(R.string.error_date_monday)
    data object RatingRequired : FormValidationError(R.string.error_rating_required)
}

