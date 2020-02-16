package com.github.pedrobacchini.springionicdomain.dto;

import com.github.pedrobacchini.springionicdomain.SpringIonicDomainApplication;
import com.github.pedrobacchini.springionicdomain.config.ApplicationProperties;
import com.github.pedrobacchini.springionicdomain.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = -8347399789817608571L;

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String name;

    private String picture;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        ApplicationProperties applicationProperties = SpringIonicDomainApplication.getBean(ApplicationProperties.class);
        this.picture = applicationProperties.getImage().getCategory().getBucketBaseUrl() + id +".jpg";
    }
}
