package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.Service
import com.example.trabajosexpres.Model.Cost
import com.example.trabajosexpres.Model.Account
import com.example.trabajosexpres.Model.AccountType
import com.example.trabajosexpres.Model.Message
import com.example.trabajosexpres.Validator.MessageValidator
import junit.framework.Assert
import org.hamcrest.core.IsEqual
import org.junit.Test

class MessageValidatorTest {

    @Test
    fun message_isCorrect() {
        val messageValidator = MessageValidator()
        val  accountsTypes = {AccountType(1,AccountType.AccountStatus.ACTIVE,AccountType.AccountType.CLIENT)}
        val message = Message(1, 1,"Hola")
        val validate: ValidationResult = messageValidator.validate(message)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun messageWithSpaces_isIncorrect() {
        val messageValidator = MessageValidator()
        val message = Message(0, 0,"               ")
        val validate: ValidationResult = messageValidator.validate(message)
        org.junit.Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun emptyMessage_isIncorrect() {
        val messageValidator = MessageValidator()
        val message = Message(0, 0,"")
        val validate: ValidationResult = messageValidator.validate(message)
        org.junit.Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }
}