package br.com.projectjersey.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "habilidade")
@NamedQueries({  
    @NamedQuery(name = "habilidade.findById", query = "SELECT h FROM Habilidade h WHERE h.id = :id"), 
    @NamedQuery(name = "habilidade.findAll", query = "SELECT h FROM Habilidade h")
})  
public class Habilidade {

	@Id
	@SequenceGenerator(name = "pk_habilidade", sequenceName = "habilidade_id_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pk_habilidade")
	@Column(nullable = false)
	private Integer id;
	
	@Column(nullable = false)
	private String descricao;
	
	@OneToMany(mappedBy = "habilidade")
	private List<HabilidadePessoa> habilidades = new ArrayList<>();
	
	@Transient
	private Integer nivel;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<HabilidadePessoa> getHabilidades() {
		return habilidades;
	}
	
	public void setHabilidades(List<HabilidadePessoa> habilidades) {
		this.habilidades = habilidades;
	}
	
	public Integer getNivel() {
		return nivel;
	}
	
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
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