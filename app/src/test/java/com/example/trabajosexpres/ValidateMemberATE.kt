package com.example.trabajosexpres

import org.junit.Assert.assertFalse
import org.junit.Test
import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidateMemberATE {
    @Test
    fun lastName_isCorrect() {

        assert(validateLastName("Luna Herrera"))
    }

    @Test
    fun lastName_Incorrect() {

        assert(validateLastName("&&Luna"))
    }

    @Test
    fun lastName_IncorrectNumber() {

        assertFalse(validateLastName("1234"))
    }

    @Test
    fun password_isCorrect() {
        assert(validatePassword("Wigetta@123Zz"))
    }

    @Test
    fun password_IncorrectEmpty() {
        assertFalse(validatePassword(""))
    }

    @Test
    fun password_Incorrect() {
        assertFalse(validatePassword("hola mundo"))
    }


    private fun validatePassword(password: String?): Boolean {
        val isValidPassword: Boolean
        val pattern: Pattern = Pattern.compile("^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$")
        val mather: Matcher = pattern.matcher(password)
        isValidPassword = mather.find()
        return isValidPassword
    }

    private fun validateLastName(lastName: String?): Boolean {
        val isValidLastName: Boolean
        val pattern = Pattern
            .compile("[A-Za-z]{3,50}")
        val mather = pattern.matcher(lastName)
        isValidLastName = mather.find()
        return isValidLastName
    }
}