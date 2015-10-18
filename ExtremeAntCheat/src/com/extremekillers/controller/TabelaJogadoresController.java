package com.extremekillers.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.context.RequestContext;

import com.extremekillers.business.UsuarioBO;
import com.extremekillers.entity.Usuario;

@ViewScoped
@ManagedBean
public class TabelaJogadoresController implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Usuario> usuariosWarface = new ArrayList<Usuario>();  
	private UsuarioBO usuarioBO = new UsuarioBO(); 
	private Usuario usuario = new Usuario();
	private String nomeUsuario = "";
	private Integer maxResultado;
	private Integer ultimoId = 0;
	
	@PostConstruct
	public void construtor(){
		usuariosWarface = usuarioBO.findFilter(0,5);
		maxResultado = usuarioBO.getCountUsuarios();
		RequestContext.getCurrentInstance().execute("PF('statusDialog').hide();");
	}
	
	public void maisResutado(){
		List<Usuario> usuarios = usuarioBO.findFilter(usuariosWarface.get((ultimoId = ultimoId + 4)).getId().intValue(), maxResultado);
		usuariosWarface.addAll(usuarios);
	}
	
	public void buscarUsuario(){
		if(nomeUsuario != null && !nomeUsuario.isEmpty()){
		 	this.usuario = null;
			this.usuario = usuarioBO.findByFilterNameOrNick(nomeUsuario);
			if(this.usuario != null && this.usuario.getId() != null){
				RequestContext.getCurrentInstance().execute("PF('retorno_busca_usuario').show();");
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Jogador não encontrado",""));	
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"campos vazio ",""));
		}
	}
	
	public void verificaCodigo(){
		boolean senha = usuario.getSenhaAntXiter() != null && !usuario.getSenhaAntXiter().isEmpty();
		boolean email = usuario.getEmail() != null && !usuario.getEmail().isEmpty();
		if(senha && email){
			this.usuario = usuarioBO.autentica(usuario.getSenhaAntXiter(), usuario.getEmail());
			if(this.usuario != null && this.usuario.getId() != null){
				RequestContext.getCurrentInstance().execute("PF('ataulizarWarface').show();");
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senha ou Email Invalido !",""));
			}
		}
	}
	
	public void sairCampeonato() throws IOException{
		if(usuarioBO.detele(usuario.getId())){
			FacesContext.getCurrentInstance().getExternalContext().redirect("tabela-jogadores.xhtml");
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao sair do campeonato",""));
		}
	}
	
	public void atualizarJogador() throws IOException{
		if(usuarioBO.update(usuario)){
			FacesContext.getCurrentInstance().getExternalContext().redirect("tabela-jogadores.xhtml?");
			//RequestContext.getCurrentInstance().execute("PF('ataulizarWarface').hide();");
			//FacesContext.getCurrentInstance().addMessage("id_message_form_jogador_warface", new FacesMessage(FacesMessage.SEVERITY_INFO,"Jogador atualizado com sucesso !",""));
		}else{
			FacesContext.getCurrentInstance().addMessage("id_message_form_jogador_warface", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao atualizar jogador",""));
		}		
	}
	
	public String getFotoBse64(byte[] foto){
		if(foto != null){
			byte[] fotoBase64= Base64.encodeBase64(foto);
			return "data:image/png;base64,"+new String(fotoBase64);
		}else{
			return "";
		}
	}
	
	public String getSexo(char sexo){
		switch (sexo) {
		case 'm':
			return "Masculino";
		case 'f':
			return "feminino";
		case 'o':
			return "Outros";
		default:
			return "Sexo Indefinido";
		}
	}

	public List<Usuario> getUsuariosWarface() {
		return usuariosWarface;
	}

	public void setUsuariosWarface(List<Usuario> usuariosWarface) {
		this.usuariosWarface = usuariosWarface;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	
}