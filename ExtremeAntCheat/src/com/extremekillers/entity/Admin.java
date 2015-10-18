package com.extremekillers.entity;

public class Admin {
	
	private Integer id;
	private String email;
	private String senha;
	private Integer serialPlayerId;
	private String ligaRemetente;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getSerialPlayerId() {
		return serialPlayerId;
	}
	public void setSerialPlayerId(Integer serialPlayerId) {
		this.serialPlayerId = serialPlayerId;
	}
	public String getLigaRemetente() {
		return ligaRemetente;
	}
	public void setLigaRemetente(String ligaRemetente) {
		this.ligaRemetente = ligaRemetente;
	}
}
