package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.MemberATE
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
        val currentDate = LocalDate.of(2021,10,12)
        val date = LocalDate.of(2000,7,5)
        val memberATE = MemberATE(null,"martha_13@outlookcom",date,"Ortiz",
                "Martha",null)
        val request = Request("Calle xalapa colonia del maestro",currentDate, Request.RequestStatus.REQUEST,"12:00:00",
                "Se descompuso mi lavabo",memberATE)
        val validate: ValidationResult = requestValidator.validate(request)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun requestWithSpaces_isIncorrect() {
        val requestValidator = RequestValidator()
        val currentDate = LocalDate.of(2021,10,12)
        val date = LocalDate.of(2019,7,5)
        val memberATE = MemberATE(null,"martha_13@outlookcom",date,"Ortiz",
                "Martha",null)
        val request = Request("    ",currentDate, Request.RequestStatus.REQUEST,"    ",
                "   ",memberATE)
        val validate: ValidationResult = requestValidator.validate(request)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }

    @Test
    fun emptyRequest_isIncorrect() {
        val requestValidator = RequestValidator()
        val currentDate = LocalDate.of(2021,10,12)
        val date = LocalDate.of(2019,7,5)
        val memberATE = MemberATE(null,"martha_13@outlookcom",date,"Ortiz",
                "Martha",null)
        val request = Request("",currentDate, Request.RequestStatus.REQUEST,"",
                "",memberATE)
        val validate: ValidationResult = requestValidator.validate(request)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(5));
    }
}