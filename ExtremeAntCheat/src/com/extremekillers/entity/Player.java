package com.extremekillers.entity;

import java.util.Date;

public class Player {

	private int id;
	private String nome;
	private boolean ativo;
	private Date data;
	private int usuario_id;
	private String detalhes;
	private int serialLigaId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public int getUsuario_id() {
		return usuario_id;
	}
	
	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}
	
	public String getDetalhes() {
		return detalhes;
	}
	
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	
	public int getSerialLigaId() {
		return serialLigaId;
	}
	
	public void setSerialLigaId(int serialLigaId) {
		this.serialLigaId = serialLigaId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", nome=" + nome + ", ativo=" + ativo
				+ ", data=" + data + ", usuario_id=" + usuario_id + "]";
	}
	
}