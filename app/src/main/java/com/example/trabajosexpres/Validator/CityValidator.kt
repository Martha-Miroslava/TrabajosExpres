package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.LogicalPredicate.not
import br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull
import br.com.fluentvalidator.predicate.StringPredicate.stringMatches
import br.com.fluentvalidator.predicate.StringPredicate.stringSizeBetween

import com.example.trabajosexpres.Model.City

class CityValidator : AbstractValidator<City> {
    constructor() : super()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(City::name)
                .must(not(stringEmptyOrNull()))
                .withMessage("Ingresa una ciudad correcta")
                .must(stringMatches("^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s]*)+\$"))
                .withMessage("Ingresa una ciudad correcta")
                .must(stringSizeBetween(1,50)).`when`(not(stringEmptyOrNull()))
                .withMessage("Ingresa una ciudad correcta")
    }
}