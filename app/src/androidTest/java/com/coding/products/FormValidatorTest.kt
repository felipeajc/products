package com.coding.products

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.coding.products.form.FormValidator
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

/**
 * Unit tests for [FormValidator]. Each test hits one validation method with
 * a specific scenario to make sure the logic behaves as expected.
 */
class FormValidatorTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    /**
     * Should return error when name is empty.
     */
    @Test
    fun validateNameReturnsErrorWhenEmpty() {
        val result = FormValidator.validateName(context, "")
        assertEquals(context.getString(R.string.error_name_required), result)
    }

    /**
     * Should return null when name is valid.
     */
    @Test
    fun validateNameReturnsNullWhenValid() {
        val result = FormValidator.validateName(context, "João")
        assertNull(result)
    }

    /**
     * Should return error when email is empty.
     */
    @Test
    fun validateEmailReturnsErrorWhenEmpty() {
        val result = FormValidator.validateEmail(context, "")
        assertEquals(context.getString(R.string.error_email_required), result)
    }

    /**
     * Should return error when email format is invalid.
     */
    @Test
    fun validateEmailReturnsErrorWhenInvalid() {
        val result = FormValidator.validateEmail(context, "not-an-email")
        assertEquals(context.getString(R.string.error_email_invalid), result)
    }

    /**
     * Should return null when email is valid.
     */
    @Test
    fun validateEmailReturnsNullWhenValid() {
        val result = FormValidator.validateEmail(context, "test@example.com")
        assertNull(result)
    }

    /**
     * Should return error when promo code has accents.
     */
    @Test
    fun validatePromoCodeReturnsErrorWhenAccented() {
        val result = FormValidator.validatePromoCode(context, "CÓDIGO")
        assertEquals(context.getString(R.string.error_promo_no_accents), result)
    }

    /**
     * Should return error when promo code format is off.
     */
    @Test
    fun validatePromoCodeReturnsErrorWhenFormatIsInvalid() {
        val result = FormValidator.validatePromoCode(context, "INVALID123")
        assertEquals(context.getString(R.string.error_promo_invalid_format), result)
    }

    /**
     * Should return null when promo code is valid.
     */
    @Test
    fun validatePromoCodeReturnsNullWhenValid() {
        val result = FormValidator.validatePromoCode(context, "ABC-XYZ")
        assertNull(result)
    }

    /**
     * Should return error when date is null.
     */
    @Test
    fun validateDateReturnsErrorWhenNull() {
        val result = FormValidator.validateDate(context, null)
        assertEquals(context.getString(R.string.error_date_required), result)
    }

    /**
     * Should return error when date is on a Monday.
     */
    @Test
    fun validateDateReturnsErrorWhenDateIsMonday() {
        val date = LocalDate.parse("2024-07-22") // Monday
        val result = FormValidator.validateDate(context, date)
        assertEquals(context.getString(R.string.error_date_monday), result)
    }

    /**
     * Should return error when date is in the future.
     */
    @Test
    fun validateDateReturnsErrorWhenDateIsInTheFuture() {
        val date = LocalDate.now().plusDays(1)
        val result = FormValidator.validateDate(context, date)
        assertEquals(context.getString(R.string.error_date_future), result)
    }

    /**
     * Should return null when date is valid.
     */
    @Test
    fun validateDateReturnsNullWhenValid() {
        val date = LocalDate.now().minusDays(1)
        val result = FormValidator.validateDate(context, date)
        assertNull(result)
    }

    /**
     * Should return error when rating is empty.
     */
    @Test
    fun validateRatingReturnsErrorWhenEmpty() {
        val result = FormValidator.validateRating(context, "")
        assertEquals(context.getString(R.string.error_rating_required), result)
    }

    /**
     * Should return null when rating is valid.
     */
    @Test
    fun validateRatingReturnsNullWhenValid() {
        val result = FormValidator.validateRating(context, "Excelente")
        assertNull(result)
    }
}
