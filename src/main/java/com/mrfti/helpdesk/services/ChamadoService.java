package com.mrfti.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrfti.helpdesk.domain.Chamado;
import com.mrfti.helpdesk.domain.Cliente;
import com.mrfti.helpdesk.domain.Tecnico;
import com.mrfti.helpdesk.domain.dtos.ChamadoDTO;
import com.mrfti.helpdesk.domain.enums.Prioridade;
import com.mrfti.helpdesk.domain.enums.Status;
import com.mrfti.helpdesk.repositories.ChamadoRepository;
import com.mrfti.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;

	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO objDTO) {
		return repository.save(newChamado(objDTO));
	}

	
	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDTO); // chamado o metodo para verificar se já existe o id para o update
		return repository.save(oldObj);
	}
	
	private Chamado newChamado(ChamadoDTO obj) { // usado no create e update
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());

		Chamado chamado = new Chamado();
		if (obj.getId() != null) {
			chamado.setId(obj.getId());
		}

		if(obj.getStatus().equals(2)) { // se no update o usuario encerrar a os gera a data de fechamento
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());

		return chamado;
	}

	

}
