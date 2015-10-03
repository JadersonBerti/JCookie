package br.com.projectjersey.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


@Entity
@Table(name = "pessoa")
@NamedQueries({  
    @NamedQuery(name = "pessoa.findAll", query = "SELECT p FROM Pessoa p")  
})
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Integer id;

	private String nome;

	private String formacao;
	
	@OneToMany(mappedBy = "pessoa")
	private List<HabilidadePessoa> pessoas;
	
	private byte[] curriculo;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getFormacao() {
		return formacao;
	}
	
	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}
	
	public List<HabilidadePessoa> getPessoas() {
		return pessoas;
	}
	
	public void setPessoas(List<HabilidadePessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public byte[] getCurriculo() {
		return curriculo;
	}
	
	public void setCurriculo(byte[] curriculo) {
		this.curriculo = curriculo;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}