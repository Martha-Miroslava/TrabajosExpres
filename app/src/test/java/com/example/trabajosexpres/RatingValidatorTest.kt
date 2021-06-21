package com.example.trabajosexpres

import br.com.fluentvalidator.context.ValidationResult
import com.example.trabajosexpres.Model.Rating
import com.example.trabajosexpres.Validator.RatingValidator
import junit.framework.Assert
import org.hamcrest.core.IsEqual
import org.junit.Test

class RatingValidatorTest {

    @Test
    fun rating_isCorrect() {
        val ratingValidator = RatingValidator()
        val rating = Rating(1,"Excelente servicio", 5)
        val validate: ValidationResult = ratingValidator.validate(rating)
        Assert.assertTrue(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(0));
    }

    @Test
    fun ratingZero_isIncorrect() {
        val ratingValidator = RatingValidator()
        val rating = Rating(1,"Excelente servicio", 0)
        val validate: ValidationResult = ratingValidator.validate(rating)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun ratingGreaterThanFive_isIncorrect() {
        val ratingValidator = RatingValidator()
        val rating = Rating(1,"Excelente servicio", 6)
        val validate: ValidationResult = ratingValidator.validate(rating)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(1));
    }

    @Test
    fun emptyComment_isIncorrect() {
        val ratingValidator = RatingValidator()
        val rating = Rating(1,"", 5)
        val validate: ValidationResult = ratingValidator.validate(rating)
        Assert.assertFalse(validate.isValid)
        org.junit.Assert.assertThat(validate.getErrors().size, IsEqual.equalTo(2));
    }

}