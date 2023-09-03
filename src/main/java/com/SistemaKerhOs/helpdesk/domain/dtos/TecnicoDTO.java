package com.SistemaKerhOs.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.SistemaKerhOs.helpdesk.domain.Tecnico;
import com.SistemaKerhOs.helpdesk.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TecnicoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	protected Integer id;
	@NotNull(message = "Campo NOME não pode ser vazio")
	protected String nome;
	@NotNull(message = "Campo CPF não pode ser vazio")
	protected String cpf;
	@NotNull(message = "Campo E-MAIL não pode ser vazio")
	protected String email;
	@NotNull(message = "Campo SENHA não pode ser vazio")
	protected String senha;
	protected Set<Integer> perfis = new HashSet<>();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriado = LocalDate.now();

	public TecnicoDTO() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public TecnicoDTO(Tecnico obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriado = obj.getDataCriado();
		addPerfil(Perfil.CLIENTE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriado() {
		return dataCriado;
	}

	public void setDataCriado(LocalDate dataCriado) {
		this.dataCriado = dataCriado;
	}

	
	
	
}
