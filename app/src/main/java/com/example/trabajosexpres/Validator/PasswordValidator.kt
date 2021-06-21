package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.LogicalPredicate
import br.com.fluentvalidator.predicate.StringPredicate
import com.example.trabajosexpres.Model.Password

class PasswordValidator : AbstractValidator<Password> {
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(Password::password)
                .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa una contraseña correcta")
                .must(StringPredicate.stringMatches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\$@\$!%*?&#])[A-Za-z\\d\$@\$!%*?&#]{8,15}"))
                .withMessage("Ingresa una contraseña correcta")
                .must(StringPredicate.stringSizeBetween(8, 15)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa una contraseña correcta")
    }
}