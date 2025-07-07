package com.treina.recife.sgp.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina.recife.sgp.constants.StatusUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;

    @NotNull(message = "Campo nome é obrigatório.")
    @Column(name = "NOME", nullable = false)
    private String nome;

    // @Min(value = 0)
    // @Max(value = 100)
    // private int idade;

    @NotNull(message = "Campo cpf é obrigatório.")
    @Column(name = "CPF", nullable = false, unique = true)
    private String cpf;

    @NotNull
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "SENHA", nullable = false)
    private String senha;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DT_NASC_USUARIO", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusUsuario status;

    @OneToMany(mappedBy = "responsavel")
    private List<Projeto> projetos  = new ArrayList<>();;


}
