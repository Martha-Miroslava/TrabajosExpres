package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.Cost
import com.example.trabajosexpres.Validator.CostValidator
import junit.framework.Assert
import org.hamcrest.core.IsEqual
import org.junit.Test
import java.math.BigDecimal

class CostValidatorTest {

    @Test
    fun cost_isCorrect() {
        val costValidator = CostValidator()
        val costConvert = BigDecimal.valueOf(100)
        val cost = Cost(null,costConvert, Cost.Currency.MXN)
        val validate: ValidationResult = costValidator.validate(cost)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun costZero_isIncorrect() {
        val costValidator = CostValidator()
        val costConvert = BigDecimal.valueOf(0)
        val cost = Cost(null,costConvert, Cost.Currency.MXN)
        val validate: ValidationResult = costValidator.validate(cost)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun costOneThousand_isIncorrect() {
        val costValidator = CostValidator()
        val costConvert = BigDecimal.valueOf(1000)
        val cost = Cost(null,costConvert, Cost.Currency.MXN)
        val validate: ValidationResult = costValidator.validate(cost)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }
}