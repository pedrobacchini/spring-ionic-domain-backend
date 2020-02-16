package com.github.pedrobacchini.springionicdomain.dto;

import com.github.pedrobacchini.springionicdomain.service.validation.ClientInsert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Setter
@Getter
@ClientInsert
@NoArgsConstructor
public class ClientNewDTO implements Serializable {

    private static final long serialVersionUID = -6207584080809869048L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String name;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email invalido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String cpfOrCnpj;

    private Integer type;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String password;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String street;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String number;

    private String complement;

    private String neighborhood;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String cep;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String phone1;

    private String phone2;

    private String phone3;

    private Integer cityId;
}
