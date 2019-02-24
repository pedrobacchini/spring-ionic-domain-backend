package com.github.pedrobacchini.springionicdomain.service.validation;

import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import com.github.pedrobacchini.springionicdomain.dto.ClienteDTO;
import com.github.pedrobacchini.springionicdomain.repository.ClienteRepository;
import com.github.pedrobacchini.springionicdomain.resource.exception.FieldMessage;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

   private final HttpServletRequest request;
   private final ClienteRepository clienteRepository;

   public ClienteUpdateValidator(HttpServletRequest request, ClienteRepository clienteRepository) {
       this.request = request;
       this.clienteRepository = clienteRepository;
   }

   public void initialize(ClienteUpdate constraint) {
   }

   public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {

      @SuppressWarnings("unchecked")
      Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
      Integer uriId = Integer.parseInt(map.get("id"));

      List<FieldMessage> list = new ArrayList<>();

      Optional<Cliente> clienteOptional = clienteRepository.findByEmail(clienteDTO.getEmail());
      clienteOptional.ifPresent(cliente -> {
          if(!cliente.getId().equals(uriId))
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
