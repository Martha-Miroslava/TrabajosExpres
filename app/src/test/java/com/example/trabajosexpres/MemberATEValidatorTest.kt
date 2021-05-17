package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.MemberATE
import com.example.trabajosexpres.Validator.MemberATEValidator
import junit.framework.Assert
import org.hamcrest.core.IsEqual
import org.junit.Test
import java.time.LocalDate

class MemberATEValidatorTest {

    @Test
    fun memberATE_isCorrect() {
        val memberATEValidator = MemberATEValidator()
        val date = LocalDate.of(2000,7,5)
        val memberATE = MemberATE(null,"martha_15_7@outlook.com",date,"Ortiz",
        "Martha",null)
        val validate: ValidationResult = memberATEValidator.validate(memberATE)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun memberATEWithSpaces_isIncorrect() {
        val memberATEValidator = MemberATEValidator()
        val date = LocalDate.of(2000,7,5)
        val memberATE = MemberATE(null,"          ",date,"         ",
                "          ",null)
        val validate: ValidationResult = memberATEValidator.validate(memberATE)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(3));
    }

    @Test
    fun emptyMemberATE_isIncorrect() {
        val memberATEValidator = MemberATEValidator()
        val date = LocalDate.of(2000,7,5)
        val memberATE = MemberATE(null,"",date,"",
                "",null)
        val validate: ValidationResult = memberATEValidator.validate(memberATE)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(6));
    }


    @Test
    fun email1_isIncorrect() {
        val memberATEValidator = MemberATEValidator()
        val date = LocalDate.of(2000,7,5)
        val memberATE = MemberATE(null,"martha_14_outlook.com",date,"Ortiz",
                "Martha",null)
        val validate: ValidationResult = memberATEValidator.validate(memberATE)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun email2_isIncorrect() {
        val memberATEValidator = MemberATEValidator()
        val date = LocalDate.of(2000,7,5)
        val memberATE = MemberATE(null,"martha_13@.com",date,"Ortiz",
                "Martha",null)
        val validate: ValidationResult = memberATEValidator.validate(memberATE)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun email3_isIncorrect() {
        val memberATEValidator = MemberATEValidator()
        val date = LocalDate.of(2000,7,5)
        val memberATE = MemberATE(null,"martha_13@outlookcom",date,"Ortiz",
                "Martha",null)
        val validate: ValidationResult = memberATEValidator.validate(memberATE)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun email4_isIncorrect() {
        val memberATEValidator = MemberATEValidator()
        val date = LocalDate.of(2000,7,5)
        val memberATE = MemberATE(null,"martha_15@outlook.",date,"Ortiz",
                "Martha",null)
        val validate: ValidationResult = memberATEValidator.validate(memberATE)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }
}