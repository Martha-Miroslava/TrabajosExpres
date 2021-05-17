package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.LogicalPredicate
import br.com.fluentvalidator.predicate.StringPredicate
import com.example.trabajosexpres.Model.Report

class ReportValidator : AbstractValidator<Report> {
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(Report::reason)
                .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un razón correcta")
                .must(StringPredicate.stringMatches("[a-zA-Z\\s+]{4,}"))
                .withMessage("Ingresa un razón correcta")
                .must(StringPredicate.stringSizeGreaterThan(0)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un razón correcta")
    }
}