package com.mrfti.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrfti.helpdesk.domain.Cliente;
import com.mrfti.helpdesk.domain.Pessoa;
import com.mrfti.helpdesk.domain.dtos.ClienteDTO;
import com.mrfti.helpdesk.repositories.ClienteRepository;
import com.mrfti.helpdesk.repositories.PessoaRepository;
import com.mrfti.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.mrfti.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private  BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objecto não encontrado! Id: " + id));
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) { // converter as infos para novo Cliente
		objDTO.setId(null); // assegurando que o ID virá nulo, senao ele pensará que é um update
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return repository.save(newObj);
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		validaPorCpfEEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
	}
	
	
	public void delete(Integer id) {
		Cliente obj = findById(id); // verifica se o id existe usando este método já criado
		if(obj.getChamados().size() > 0 ) { // se houver chamado atrelado não permitirá exclusão
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		} //senão
		
		repository.deleteById(id);
		
	}
	
	private void validaPorCpfEEmail(ClienteDTO objDTO) {
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
