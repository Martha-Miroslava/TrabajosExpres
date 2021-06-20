package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.LogicalPredicate
import br.com.fluentvalidator.predicate.ObjectPredicate
import br.com.fluentvalidator.predicate.StringPredicate
import com.example.trabajosexpres.Model.Account
import com.example.trabajosexpres.Model.AccountRecover
import com.example.trabajosexpres.Model.MemberATE

class AccountRevoverValidator : AbstractValidator<AccountRecover> {
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(AccountRecover::email)
                .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un email correcto")
                .must(StringPredicate.stringMatches("^[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\$"))
                .withMessage("Ingresa un email correcto")
                .must(StringPredicate.stringSizeBetween(5, 254)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un email correcto")

        ruleFor(AccountRecover::password)
                .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa una contraseña correcta")
                .must(StringPredicate.stringMatches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\$@\$!%*?&#])[A-Za-z\\d\$@\$!%*?&#]{8,15}"))
                .withMessage("Ingresa una contraseña correcta")
                .must(StringPredicate.stringSizeBetween(8, 15)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa una contraseña correcta")

        ruleFor(AccountRecover::code)
                .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
                .withMessage("Ingresa un tipo correcto")
    }
}