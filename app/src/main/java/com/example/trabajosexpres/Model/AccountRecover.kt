package com.example.trabajosexpres.Model

data class AccountRecover (
    val email: String,
    val password: String,
    val code: Int
)