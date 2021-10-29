package com.mrfti.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrfti.helpdesk.domain.Chamado;
import com.mrfti.helpdesk.domain.Cliente;
import com.mrfti.helpdesk.domain.Tecnico;
import com.mrfti.helpdesk.domain.enums.Perfil;
import com.mrfti.helpdesk.domain.enums.Prioridade;
import com.mrfti.helpdesk.domain.enums.Status;
import com.mrfti.helpdesk.repositories.ChamadoRepository;
import com.mrfti.helpdesk.repositories.ClienteRepository;
import com.mrfti.helpdesk.repositories.TecnicoRepository;

@Service // cria, gerencia e destroi no tempo correto
public class DBservice {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	public void instanciaDB() {

		Tecnico tec1 = new Tecnico(null, "Marcelo Ramos", "753.988.110-02", "marcelo@mrftio.com", "123");
		tec1.addPerfil(Perfil.ADMIN);

		Cliente cli1 = new Cliente(null, "Bill Gates", "655.844.770-36", "bill@mrfti.com", "111");
		cli1.addPerfil(Perfil.CLIENTE);

		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1,
				cli1);

		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));

	}

}
