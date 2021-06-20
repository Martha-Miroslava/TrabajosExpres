package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.Report
import com.example.trabajosexpres.Validator.ReportValidator
import junit.framework.Assert
import org.hamcrest.core.IsEqual
import org.junit.Test

class ReportValidatorTest {

    @Test
    fun report_isCorrect() {
        val reportValidator = ReportValidator()
        val report = Report(1,"Mal servicio")
        val validate: ValidationResult = reportValidator.validate(report)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun report_isIncorrect() {
        val reportValidator = ReportValidator()
        val report = Report(1,"")
        val validate: ValidationResult = reportValidator.validate(report)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }
}