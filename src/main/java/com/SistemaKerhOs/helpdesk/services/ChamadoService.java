package com.SistemaKerhOs.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaKerhOs.helpdesk.domain.Chamado;
import com.SistemaKerhOs.helpdesk.domain.Cliente;
import com.SistemaKerhOs.helpdesk.domain.Tecnico;
import com.SistemaKerhOs.helpdesk.domain.dtos.ChamadoDTO;
import com.SistemaKerhOs.helpdesk.enums.Prioridade;
import com.SistemaKerhOs.helpdesk.enums.Status;
import com.SistemaKerhOs.helpdesk.repositories.ChamadoRepository;
import com.SistemaKerhOs.helpdesk.services.exceptions.ObjectnotFoundException;


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
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado ID: "+ id));
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
	  oldObj = newChamado(objDTO);
		return repository.save(oldObj);
	}
	
	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		Chamado chamado = new Chamado();
		if (obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		if(obj.getStatus().equals(2)){
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacao(obj.getObservacao());
		return chamado;
	}


}