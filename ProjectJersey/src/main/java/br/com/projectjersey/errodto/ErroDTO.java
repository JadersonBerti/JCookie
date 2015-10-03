package br.com.projectjersey.errodto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(value=XmlAccessType.FIELD)
public class ErroDTO implements Serializable{

	private static final long serialVersionUID = 3230653310935229601L;
	
	private Integer codigoErro;
	private String mensagemErro;
	
	
	public ErroDTO() {
		// TODO Auto-generated constructor stub
	}

	public ErroDTO(Integer codigoErro, String mensagemErro) {
		super();
		this.codigoErro = codigoErro;
		this.mensagemErro = mensagemErro;
	}

	public Integer getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(Integer codigoErro) {
		this.codigoErro = codigoErro;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}

	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}

}
