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
        val service = Service(1,null,null,null,null,"Sevicio estrella",
        Cost(null,100.00.toBigDecimal(), Cost.Currency.MXN),Cost(null,100.00.toBigDecimal(), Cost.Currency.MXN), "Rapido servicio",
        "Servicio de calidad", "Plomeria","Lunes a viernes a las 8:00 am",Service.ServiceStatus.ACTIVE)
        val  accountsTypes = {AccountType(1,AccountType.AccountStatus.ACTIVE,AccountType.AccountType.CLIENT)}
        val account = Account(1,"MiroStar","Mmol18052", accountsTypes)
        val message = Message(service, account,"Hola")
        val validate: ValidationResult = messageValidator.validate(message)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun messageWithSpaces_isIncorrect() {
        val messageValidator = MessageValidator()
        val service = Service(1,null,null,null,null,"Sevicio estrella",
                Cost(null,100.00.toBigDecimal(), Cost.Currency.MXN),Cost(null,100.00.toBigDecimal(), Cost.Currency.MXN), "Rapido servicio",
                "Servicio de calidad", "Plomeria","Lunes a viernes a las 8:00 am",Service.ServiceStatus.ACTIVE)
        val  accountsTypes = {AccountType(1,AccountType.AccountStatus.ACTIVE,AccountType.AccountType.CLIENT)}
        val account = Account(1,"MiroStar","Mmol18052", accountsTypes)
        val message = Message(service, account,"               ")
        val validate: ValidationResult = messageValidator.validate(message)
        org.junit.Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun emptyMessage_isIncorrect() {
        val messageValidator = MessageValidator()
        val service = Service(1,null,null,null,null,"Sevicio estrella",
                Cost(null,100.00.toBigDecimal(), Cost.Currency.MXN),Cost(null,100.00.toBigDecimal(), Cost.Currency.MXN), "Rapido servicio",
                "Servicio de calidad", "Plomeria","Lunes a viernes a las 8:00 am",Service.ServiceStatus.ACTIVE)
        val  accountsTypes = {AccountType(1,AccountType.AccountStatus.ACTIVE,AccountType.AccountType.CLIENT)}
        val account = Account(1,"MiroStar","Mmol18052", accountsTypes)
        val message = Message(service, account,"")
        val validate: ValidationResult = messageValidator.validate(message)
        org.junit.Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }
}