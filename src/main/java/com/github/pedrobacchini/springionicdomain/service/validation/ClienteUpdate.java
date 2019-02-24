package com.github.pedrobacchini.springionicdomain.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ClienteUpdateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface ClienteUpdate {

    String message() default "Erro de validaçõa";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
