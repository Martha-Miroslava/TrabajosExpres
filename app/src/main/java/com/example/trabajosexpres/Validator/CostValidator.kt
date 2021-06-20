package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual
import br.com.fluentvalidator.predicate.ComparablePredicate.lessThanOrEqual
import br.com.fluentvalidator.predicate.LogicalPredicate.not
import br.com.fluentvalidator.predicate.ObjectPredicate.nullValue
import com.example.trabajosexpres.Model.Cost
import java.math.BigDecimal

class CostValidator : AbstractValidator<Cost> {
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(Cost::cost)
            .must(not(nullValue()))
            .withMessage("Ingresa un costo correcto")
            .must(greaterThanOrEqual(BigDecimal.valueOf(1)))
            .withMessage("Ingresa un costo correcto")
            .must(lessThanOrEqual(BigDecimal.valueOf(999)))
            .withMessage("Ingresa un costo correcto")

        ruleFor(Cost::currency)
            .must(not(nullValue()))
            .withMessage("Ingresa una moneda correcta")
    }
}