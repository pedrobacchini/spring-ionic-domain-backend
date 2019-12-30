package com.github.pedrobacchini.springionicdomain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class CredenciaisDTO implements Serializable {

    private static final long serialVersionUID = -5490988309660789925L;

    private String email;
    private String senha;
}
