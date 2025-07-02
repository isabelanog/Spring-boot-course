package com.treina.recife.sgp.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "Projetos")
public class Projeto {

    private long id;

    private String nome;

    private String descricao;

    //primeiro criar sem relacionamentos
}
