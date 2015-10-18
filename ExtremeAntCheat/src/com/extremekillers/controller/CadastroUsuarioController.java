package com.extremekillers.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

import com.extremekillers.business.SerialPlayerBO;
import com.extremekillers.business.UsuarioBO;
import com.extremekillers.entity.SerialPlayer;
import com.extremekillers.entity.Usuario;
import com.extremekillers.util.Util;

@ManagedBean
@ViewScoped
public class CadastroUsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private UsuarioBO usuarioBO;
	private SerialPlayer serialPlayer;
	private UploadedFile foto;
	private boolean redenderizaFoto = false;
	private boolean autenticado = false;
	
	@PostConstruct
	public void construtor(){
		usuario = new Usuario();
		usuarioBO = new UsuarioBO();
		serialPlayer = new SerialPlayer();
	
		RequestContext.getCurrentInstance().execute("PF('verificaChave').show();");
	}
	
	public void validaNick(){
		boolean isExiste = usuarioBO.validaNick(this.usuario.getNickJogo());
		if(isExiste){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este Nick Já existe.",""));
			usuario.setNickJogo("");
		}
	}
	
	public void verificaChave(){
		this.autenticado = new SerialPlayerBO().verificaChave(serialPlayer.getSerialHash());
		if(autenticado){
			RequestContext.getCurrentInstance().execute("PF('verificaChave').hide();");
			serialPlayer = new SerialPlayerBO().findByHash(serialPlayer.getSerialHash());
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Chave inválida ou numero de contas chegou no limite!",""));
		}
	}
	
	public void save() throws Exception {
		if(this.foto.getSize() < 1){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Foto Obrigatoria!",""));
			return;
		}
		
		byte[] img = Util.decreaseSizeImg(this.foto.getInputstream(), 400, 200);
		FileUtils.writeByteArrayToFile(new File("teste.png"), img);

		//		String projeto = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
//		usuario.convertiInputStremToByte(foto.getInputstream(), foto.getSize());
//		usuario.setSerialPlayerId(serialPlayer.getId());
//		if(usuarioBO.save(usuario, projeto).getId() != null){
//			this.redenderizaFoto = true;
//			byte[] fotoBase64= Base64.encodeBase64(usuario.getFotoByte());
//			usuario.setFoto("data:image/png;base64,"+new String(fotoBase64));
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Inscrição realizada com sucesso !",""));
//		}else{
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao realizar a inscrição !",""));
//		}
	}

	public void voltarIndex() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UploadedFile getFoto() {
		return foto;
	}

	public void setFoto(UploadedFile foto) {
		this.foto = foto;
	}

	public boolean isRedenderizaFoto() {
		return redenderizaFoto;
	}

	public void setRedenderizaFoto(boolean redenderizaFoto) {
		this.redenderizaFoto = redenderizaFoto;
	}
	
	public SerialPlayer getSerialPlayer() {
		return serialPlayer;
	}
	
	public void setSerialPlayer(SerialPlayer serialPlayer) {
		this.serialPlayer = serialPlayer;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
	
}