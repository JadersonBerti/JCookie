package com.extremekillers.entity;

import java.io.InputStream;

import com.extremekillers.util.Util;

public class Usuario extends Pessoa{

	private String nome;
	private String nickJogo;
	private String email;
	private String senhaAntXiter;
	private String foto;
	private byte[] fotoByte;
	private Integer serialPlayerId;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNickJogo() {
		return nickJogo;
	}
	
	public void setNickJogo(String nickJogo) {
		this.nickJogo = nickJogo;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenhaAntXiter() {
		return senhaAntXiter;
	}
	
	public void setSenhaAntXiter(String senhaAntXiter) {
		this.senhaAntXiter = senhaAntXiter;
	}
	
	public byte[] getFotoByte() {
		return fotoByte;
	}
	
	public void setFotoByte(byte[] fotoByte) {
		this.fotoByte = fotoByte;
	}
	
	public Integer getSerialPlayerId() {
		return serialPlayerId;
	}
	
	public void setSerialPlayerId(Integer serialPlayerId) {
		this.serialPlayerId = serialPlayerId;
	}
	
	public void convertiInputStremToByte(InputStream inputStream , Long size){
		try {
			this.fotoByte = Util.inputStreamToByte(inputStream, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return super.toString() + ", Usuario [nome=" + nome + ", nickJogo=" + nickJogo + ", email="
				+ email + ", senhaAntXiter=" + senhaAntXiter + ", fotoByte="
				+ fotoByte != null ? "Nao e nullo":"e Nullo" + "]";
	}

	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}