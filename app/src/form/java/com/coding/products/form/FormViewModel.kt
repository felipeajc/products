package com.coding.products.form

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel that manages form state and validation logic.
 * Updates each field independently and clears error states when changed.
 * Full validation happens on form submission.
 */
@HiltViewModel
class FormViewModel @Inject constructor() : ViewModel() {

    private val _formState = MutableStateFlow(FormState())
    val formState: StateFlow<FormState> = _formState.asStateFlow()

    // Updates the name field and clears its error
    fun onNameChange(name: String) {
        _formState.value = _formState.value.copy(name = name, nameError = null)
    }

    // Updates the email field and clears its error
    fun onEmailChange(email: String) {
        _formState.value = _formState.value.copy(email = email, emailError = null)
    }

    // Updates the phone field and clears its error
    fun onPhoneChange(phone: String) {
        _formState.value = _formState.value.copy(phone = phone, phoneError = null)
    }

    // Updates the promo code field and clears its error
    fun onPromoCodeChange(code: String) {
        _formState.value = _formState.value.copy(promoCode = code, promoCodeError = null)
    }

    // Parses and updates the delivery date from a string
    fun onDateChange(dateString: String) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        val parsedDate = try {
            LocalDate.parse(dateString, formatter)
        } catch (e: Exception) {
            null
        }

        _formState.value = _formState.value.copy(
            deliveryDate = parsedDate,
            deliveryDateError = null
        )
    }

    // Updates the rating field and clears its error
    fun onRatingChange(rating: String) {
        _formState.value = _formState.value.copy(rating = rating, ratingError = null)
    }

    /**
     * Runs all form validations.
     * Updates the state with error messages (if any) and flags whether submission succeeded.
     */
    fun validateForm(context: Context): Boolean {
        val state = _formState.value

        val nameError = FormValidator.validateName(context, state.name)
        val emailError = FormValidator.validateEmail(context, state.email)
        val phoneError = FormValidator.validateNumber(context, state.phone)
        val promoCodeError = FormValidator.validatePromoCode(context, state.promoCode)
        val dateError = FormValidator.validateDate(context, state.deliveryDate)
        val ratingError = FormValidator.validateRating(context, state.rating)

        val isValid = listOf(
            nameError,
            emailError,
            phoneError,
            promoCodeError,
            dateError,
            ratingError
        ).all { it == null }

        _formState.value = state.copy(
            nameError = nameError,
            emailError = emailError,
            phoneError = phoneError,
            promoCodeError = promoCodeError,
            deliveryDateError = dateError,
            ratingError = ratingError,
            isSubmitting = true,
            formSubmittedSuccessfully = isValid
        )

        return isValid
    }
}
