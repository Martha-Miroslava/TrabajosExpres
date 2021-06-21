package com.example.trabajosexpres.Model

data class RequestReceived (

    val idRequest: Int,
    /* client address */
    val address: kotlin.String,
    /* Date the service is to be performed */
    val date: String,
    /* status of request */
    val requestStatus: Int,
    /* Time the service is to be performed */
    val time: kotlin.String,
    /* problem to solve */
    val trouble: String,

    val idMemberATE: String,

    val idService: Int
)