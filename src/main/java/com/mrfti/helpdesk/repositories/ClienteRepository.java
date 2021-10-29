package com.mrfti.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrfti.helpdesk.domain.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
