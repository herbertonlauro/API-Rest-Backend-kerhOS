package com.SistemaKerhOs.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemaKerhOs.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{
	
}
