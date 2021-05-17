package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.LogicalPredicate
import br.com.fluentvalidator.predicate.ObjectPredicate
import br.com.fluentvalidator.predicate.StringPredicate
import com.example.trabajosexpres.Model.MemberATE

class MemberATEValidator : AbstractValidator<MemberATE> {
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(MemberATE::name)
            .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un nombre correcto")
            .must(StringPredicate.stringMatches("^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s]*)+\$"))
            .withMessage("Ingresa un nombre correcto")
            .must(StringPredicate.stringSizeBetween(1, 50)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un nombre correcto")

        ruleFor(MemberATE::lastName)
            .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un apellido correcto")
            .must(StringPredicate.stringMatches("^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s]*)+\$"))
            .withMessage("Ingresa un apellido correcto")
            .must(StringPredicate.stringSizeBetween(1, 50)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un apellido correcto")

        ruleFor(MemberATE::email)
            .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un email correcto")
            .must(StringPredicate.stringMatches("^[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\$"))
            .withMessage("Ingresa un email correcto")
            .must(StringPredicate.stringSizeBetween(10, 254)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un email correcto")

        ruleFor(MemberATE::dateBirth)
            .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
            .withMessage("Ingresa una fecha correcta")
    }
}