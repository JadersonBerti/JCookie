package com.extremekillers.entity;

import java.util.Date;

public class ContadorTempo {

	private Integer id;
	private Date inicio;
	private Date termino;
	private Integer serial_player_id;
	private Integer inicio_admin_id;
	private String nomeAdminIncio;
	private Integer termino_admin_id;
	private String nomeAdminTermino;
	private String totalHoras;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getTermino() {
		return termino;
	}
	public void setTermino(Date termino) {
		this.termino = termino;
	}
	public Integer getSerial_player_id() {
		return serial_player_id;
	}
	public void setSerial_player_id(Integer serial_player_id) {
		this.serial_player_id = serial_player_id;
	}
	public Integer getInicio_admin_id() {
		return inicio_admin_id;
	}
	public void setInicio_admin_id(Integer inicio_admin_id) {
		this.inicio_admin_id = inicio_admin_id;
	}
	public Integer getTermino_admin_id() {
		return termino_admin_id;
	}
	public void setTermino_admin_id(Integer termino_admin_id) {
		this.termino_admin_id = termino_admin_id;
	}
	public String getTotalHoras() {
		return totalHoras;
	}
	public void setTotalHoras(String totalHoras) {
		this.totalHoras = totalHoras;
	}
	public String getNomeAdminIncio() {
		return nomeAdminIncio;
	}
	public void setNomeAdminIncio(String nomeAdminIncio) {
		this.nomeAdminIncio = nomeAdminIncio;
	}
	public String getNomeAdminTermino() {
		return nomeAdminTermino;
	}
	public void setNomeAdminTermino(String nomeAdminTermino) {
		this.nomeAdminTermino = nomeAdminTermino;
	}
	
	
	
}