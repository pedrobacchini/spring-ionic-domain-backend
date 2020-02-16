package com.github.pedrobacchini.springionicdomain.service.validation;

import com.github.pedrobacchini.springionicdomain.domain.Client;
import com.github.pedrobacchini.springionicdomain.dto.ClientDTO;
import com.github.pedrobacchini.springionicdomain.repository.ClientRepository;
import com.github.pedrobacchini.springionicdomain.resource.exception.FieldMessage;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

    private final HttpServletRequest request;
    private final ClientRepository clientRepository;

    public ClientUpdateValidator(HttpServletRequest request, ClientRepository clientRepository) {
        this.request = request;
        this.clientRepository = clientRepository;
    }

    public void initialize(ClientUpdate constraint) {
    }

    public boolean isValid(ClientDTO clientDTO, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Optional<Client> clientOptional = clientRepository.findByEmail(clientDTO.getEmail());
        clientOptional.ifPresent(client -> {
            if (!client.getId().equals(uriId))
                list.add(new FieldMessage("email", "Email já existe"));
        });

        for (FieldMessage fieldMessage : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    fieldMessage.getMessage()).addPropertyNode(fieldMessage.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
