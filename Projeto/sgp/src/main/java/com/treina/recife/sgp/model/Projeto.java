package com.treina.recife.sgp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina.recife.sgp.constants.StatusProjeto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projectId")
    private long projectId;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "dataInicio", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    @Column(name = "dataConclusao", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataConclusao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonBackReference
    private Usuario responsavel;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

}
