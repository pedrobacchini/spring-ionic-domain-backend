package com.github.pedrobacchini.springionicdomain.service.validation;

import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import com.github.pedrobacchini.springionicdomain.dto.ClienteNewDTO;
import com.github.pedrobacchini.springionicdomain.enums.TipoCliente;
import com.github.pedrobacchini.springionicdomain.repository.ClienteRepository;
import com.github.pedrobacchini.springionicdomain.resource.exception.FieldMessage;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    private final ClienteRepository clienteRepository;

    public void initialize(ClienteInsert constraint) {
    }

    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())
                && !Utils.isValidCPF(clienteNewDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())
                && !Utils.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(clienteNewDTO.getEmail());
        clienteOptional.ifPresent(cliente -> list.add(new FieldMessage("email", "Email já existe")));

        for (FieldMessage fieldMessage : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    fieldMessage.getMessage()).addPropertyNode(fieldMessage.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
