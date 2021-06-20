package com.example.trabajosexpres.Model

 data class Token (
        /* username of account */
        val token: String,

        /* encrypted password */
        val memberATEType: Int,
        val idMemberATE: Int,
        val idCity: Int
 )
