package com.extremekillers.entity;

import java.io.InputStream;
import java.io.Serializable;

import com.extremekillers.util.Util;

public class JogadorWarface extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nickJogo;
	private String loginWarface;
	private String email;
	private String codigoAntXiter;
	private String foto;
	private byte[] fotoByte;
	
	public String getNickJogo() {
		return nickJogo;
	}
	
	public void setNickJogo(String nickJogo) {
		this.nickJogo = nickJogo;
	}
	
	public String getCodigoAntXiter() {
		return codigoAntXiter;
	}
	
	public void setCodigoAntXiter(String codigoAntXiter) {
		this.codigoAntXiter = codigoAntXiter;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public byte[] getFotoByte() {
		return fotoByte;
	}
	
	public void setFotoByte(byte[] fotoByte) {
		this.fotoByte = fotoByte;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLoginWarface() {
		return loginWarface;
	}

	public void setLoginWarface(String loginWarface) {
		this.loginWarface = loginWarface;
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
		return super.toString()+",JogadorWarface [nickJogo=" + nickJogo + ", loginWarface="
				+ loginWarface + ", email=" + email + ", codigoAntXiter="
				+ codigoAntXiter + ", foto=" + foto + ", fotoByte="
				+ (fotoByte != null ? "Tem foto":"Não tem foto") + "]";
	}
	

	
	
}
