package com.coding.products.form

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel responsible for managing the form screen's UI state and validation logic.
 *
 * - Exposes the current [FormState] via [formState].
 * - Updates each form field independently and clears its associated error on change.
 * - Validates all fields during submission using [FormValidator].
 */
class FormViewModel @Inject constructor() : ViewModel() {

    private val _formState = MutableStateFlow(FormState())
    val formState: StateFlow<FormState> = _formState.asStateFlow()

    /**
     * Called when the user updates the "Name" field.
     * Clears any previous validation error for that field.
     */
    fun onNameChange(name: String) {
        _formState.value = _formState.value.copy(name = name, nameError = null)
    }

    /**
     * Called when the user updates the "Email" field.
     * Clears any previous validation error for that field.
     */
    fun onEmailChange(email: String) {
        _formState.value = _formState.value.copy(email = email, emailError = null)
    }

    /**
     * Called when the user updates the "Phone" field.
     * Clears any previous validation error for that field.
     */
    fun onPhoneChange(phone: String) {
        _formState.value = _formState.value.copy(phone = phone, phoneError = null)
    }

    /**
     * Called when the user updates the "Promo Code" field.
     * Clears any previous validation error for that field.
     */
    fun onPromoCodeChange(code: String) {
        _formState.value = _formState.value.copy(promoCode = code, promoCodeError = null)
    }

    /**
     * Parses and updates the delivery date from a string input.
     * Clears any previous validation error for that field.
     */
    fun onDateChange(dateStr: String) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        val parsedDate = runCatching { LocalDate.parse(dateStr, formatter) }.getOrNull()

        _formState.update {
            it.copy(
                deliveryDate = parsedDate,
                deliveryDateError = if (parsedDate == null) FormValidationError.DateRequired else null
            )
        }
    }

    /**
     * Called when the user selects a rating.
     * Clears any previous validation error for that field.
     */
    fun onRatingChange(rating: String) {
        _formState.value = _formState.value.copy(rating = rating, ratingError = null)
    }

    /**
     * Runs full form validation and updates the UI state with any validation errors.
     *
     * @return `true` if all fields are valid, `false` otherwise.
     */
    fun validateForm(): Boolean {
        val state = _formState.value

        val nameErr = FormValidator.validateName(state.name)
        val emailErr = FormValidator.validateEmail(state.email)
        val phoneErr = FormValidator.validateNumber(state.phone)
        val promoErr = FormValidator.validatePromoCode(state.promoCode)
        val dateErr = FormValidator.validateDate(state.deliveryDate)
        val ratingErr = FormValidator.validateRating(state.rating)

        val isValid = listOf(nameErr, emailErr, phoneErr, promoErr, dateErr, ratingErr).all { it == null }

        _formState.value = state.copy(
            nameError = nameErr,
            emailError = emailErr,
            phoneError = phoneErr,
            promoCodeError = promoErr,
            deliveryDateError = dateErr,
            ratingError = ratingErr,
            isSubmitting = true,
            formSubmittedSuccessfully = isValid
        )

        return isValid
    }
}
