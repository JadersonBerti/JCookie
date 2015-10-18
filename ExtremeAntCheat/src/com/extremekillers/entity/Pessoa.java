package com.extremekillers.entity;

import java.util.Date;

public class Pessoa {

	protected Long id;
	protected String nome;
	protected char sexo;
	protected Date dataNacimento;
	protected String pais;
	protected String estado;
	protected String cidade;
	protected String logradouro;
	protected String bairro;
	protected String numeroResidencia;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public char getSexo() {
		return sexo;
	}
	
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	public Date getDataNacimento() {
		return dataNacimento;
	}
	
	public void setDataNacimento(Date dataNacimento) {
		this.dataNacimento = dataNacimento;
	}
	
	public String getPais() {
		return pais;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getNumeroResidencia() {
		return numeroResidencia;
	}
	
	public void setNumeroResidencia(String numeroResidencia) {
		this.numeroResidencia = numeroResidencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", sexo=" + sexo
				+ ", dataNacimento=" + dataNacimento + ", pais=" + pais
				+ ", estado=" + estado + ", cidade=" + cidade + ", logradouro="
				+ logradouro + ", bairro=" + bairro + ", numeroResidencia="
				+ numeroResidencia + "]";
	}
	
}
