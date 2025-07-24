package com.treina.recife.sgp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina.recife.sgp.constants.StatusProjeto;

import java.time.LocalDate;

public class ProjetoDto {

    private String nome;

    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataConclusao;

    private StatusProjeto status;

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public StatusProjeto getStatus() {
        return status;
    }

   
}
