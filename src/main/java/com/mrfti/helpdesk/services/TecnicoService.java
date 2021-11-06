package com.mrfti.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrfti.helpdesk.domain.Pessoa;
import com.mrfti.helpdesk.domain.Tecnico;
import com.mrfti.helpdesk.domain.dtos.TecnicoDTO;
import com.mrfti.helpdesk.repositories.PessoaRepository;
import com.mrfti.helpdesk.repositories.TecnicoRepository;
import com.mrfti.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.mrfti.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objecto não encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) { // converter as infos para novo tecnico
		objDTO.setId(null); // assegurando que o ID virá nulo, senao ele pensará que é um update
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		// verifica o email somente se o cpf passar
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("EMAIL já cadastrado no sistema!");
		}
		
		
	}
	
}
