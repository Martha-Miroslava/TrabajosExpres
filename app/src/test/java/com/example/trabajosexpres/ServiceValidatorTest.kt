package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.Cost
import com.example.trabajosexpres.Model.Service
import com.example.trabajosexpres.Validator.ServiceValidator
import junit.framework.Assert
import org.hamcrest.core.IsEqual
import org.junit.Test
import java.math.BigDecimal

class ServiceValidatorTest {

    @Test
    fun service_isCorrect() {
        val serviceValidator = ServiceValidator()
        val service = Service(1,1,1,"Sevicio estrella",100.toDouble(),
                150.toDouble(), "Rapido servicio",
                "Servicio de calidad", "Plomeria","Lunes a viernes a las 8:00 am", 1)
        val validate: ValidationResult = serviceValidator.validate(service)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun service_isIncorrect() {
        val serviceValidator = ServiceValidator()
        val service = Service(1,1,1,"",100.toDouble(),
                150.toDouble(), "",
                "", "","", 1)
        val validate: ValidationResult = serviceValidator.validate(service)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(9));
    }
}