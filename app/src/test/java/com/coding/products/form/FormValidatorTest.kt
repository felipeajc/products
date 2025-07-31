package com.coding.products.form

import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

/**
 * Unit tests for [FormValidator].
 * These tests validate expected behavior of each field independently.
 */
class FormValidatorTest {

    @Test
    fun validateNameReturnsErrorWhenEmpty() {
        val result = FormValidator.validateName("")
        assertEquals(FormValidationError.NameRequired, result)
    }

    @Test
    fun validateNameReturnsNullWhenValid() {
        val result = FormValidator.validateName("João")
        assertNull(result)
    }

    @Test
    fun validateEmailReturnsErrorWhenEmpty() {
        val result = FormValidator.validateEmail("")
        assertEquals(FormValidationError.EmailRequired, result)
    }

    @Test
    fun validateEmailReturnsErrorWhenInvalid() {
        val result = FormValidator.validateEmail("not-an-email")
        assertEquals(FormValidationError.EmailInvalid, result)
    }

    @Test
    fun validateEmailReturnsNullWhenValid() {
        val result = FormValidator.validateEmail("test@example.com")
        assertNull(result)
    }

    @Test
    fun validateNumberReturnsErrorWhenEmpty() {
        val result = FormValidator.validateNumber("")
        assertEquals(FormValidationError.PhoneRequired, result)
    }

    @Test
    fun validateNumberReturnsErrorWhenNotDigits() {
        val result = FormValidator.validateNumber("123-abc")
        assertEquals(FormValidationError.PhoneInvalid, result)
    }

    @Test
    fun validateNumberReturnsNullWhenValid() {
        val result = FormValidator.validateNumber("12345678")
        assertNull(result)
    }

    @Test
    fun validatePromoCodeReturnsErrorWhenEmpty() {
        val result = FormValidator.validatePromoCode("")
        assertEquals(FormValidationError.PromoRequired, result)
    }

    @Test
    fun validatePromoCodeReturnsErrorWhenAccented() {
        val result = FormValidator.validatePromoCode("CÓDIGO")
        assertEquals(FormValidationError.PromoAccentsNotAllowed, result)
    }

    @Test
    fun validatePromoCodeReturnsErrorWhenInvalidFormat() {
        val result = FormValidator.validatePromoCode("INVALID123")
        assertEquals(FormValidationError.PromoInvalidFormat, result)
    }

    @Test
    fun validatePromoCodeReturnsNullWhenValid() {
        val result = FormValidator.validatePromoCode("ABC-XYZ")
        assertNull(result)
    }

    @Test
    fun validateDateReturnsErrorWhenNull() {
        val result = FormValidator.validateDate(null)
        assertEquals(FormValidationError.DateRequired, result)
    }

    @Test
    fun validateDateReturnsErrorWhenMonday() {
        val date = LocalDate.of(2024, 7, 22) // A known Monday
        val result = FormValidator.validateDate(date)
        assertEquals(FormValidationError.DateIsMonday, result)
    }

    @Test
    fun validateDateReturnsErrorWhenInFuture() {
        val date = LocalDate.now().plusDays(1)
        val result = FormValidator.validateDate(date)
        assertEquals(FormValidationError.DateInFuture, result)
    }

    @Test
    fun validateDateReturnsNullWhenValid() {
        val date = LocalDate.now().minusDays(1)
        val result = FormValidator.validateDate(date)
        assertNull(result)
    }

    @Test
    fun validateRatingReturnsErrorWhenEmpty() {
        val result = FormValidator.validateRating("")
        assertEquals(FormValidationError.RatingRequired, result)
    }

    @Test
    fun validateRatingReturnsNullWhenValid() {
        val result = FormValidator.validateRating("Excelente")
        assertNull(result)
    }
}
