package com.coding.products

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.coding.products.form.FormValidator
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class FormValidatorTest {
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun validateNameReturnsErrorWhenEmpty() {
        val result = FormValidator.validateName(context, "")
        assertEquals(context.getString(R.string.error_name_required), result)
    }

    @Test
    fun validateNameReturnsNullWhenValid() {
        val result = FormValidator.validateName(context, "João")
        assertNull(result)
    }

    @Test
    fun validateEmailReturnsErrorWhenEmpty() {
        val result = FormValidator.validateEmail(context, "")
        assertEquals(context.getString(R.string.error_email_required), result)
    }

    @Test
    fun validateEmailReturnsErrorWhenInvalid() {
        val result = FormValidator.validateEmail(context, "not-an-email")
        assertEquals(context.getString(R.string.error_email_invalid), result)
    }

    @Test
    fun validateEmailReturnsNullWhenValid() {
        val result = FormValidator.validateEmail(context, "test@example.com")
        assertNull(result)
    }

    @Test
    fun validatePromoCodeReturnsErrorWhenAccented() {
        val result = FormValidator.validatePromoCode(context, "CÓDIGO")
        assertEquals(context.getString(R.string.error_promo_no_accents), result)
    }

    @Test
    fun validatePromoCodeReturnsErrorWhenFormatIsInvalid() {
        val result = FormValidator.validatePromoCode(context, "INVALID123")
        assertEquals(context.getString(R.string.error_promo_invalid_format), result)
    }

    @Test
    fun validatePromoCodeReturnsNullWhenValid() {
        val result = FormValidator.validatePromoCode(context, "ABC-XYZ")
        assertNull(result)
    }

    @Test
    fun validateDateReturnsErrorWhenNull() {
        val result = FormValidator.validateDate(context, null)
        assertEquals(context.getString(R.string.error_date_required), result)
    }

    @Test
    fun validateDateReturnsErrorWhenDateIsMonday() {
        val date = LocalDate.parse("2024-07-22") // Monday
        val result = FormValidator.validateDate(context, date)
        assertEquals(context.getString(R.string.error_date_monday), result)
    }

    @Test
    fun validateDateReturnsErrorWhenDateIsInTheFuture() {
        val date = LocalDate.now().plusDays(1)
        val result = FormValidator.validateDate(context, date)
        assertEquals(context.getString(R.string.error_date_future), result)
    }

    @Test
    fun validateDateReturnsNullWhenValid() {
        val date = LocalDate.now().minusDays(1)
        val result = FormValidator.validateDate(context, date)
        assertNull(result)
    }

    @Test
    fun validateRatingReturnsErrorWhenEmpty() {
        val result = FormValidator.validateRating(context, "")
        assertEquals(context.getString(R.string.error_rating_required), result)
    }

    @Test
    fun validateRatingReturnsNullWhenValid() {
        val result = FormValidator.validateRating(context, "Excelente")
        assertNull(result)
    }
}
