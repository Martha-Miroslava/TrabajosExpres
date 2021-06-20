package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.*
import com.example.trabajosexpres.Validator.AccountValidator
import junit.framework.Assert
import org.hamcrest.core.IsEqual
import org.junit.Test

class AccountValidatorTest {

    /*@Test
    fun account_isCorrect() {
        val accountValidator = AccountValidator()
        val  accountsTypes = { AccountType(1, AccountType.AccountStatus.ACTIVE, AccountType.AccountType.CLIENT) }
        val account = Account(1,"MiroStar","Mmol18052#", accountsTypes)
        val validate: ValidationResult = accountValidator.validate(account)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun usernameWithSpaces_isIncorrect() {
        val accountValidator = AccountValidator()
        val  accountsTypes = { AccountType(1, AccountType.AccountStatus.ACTIVE, AccountType.AccountType.CLIENT) }
        val account = Account(1,"     ","Mmol18052#", accountsTypes)
        val validate: ValidationResult = accountValidator.validate(account)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun passwordWithSpaces_isIncorrect() {
        val accountValidator = AccountValidator()
        val  accountsTypes = { AccountType(1, AccountType.AccountStatus.ACTIVE, AccountType.AccountType.CLIENT) }
        val account = Account(1,"MiroStar","        ", accountsTypes)
        val validate: ValidationResult = accountValidator.validate(account)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun emptyUsername_isIncorrect() {
        val accountValidator = AccountValidator()
        val  accountsTypes = { AccountType(1, AccountType.AccountStatus.ACTIVE, AccountType.AccountType.CLIENT) }
        val account = Account(1,"","Mmol180515#", accountsTypes)
        val validate: ValidationResult = accountValidator.validate(account)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }

    @Test
    fun emptyPassword_isIncorrect() {
        val accountValidator = AccountValidator()
        val  accountsTypes = { AccountType(1, AccountType.AccountStatus.ACTIVE, AccountType.AccountType.CLIENT) }
        val account = Account(1,"MiroStar","", accountsTypes)
        val validate: ValidationResult = accountValidator.validate(account)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }

    @Test
    fun passwordLessThanEight_isIncorrect() {
        val accountValidator = AccountValidator()
        val  accountsTypes = { AccountType(1, AccountType.AccountStatus.ACTIVE, AccountType.AccountType.CLIENT) }
        val account = Account(1,"MiroStar","Mm1#", accountsTypes)
        val validate: ValidationResult = accountValidator.validate(account)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }

    @Test
    fun passwordGreaterThanFifteen_isIncorrect() {
        val accountValidator = AccountValidator()
        val  accountsTypes = { AccountType(1, AccountType.AccountStatus.ACTIVE, AccountType.AccountType.CLIENT) }
        val account = Account(1,"MiroStar","Mmol180515#mfkgnfkgnkfn", accountsTypes)
        val validate: ValidationResult = accountValidator.validate(account)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }

    @Test
    fun usernameLessThanFive_isIncorrect() {
        val accountValidator = AccountValidator()
        val  accountsTypes = { AccountType(1, AccountType.AccountStatus.ACTIVE, AccountType.AccountType.CLIENT) }
        val account = Account(1,"Mir","Mmol180515#", accountsTypes)
        val validate: ValidationResult = accountValidator.validate(account)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }

    @Test
    fun usernameGreaterThanFifty_isIncorrect() {
        val accountValidator = AccountValidator()
        val  accountsTypes = { AccountType(1, AccountType.AccountStatus.ACTIVE, AccountType.AccountType.CLIENT) }
        val account = Account(1,"MNGJGNJFDNGJDNGJNFJGNJFNGJNDFJNvnjrngjnfgjndfjgnjdfngjnfdj",
                "Mmol180515#", accountsTypes)
        val validate: ValidationResult = accountValidator.validate(account)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }*/
}