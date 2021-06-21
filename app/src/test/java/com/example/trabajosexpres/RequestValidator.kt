package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.Request
import com.example.trabajosexpres.Validator.RequestValidator
import junit.framework.Assert
import org.hamcrest.core.IsEqual
import org.junit.Test
import java.time.LocalDate

class RequestValidator {

    @Test
    fun request_isCorrect() {
        val requestValidator = RequestValidator()
        val request = Request("Calle xalapa colonia del maestro","2020/05/06", 1,"12:00:00",
                "Se descompuso mi lavabo",1)
        val validate: ValidationResult = requestValidator.validate(request)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun requestWithSpaces_isIncorrect() {
        val requestValidator = RequestValidator()
        val request = Request("    ","        ", 0,"    ",
                "   ",2)
        val validate: ValidationResult = requestValidator.validate(request)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(3));
    }

    @Test
    fun emptyRequest_isIncorrect() {
        val requestValidator = RequestValidator()
        val request = Request("","", 0,"",
                "",3)
        val validate: ValidationResult = requestValidator.validate(request)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(6));
    }
}