package com.treina.recife.sgp.service;

import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Tarefa;
import com.treina.recife.sgp.model.Usuario;
import org.springframework.data.domain.Pageable;
import com.treina.recife.sgp.repository.ProjetoRepository;
import com.treina.recife.sgp.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProjetoService {

    Page<Projeto> getProjetos(Pageable paginacao);

    Optional<Projeto> getProjectById(long projectId);

    Projeto createProjeto(Projeto projeto);

    Projeto updateProjeto(Projeto projeto);

    void deleteProjeto(long projectId);

    Optional<Usuario> getResponsavel(long projectId);
}
