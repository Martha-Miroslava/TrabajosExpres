package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.MemberATE
import com.example.trabajosexpres.Validator.MemberATEValidator
import junit.framework.Assert
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Test

class MemberATEValidatorTest {

    @Test
    fun memberATE_isCorrect() {
        val memberATEValidator = MemberATEValidator()
        val member = MemberATE(0, "martha@outlook.com", "2000/05/05", "Ortiz",
            "Martha", "Mirostar12", "Wigettaz4BTS#", 1, 1, 1)
        val validate: ValidationResult = memberATEValidator.validate(member)
        Assert.assertTrue(validate.isValid)
        assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun memberATE_isIncorrect() {
        val memberATEValidator = MemberATEValidator()
        val member = MemberATE(0, "", "", "",
            "", "", "", 1, 1, 1)
        val validate: ValidationResult = memberATEValidator.validate(member)
        assertFalse(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(11));

    }

    @Test
    fun memberATE_incorrectWithSpaces() {
        val memberATEValidator = MemberATEValidator()
        val member = MemberATE(0, "           ", "       ", "       ",
            "       ", "        ", "            ", 1, 1, 1)
        val validate: ValidationResult = memberATEValidator.validate(member)
        assertFalse(validate.isValid)
        assertThat(validate.getErrors().size, equalTo(6));

    }



}