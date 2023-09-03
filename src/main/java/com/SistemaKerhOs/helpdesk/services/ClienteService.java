package com.SistemaKerhOs.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.SistemaKerhOs.helpdesk.domain.Pessoa;
import com.SistemaKerhOs.helpdesk.domain.Cliente;
import com.SistemaKerhOs.helpdesk.domain.dtos.ClienteDTO;
import com.SistemaKerhOs.helpdesk.repositories.PessoaRepository;
import com.SistemaKerhOs.helpdesk.repositories.ClienteRepository;
import com.SistemaKerhOs.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.SistemaKerhOs.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("objeto não encontrado id: " + id));
	}

	public List<Cliente> findAll() {
		 return repository.findAll();
	}
	
	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaCpfEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return repository.save(newObj);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		if(!objDTO.getSenha().equals(oldObj.getSenha()))  
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaCpfEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
	}
	public void delete(Integer id) {
		Cliente obj = findById(id);
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("O Cliente tem ordem no seu cadastro e não pode ser deletado");
		}else {
			repository.deleteById(id);
		}
	}
	private void validaCpfEmail(ClienteDTO objDTO) {
		 Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		 if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			 throw new DataIntegrityViolationException("CPF JA CADASTRADO");
		 }
		  obj = pessoaRepository.findByEmail(objDTO.getEmail());
		 if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			 throw new DataIntegrityViolationException("E-MAIL JA CADASTRADO");
		 }
	}

	

	
}