package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.LogicalPredicate
import br.com.fluentvalidator.predicate.ObjectPredicate
import br.com.fluentvalidator.predicate.StringPredicate
import br.com.fluentvalidator.predicate.StringPredicate.isDate
import com.example.trabajosexpres.Model.Account
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
            .must(StringPredicate.stringSizeBetween(2, 50)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un nombre correcto")

        ruleFor(MemberATE::lastName)
            .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un apellido correcto")
            .must(StringPredicate.stringMatches("^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s]*)+\$"))
            .withMessage("Ingresa un apellido correcto")
            .must(StringPredicate.stringSizeBetween(2, 50)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un apellido correcto")

        ruleFor(MemberATE::email)
            .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un email correcto")
            .must(StringPredicate.stringMatches("^[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\$"))
            .withMessage("Ingresa un email correcto")
            .must(StringPredicate.stringSizeBetween(10, 254)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un email correcto")

        ruleFor(MemberATE::dateBirth)
            .must(StringPredicate.stringMatches("([12]\\d{3}/(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01]))"))
            .withMessage("Ingresa una fecha correcta")

        ruleFor(MemberATE::username)
                .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un username correcto")
                .must(StringPredicate.stringMatches("[A-Za-zñÑáéíóúÁÉÍÓÚ0-9]{5,50}"))
                .withMessage("Ingresa un username correcto")
                .must(StringPredicate.stringSizeBetween(5, 50)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un username correcto")

        ruleFor(MemberATE::password)
                .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa una contraseña correcta")
                .must(StringPredicate.stringMatches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\$@\$!%*?&#])[A-Za-z\\d\$@\$!%*?&#]{8,15}"))
                .withMessage("Ingresa una contraseña correcta")
                .must(StringPredicate.stringSizeBetween(8, 15)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa una contraseña correcta")

        ruleFor(MemberATE::accountType)
                .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
                .withMessage("Ingresa un tipo correcto")

        ruleFor(MemberATE::idCity)
                .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
                .withMessage("Ingresa una ciudad correcto")

        ruleFor(MemberATE::memberATEStatus)
                .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
                .withMessage("Ingresa una estatus correcto")
    }
}