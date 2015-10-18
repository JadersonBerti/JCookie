package com.extremekillers.entity;

import java.util.Date;

public class Xiter {

	private Long id;
	private String nomeXiter;
	private Date dataUsoXiter;
	private String nomeJogador;
	private byte[] arquivo;
	private String solucionado;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNomeXiter() {
		return nomeXiter;
	}
	
	public void setNomeXiter(String nomeXiter) {
		this.nomeXiter = nomeXiter;
	}
	
	public Date getDataUsoXiter() {
		return dataUsoXiter;
	}
	
	public void setDataUsoXiter(Date dataUsoXiter) {
		this.dataUsoXiter = dataUsoXiter;
	}
	
	public String getNomeJogador() {
		return nomeJogador;
	}
	
	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}
	
	public byte[] getArquivo() {
		return arquivo;
	}
	
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	
	public String getSolucionado() {
		return solucionado;
	}

	public void setSolucionado(String solucionado) {
		this.solucionado = solucionado;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}
