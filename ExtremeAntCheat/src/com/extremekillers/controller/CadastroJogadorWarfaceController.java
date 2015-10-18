package com.extremekillers.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.extremekillers.business.JogadorWarfaceBO;
import com.extremekillers.entity.JogadorWarface;

@ManagedBean
public class CadastroJogadorWarfaceController implements Serializable { 

	private static final long serialVersionUID = 1L;
	private JogadorWarface jogadorWarface;
	private JogadorWarfaceBO jogadorWarfaceBO;
	private UploadedFile foto;
	private boolean redenderizaFoto = false;
	
	@PostConstruct
	public void construtor(){
		jogadorWarface = new JogadorWarface();
		jogadorWarfaceBO = new JogadorWarfaceBO();
		
		RequestContext.getCurrentInstance().execute("PF('modalRestricao').show()");
		//this.bloquioView();
	}
	
	@SuppressWarnings("unused")
	private void bloquioView(){
		if(!jogadorWarfaceBO.getServidorOn()){
			RequestContext.getCurrentInstance().execute("PF('modalRestricao').show()");
		}
	}
	
	public void save() throws Exception {
		if(this.foto.getSize() < 1){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Foto Obrigatoria!",""));
			return;
		}
		
		if(jogadorWarfaceBO.isCodigoAntXiter(jogadorWarface.getCodigoAntXiter())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Codigo existente crie um diferente!",""));
			return;
		}
		
		jogadorWarface.convertiInputStremToByte(foto.getInputstream(), foto.getSize());
		if(jogadorWarfaceBO.save(jogadorWarface).getId() != null){
			this.redenderizaFoto = true;
			byte[] fotoBase64= Base64.encodeBase64(jogadorWarface.getFotoByte());
			jogadorWarface.setFoto("data:image/png;base64,"+new String(fotoBase64));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Inscrição realizada com sucesso !",""));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao realizar a inscrição !",""));
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {  
        this.foto=event.getFile();
    }
	
	public JogadorWarface getJogadorWarface() {
		return jogadorWarface;
	}

	public void setJogadorWarface(JogadorWarface jogadorWarface) {
		this.jogadorWarface = jogadorWarface;
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
	
}