package com.SistemaKerhOs.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemaKerhOs.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{
	
}
