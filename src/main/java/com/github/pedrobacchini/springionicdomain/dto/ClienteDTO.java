package com.github.pedrobacchini.springionicdomain.dto;

import com.github.pedrobacchini.springionicdomain.SpringIonicDomainApplication;
import com.github.pedrobacchini.springionicdomain.config.ApplicationProperties;
import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import com.github.pedrobacchini.springionicdomain.service.validation.ClienteUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@ClienteUpdate
@NoArgsConstructor
public class ClienteDTO implements Serializable {

    private static final long serialVersionUID = 7783415683050919389L;

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email invalido")
    private String email;

    private String picture;

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        ApplicationProperties applicationProperties = SpringIonicDomainApplication.getBean(ApplicationProperties.class);
        this.picture = applicationProperties.getImage().getProfile().getBucketBaseUrl() + id +".jpg";
    }
}
