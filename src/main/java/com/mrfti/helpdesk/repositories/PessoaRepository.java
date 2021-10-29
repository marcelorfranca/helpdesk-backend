package com.mrfti.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrfti.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
