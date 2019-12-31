package com.github.pedrobacchini.springionicdomain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class EmailDTO implements Serializable {

    private static final long serialVersionUID = -4583539683902376964L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email invalido")
    private String email;
}
