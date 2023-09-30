package com.domingosuarez.app.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePersonCommand {
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
}
