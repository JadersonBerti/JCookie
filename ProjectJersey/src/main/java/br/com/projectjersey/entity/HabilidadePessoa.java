package br.com.projectjersey.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="habilidade_pessoa")
@IdClass(HabilidadePessoaId.class)
public class HabilidadePessoa {
	
	@Id
    @ManyToOne
    @JoinColumn(name="pessoa_id", nullable = false)
    private Pessoa pessoa = new Pessoa();
 
	@Id
    @ManyToOne
    @JoinColumn(name="habilidade_id", nullable = false)
    private Habilidade habilidade = new Habilidade();
	
	private Integer nivel;
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public Habilidade getHabilidade() {
		return habilidade;
	}
	
	public void setHabilidade(Habilidade habilidade) {
		this.habilidade = habilidade;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	
	
}
