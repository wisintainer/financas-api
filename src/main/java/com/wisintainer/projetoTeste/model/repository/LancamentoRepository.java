package com.wisintainer.projetoTeste.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisintainer.projetoTeste.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
