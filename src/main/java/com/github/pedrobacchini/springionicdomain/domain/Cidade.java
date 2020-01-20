package com.github.pedrobacchini.springionicdomain.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pedrobacchini.springionicdomain.json.View;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cidade implements Serializable {

    private static final long serialVersionUID = 9047842126958355681L;

    @Id
    @JsonView(View.FindAll.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(View.FindAll.class)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;
}
