package com.example.trabajosexpres.Model

data class CodeConfirmation (
    /* encrypted username */
    val username: String,
    /* encrypted password */
    val password: String,
    val email: String
)