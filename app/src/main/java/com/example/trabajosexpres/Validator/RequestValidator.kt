package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.LocalDatePredicate.localDateAfterOrEqual
import br.com.fluentvalidator.predicate.LogicalPredicate
import br.com.fluentvalidator.predicate.ObjectPredicate
import br.com.fluentvalidator.predicate.StringPredicate
import br.com.fluentvalidator.predicate.StringPredicate.isTime
import com.example.trabajosexpres.Model.Request
import java.time.LocalDate

class RequestValidator : AbstractValidator<Request> {
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(Request::address)
                .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa una dirección correcta")
                .must(StringPredicate.stringMatches("[A-Za-z_\\s_0-9+]{4,}"))
                .withMessage("Ingresa una dirección correcta")
                .must(StringPredicate.stringSizeGreaterThan(0)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa una dirección correcta")

        ruleFor(Request::date)
                .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
                .withMessage("Ingresa una fecha correcta")
                .must(localDateAfterOrEqual(currentDate))
                .withMessage("Ingresa una fecha correcta")

        ruleFor(Request::requestStatus)
                .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
                .withMessage("Ingresa un estado correcto")

        ruleFor(Request::time)
                .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
                .withMessage("Ingresa una hora correcta")
                .must(isTime("HH:mm:ss"))
                .withMessage("Ingresa una hora correcta")

        ruleFor(Request::trouble)
                .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un problema correcto")
                .must(StringPredicate.stringMatches("[A-Za-z_\\s_0-9+]{4,}"))
                .withMessage("Ingresa un problema correcto")
                .must(StringPredicate.stringSizeGreaterThan(0)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un problema correcto")

    }
}