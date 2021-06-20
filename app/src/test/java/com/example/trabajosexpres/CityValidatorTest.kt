package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.City
import com.example.trabajosexpres.Model.State
import com.example.trabajosexpres.Validator.CityValidator
import junit.framework.Assert.assertTrue
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CityValidatorTest {

    @Test
    fun city_isCorrect() {
        /*The name of a city does not accept numbers*/
        val cityValidator = CityValidator()
        val state = State(1,"Veracruz")
        val city = City(1,"Xalapa", state)
        val validate: ValidationResult = cityValidator.validate(city)
        assertTrue(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(0));
    }

    @Test
    fun nameWithSpaces_isIncorrect() {
        val cityValidator = CityValidator()
        val state = State(1,"Veracruz")
        val city = City(1,"                           ",state)
        val validate: ValidationResult = cityValidator.validate(city)
        assertFalse(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(1));
    }

    @Test
    fun emptyName_isIncorrect() {
        val cityValidator = CityValidator()
        val city = City(1,"")
        val validate: ValidationResult = cityValidator.validate(city)
        assertFalse(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(2));
    }
}