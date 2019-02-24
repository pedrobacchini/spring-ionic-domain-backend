package com.github.pedrobacchini.springionicdomain.service.validation;

import com.github.pedrobacchini.springionicdomain.dto.ClienteNewDTO;
import com.github.pedrobacchini.springionicdomain.enums.TipoCliente;
import com.github.pedrobacchini.springionicdomain.resource.exception.FieldMessage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

   public void initialize(ClienteInsert constraint) {
   }

   public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
      List<FieldMessage> list = new ArrayList<>();

      System.out.println(clienteNewDTO.toString());

      if(clienteNewDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())
              && !Utils.isValidCPF(clienteNewDTO.getCpfOuCnpj())){
         list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
      }

      if(clienteNewDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())
              && !Utils.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())){
         list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
      }

      for (FieldMessage fieldMessage : list) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(
                 fieldMessage.getMessage()).addPropertyNode(fieldMessage.getFieldName())
                 .addConstraintViolation();
      }
      return list.isEmpty();
   }
}
