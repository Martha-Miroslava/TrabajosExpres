package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.LogicalPredicate
import br.com.fluentvalidator.predicate.ObjectPredicate
import br.com.fluentvalidator.predicate.StringPredicate
import com.example.trabajosexpres.Model.Account

class AccountValidator : AbstractValidator<Account> {
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(Account::username)
            .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un username correcto")
            .must(StringPredicate.stringMatches("[A-Za-zñÑáéíóúÁÉÍÓÚ0-9]{5,50}"))
            .withMessage("Ingresa un username correcto")
            .must(StringPredicate.stringSizeBetween(5, 50)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa un username correcto")

        ruleFor(Account::password)
            .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa una contraseña correcta1")
            .must(StringPredicate.stringMatches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\$@\$!%*?&#])[A-Za-z\\d\$@\$!%*?&#]{8,15}"))
            .withMessage("Ingresa una contraseña correcta2")
            .must(StringPredicate.stringSizeBetween(8, 15)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
            .withMessage("Ingresa una contraseña correcta3")

        ruleFor(Account::accountType)
            .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
            .withMessage("Ingresa un tipo correcto")
    }
}