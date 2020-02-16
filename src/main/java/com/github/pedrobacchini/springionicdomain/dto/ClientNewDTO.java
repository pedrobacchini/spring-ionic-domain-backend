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

    @NotEmpty
    @Length(min = 5, max = 120)
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String cpfOrCnpj;

    private Integer type;

    @NotEmpty
    private String password;

    @NotEmpty
    private String street;

    @NotEmpty
    private String number;

    private String complement;

    private String neighborhood;

    @NotEmpty
    private String cep;

    @NotEmpty
    private String phone1;

    private String phone2;

    private String phone3;

    private Integer cityId;
}
