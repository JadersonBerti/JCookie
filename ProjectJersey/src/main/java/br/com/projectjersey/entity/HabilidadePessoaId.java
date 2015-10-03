package br.com.projectjersey.entity;

import java.io.Serializable;

public class HabilidadePessoaId implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer pessoa;
	private Integer habilidade;
	
	public Integer getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Integer pessoa) {
		this.pessoa = pessoa;
	}
	
	public Integer getHabilidade() {
		return habilidade;
	}
	
	public void setHabilidade(Integer habilidade) {
		this.habilidade = habilidade;
	}
	
	@Override
	public int hashCode() {
		return pessoa + habilidade;
	}
	
	@Override
	public boolean equals(Object obj) {
		 if(obj instanceof HabilidadePessoaId){
			 HabilidadePessoaId habilidadePessoaId = (HabilidadePessoaId) obj;
	            return habilidadePessoaId.habilidade == habilidade && habilidadePessoaId.pessoa == pessoa;
	        }
	 
	     return false;
	}
	
}
