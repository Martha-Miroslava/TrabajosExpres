package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.Country
import com.example.trabajosexpres.Validator.CountryValidator
import junit.framework.Assert.assertTrue
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Test

class CountryValidatorTest {

    @Test
    fun country_isCorrect() {
        /*The name of a country does not accept numbers*/
        val countryValidator = CountryValidator()
        val country = Country(1,"México")
        val validate: ValidationResult = countryValidator.validate(country)
        assertTrue(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(0));
    }

    @Test
    fun nameWithSpaces_isIncorrect() {
        val countryValidator = CountryValidator()
        val country = Country(1,"               ")
        val validate: ValidationResult = countryValidator.validate(country)
        assertFalse(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(1));
    }

    @Test
    fun emptyName_isIncorrect() {
        val countryValidator = CountryValidator()
        val country = Country(1,"")
        val validate: ValidationResult = countryValidator.validate(country)
        assertFalse(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(2));
    }
}