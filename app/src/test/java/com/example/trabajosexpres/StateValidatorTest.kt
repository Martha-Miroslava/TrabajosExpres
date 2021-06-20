package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.State
import com.example.trabajosexpres.Validator.StateValidator
import junit.framework.Assert.assertTrue
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Test

class StateValidatorTest {

    @Test
    fun state_isCorrect() {
        /*The name of a state does not accept numbers*/
        val stateValidator = StateValidator()
        val state = State(1,"Veracruz")
        val validate: ValidationResult = stateValidator.validate(state)
        assertTrue(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(0));
    }

    @Test
    fun nameWithSpaces_isIncorrect() {
        val stateValidator = StateValidator()
        val state = State(1,"                   ")
        val validate: ValidationResult = stateValidator.validate(state)
        assertFalse(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(1));
    }

    @Test
    fun emptyName_isIncorrect() {
        val stateValidator = StateValidator()
        val state = State(1,"")
        val validate: ValidationResult = stateValidator.validate(state)
        assertFalse(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(2));
    }
}