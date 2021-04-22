package com.example.trabajosexpres

import org.junit.Assert.assertFalse
import org.junit.Test
import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidateMiembroATETest {
    @Test
    fun email_isCorrect() {
        assert(validateEmail("martha_13_5@gmail.com"))
    }

    @Test
    fun name_isCorrect() {

        assert(validateName("Martha"))
    }

    @Test
    fun email_isIncorrect() {
        assertFalse(validateEmail("martha_gmail.com"))
    }

    @Test
    fun name_isIsCorrect() {

        assertFalse(validateName("&&&&&&&"))
    }

    fun validateEmail(email: String?): Boolean {
        val isValidEmail: Boolean
        val pattern: Pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val mather: Matcher = pattern.matcher(email)
        isValidEmail = mather.find()
        return isValidEmail
    }

    fun validateName(name: String?): Boolean {
        val isValidName: Boolean
        val pattern = Pattern
                .compile("[A-Za-z]{3,50}")
        val mather = pattern.matcher(name)
        isValidName = mather.find()
        return isValidName
    }
}