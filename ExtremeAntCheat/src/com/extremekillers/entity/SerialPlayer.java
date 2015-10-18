package com.extremekillers.entity;

public class SerialPlayer {

	private Integer id;
	private String serialHash;
	private String remetente;
	private Integer numeroChaves;
	private Integer numeroChavesUsadas;
	private boolean statusServidor;
	private String tempoLiberado;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSerialHash() {
		return serialHash;
	}
	
	public void setSerialHash(String serialHash) {
		this.serialHash = serialHash;
	}
	
	public String getRemetente() {
		return remetente;
	}
	
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	
	public Integer getNumeroChaves() {
		return numeroChaves;
	}
	
	public void setNumeroChaves(Integer numeroChaves) {
		this.numeroChaves = numeroChaves;
	}
	
	public boolean isStatusServidor() {
		return statusServidor;
	}
	
	public void setStatusServidor(boolean statusServidor) {
		this.statusServidor = statusServidor;
	}
	
	public Integer getNumeroChavesUsadas() {
		return numeroChavesUsadas;
	}
	
	public void setNumeroChavesUsadas(Integer numeroChavesUsadas) {
		this.numeroChavesUsadas = numeroChavesUsadas;
	}
	
	public boolean isNull(){
		return (this.id == null && this.serialHash == null && this.remetente == null && this.numeroChaves == null ? true : false);
	}
	
	public boolean isNotNull(){
		return !isNull();
	}
	
	public String getTempoLiberado() {
		return tempoLiberado;
	}
	
	public void setTempoLiberado(String tempoLiberado) {
		this.tempoLiberado = tempoLiberado;
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
		SerialPlayer other = (SerialPlayer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SerialPlayer [id=" + id + ", serialHash=" + serialHash
				+ ", remetente=" + remetente + ", numeroChaves=" + numeroChaves
				+ "]";
	}
	
}
