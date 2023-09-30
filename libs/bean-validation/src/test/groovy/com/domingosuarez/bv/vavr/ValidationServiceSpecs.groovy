package com.domingosuarez.bv.vavr

import com.domingosuarez.error.Failure
import spock.lang.Specification

class ValidationServiceSpecs extends Specification {
    def foo() {
        given:
            def validationUtil = new ValidationService()
            def person = new Person()
        when:
            def validationResult = validationUtil.validate(person)
        then:
            validationResult.isLeft()

            def failure = validationResult.getLeft()

            failure.type == Failure.Type.VALIDATION
            failure.details.size() == 1
        and:
            def validate = validationUtil.validate(null)
        then:
            validate
            def left = validate.getLeft()
            left
            left.getDetails().size() == 0
    }
}
