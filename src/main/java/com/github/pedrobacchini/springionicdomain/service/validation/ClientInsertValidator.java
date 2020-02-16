package com.github.pedrobacchini.springionicdomain.service.validation;

import com.github.pedrobacchini.springionicdomain.domain.Client;
import com.github.pedrobacchini.springionicdomain.dto.ClientNewDTO;
import com.github.pedrobacchini.springionicdomain.enums.ClientType;
import com.github.pedrobacchini.springionicdomain.repository.ClientRepository;
import com.github.pedrobacchini.springionicdomain.resource.exception.FieldMessage;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

    private final ClientRepository clientRepository;

    public void initialize(ClientInsert constraint) {
    }

    public boolean isValid(ClientNewDTO clientNewDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (clientNewDTO.getType().equals(ClientType.INDIVIDUAL.getCod())
                && !Utils.isValidCPF(clientNewDTO.getCpfOrCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (clientNewDTO.getType().equals(ClientType.LEGAL_ENTITY.getCod())
                && !Utils.isValidCNPJ(clientNewDTO.getCpfOrCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Optional<Client> clientOptional = clientRepository.findByEmail(clientNewDTO.getEmail());
        clientOptional.ifPresent(client -> list.add(new FieldMessage("email", "Email já existe")));

        for (FieldMessage fieldMessage : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    fieldMessage.getMessage()).addPropertyNode(fieldMessage.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
