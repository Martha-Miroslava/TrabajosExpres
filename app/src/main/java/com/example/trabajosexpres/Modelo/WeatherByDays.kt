package com.example.trabajosexpres.Modelo

data class WeatherByDays (

        val cod : Int,
        val message : Int,
        val cnt : Int,
        val list : List<WeatherByCity>,
        val city : City
)