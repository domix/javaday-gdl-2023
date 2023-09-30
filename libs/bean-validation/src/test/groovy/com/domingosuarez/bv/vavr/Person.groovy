package com.domingosuarez.bv.vavr

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class Person {
    @NotNull
    @Size(min = 2, max = 14)
    String name
}
