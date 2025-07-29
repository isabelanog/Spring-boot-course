package com.treina.recife.sgp.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.treina.recife.sgp.constants.StatusUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;

    @NotNull(message = "Campo nome é obrigatório.")
    @Column(name = "NOME", nullable = false)
    private String nome;

    @NotNull(message = "Campo CPF é obrigatório.")
    @Column(name = "CPF", nullable = false, unique = true)
    //@CPF(message = "cpf inválido")
    private String cpf;

    @NotNull(message = "Campo e-mail é obrigatório.")
    @Email(message = "Formato inválido de e-mail")
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @NotNull(message = "Campo senha é obrigatório.")
    @Column(name = "SENHA", nullable = false)
    private String senha;

    @NotNull(message = "Campo data de nascimento é obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "DATANASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusUsuario status;

    @OneToMany(mappedBy = "responsavel", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Projeto> projetos  = new ArrayList<>();

    @Override
    public String toString() {
        return "Usuario{" +
                "userId=" + userId +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento" + dataNascimento + '\'' +
                ", Status: " + status + '\''+
                '}';
    }

}
