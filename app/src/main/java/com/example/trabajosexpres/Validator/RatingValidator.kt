package com.example.trabajosexpres.Validator

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.fluentvalidator.AbstractValidator
import br.com.fluentvalidator.predicate.ComparablePredicate
import br.com.fluentvalidator.predicate.LogicalPredicate
import br.com.fluentvalidator.predicate.ObjectPredicate
import br.com.fluentvalidator.predicate.StringPredicate
import com.example.trabajosexpres.Model.Rating

class RatingValidator : AbstractValidator<Rating> {
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rules() {
        ruleFor(Rating::comment)
                .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un comentario correcto")
                .must(StringPredicate.stringMatches("[a-zA-Z\\s+]{4,}"))
                .withMessage("Ingresa un comentario correcto")
                .must(StringPredicate.stringSizeGreaterThan(0)).`when`(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                .withMessage("Ingresa un comentario correcto")

        ruleFor(Rating::rating)
                .must(LogicalPredicate.not(ObjectPredicate.nullValue()))
                .withMessage("Ingresa una calificación correcta")
                .must(ComparablePredicate.greaterThanOrEqual(1))
                .withMessage("Ingresa una calificación correcta")
                .must(ComparablePredicate.lessThanOrEqual(5))
                .withMessage("Ingresa una calificación correcta")
    }
}