package br.com.projectjersey.exception;

import java.io.Serializable;

public class RestException extends Exception implements Serializable {

	private static final long serialVersionUID = 7181326313225623532L;

	private Integer codigoErro;

	public RestException(Integer codigoErro, String message) {
		super(message);
		this.setCodigoErro(codigoErro);
	}

	public RestException(Throwable cause) {
		super(cause);
	}

	public RestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Integer getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(Integer codigoErro) {
		this.codigoErro = codigoErro;
	}

	
}
