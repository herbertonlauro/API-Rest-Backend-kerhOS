package com.SistemaKerhOs.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemaKerhOs.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
}
