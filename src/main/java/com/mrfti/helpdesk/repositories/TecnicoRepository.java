package com.mrfti.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrfti.helpdesk.domain.Tecnico;


public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
