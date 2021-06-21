package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.LogicalPredicate.not
import br.com.fluentvalidator.predicate.ObjectPredicate
import br.com.fluentvalidator.predicate.StringPredicate
import br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull
import com.example.trabajosexpres.Model.Service;

class ServiceValidator : AbstractValidator<Service> {
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(Service::name)
                .must(not(stringEmptyOrNull()))
                .withMessage("Ingresa un nombre correcto")
                .must(StringPredicate.stringMatches("[A-Za-z_\\s_0-9]{5,150}"))
                .withMessage("Ingresa un nombre correcto")
                .must(StringPredicate.stringSizeGreaterThan(0)).`when`(not(stringEmptyOrNull()))
                .withMessage("Ingresa un nombre correcto")

        ruleFor(Service::descriptionService)
                .must(not(stringEmptyOrNull()))
                .withMessage("Ingresa una descripción correcta")
                .must(StringPredicate.stringMatches("[A-Za-z_\\s_0-9]{5,300}"))
                .withMessage("Ingresa una descripción correcta")
                .must(StringPredicate.stringSizeGreaterThan(0)).`when`(not(stringEmptyOrNull()))
                .withMessage("Ingresa una descripción correcta")

        ruleFor(Service::slogan)
                .must(not(stringEmptyOrNull()))
                .withMessage("Ingresa un eslogan correcto")
                .must(StringPredicate.stringMatches("[A-Za-z_\\s_0-9]{5,50}"))
                .withMessage("Ingresa un eslogan correcto")
                .must(StringPredicate.stringSizeGreaterThan(0)).`when`(not(stringEmptyOrNull()))
                .withMessage("Ingresa un eslogan correcto")

        ruleFor(Service::typeService)
                .must(not(stringEmptyOrNull()))
                .withMessage("Ingresa un tipo correcto")
                .must(StringPredicate.stringMatches("[A-Za-z_\\s_0-9]{5,100}"))
                .withMessage("Ingresa un tipo correcto")
                .must(StringPredicate.stringSizeGreaterThan(0)).`when`(not(stringEmptyOrNull()))
                .withMessage("Ingresa un tipo correcto")

        ruleFor(Service::workingHours)
                .must(not(stringEmptyOrNull()))
                .withMessage("Ingresa un horario correcto")
                .must(StringPredicate.stringSizeGreaterThan(0)).`when`(not(stringEmptyOrNull()))
                .withMessage("Ingresa un horario correcto")

        ruleFor(Service::serviceStatus)
                .must(not(ObjectPredicate.nullValue()))
                .withMessage("Ingresa un estado correcto")
    }
}