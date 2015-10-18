package com.extremekillers.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.extremekillers.business.AdministrativoBO;
import com.extremekillers.business.ContadorTempoBO;
import com.extremekillers.business.JogadorWarfaceBO;
import com.extremekillers.business.PlayerBO;
import com.extremekillers.business.SerialPlayerBO;
import com.extremekillers.entity.Admin;
import com.extremekillers.entity.ContadorTempo;
import com.extremekillers.entity.JogadorWarface;
import com.extremekillers.entity.Player;
import com.extremekillers.entity.SerialPlayer;
import com.extremekillers.util.Util;

@ManagedBean
@ViewScoped
public class AdministrativoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String email,senha,siglaParaHash,totalHoraContadorTempo = "";
	private AdministrativoBO administrativoBO;
	private String printTela;
	private byte[] printTelaBytes;
	private String processosTela;
	private StreamedContent file;
	private SerialPlayer serialPlayer; 
	private SerialPlayerBO serialPlayerBO;
	private Admin admin;
	private ContadorTempoBO contadorTempoBO; 
	private ContadorTempo contadorTempo;
	private List<ContadorTempo> contadorTempos = new ArrayList<>();
	private List<JogadorWarface> blackList = new ArrayList<>();
	private JogadorWarfaceBO jogadorWarfaceBO  = new JogadorWarfaceBO();
	private String caminhoAudio = "";
	
	@PostConstruct
	public void cosntrutor(){
		administrativoBO = new AdministrativoBO();
		contadorTempoBO = new ContadorTempoBO();
		jogadorWarfaceBO = new JogadorWarfaceBO();
		
		serialPlayer = new SerialPlayer();
		serialPlayerBO = new SerialPlayerBO();
		admin = new Admin();
		
		this.restricao();
		//this.modoDesenvolvimentoAdmin();
		//this.modoDesenvolvimentoAdminSystem();

	}
	
	public void getBlackListAdminIsView(){
		blackList = jogadorWarfaceBO.findBlackListAdminIsView();
		if(blackList != null && !blackList.isEmpty()){
			jogadorWarfaceBO.updateIsViewAdmin(blackList);
			this.startAudio();
			RequestContext.getCurrentInstance().execute("PF('mdl-blackList').show();");
			RequestContext.getCurrentInstance().update("blackList");
		}
	}
	
	public void limpaBlackListAdminIsView(CloseEvent event){
		blackList = new ArrayList<>();
	}
	
	private void startAudio(){
		this.caminhoAudio = "/resources/audio/alarme.mp3";	
	}
	
	public void cadastraLiga(){
		boolean cadastrado = administrativoBO.cadastraLiga(serialPlayer);
		if(cadastrado){
			serialPlayer = new SerialPlayer();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Liga Cadastrada Com Sucesso!",""));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não foi possivel cadastrar!",""));
		}
	}
	
	public void loginAdminSystem(){
		boolean autenticado = administrativoBO.autenticarAdminSystem(this.email, this.senha);
		if(autenticado){
			setSiglaParaHash("");
			RequestContext.getCurrentInstance().execute("PF('login-admin-system').hide();PF('painel-admin-system').show();");
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"email ou senha incorreto.",""));
		}
	}

	public void loginAdmin() throws IOException{
		admin = administrativoBO.autenticar(this.email, this.senha);
		if(admin != null && admin.getSerialPlayerId() != null){
			contadorTempo = new ContadorTempo();
			contadorTempoBO = new ContadorTempoBO();
			contadorTempo = contadorTempoBO.retornaUltimoContadorAtivo(admin.getSerialPlayerId());
			this.serialPlayer = new SerialPlayerBO().findById(admin.getSerialPlayerId());
			RequestContext.getCurrentInstance().execute("PF('admin_login_paienl').hide();PF('admin_painel').show();");
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"email ou senha incorreto.",""));
		}
	}
	
	public void comandoThemaBasic(int usuario_id){
		administrativoBO.executeComandoThemaBaisc(usuario_id);
	}
	
	public void comandoCopieProcesso(int usuario_id) throws Exception {
		//executar comando para scaninar os processos ativos
		administrativoBO.executeComandoCopieProcesso(usuario_id);	
		
		//da um tempo para o agente trom processar no desktop.
		Thread.sleep(8000);
		
		//Pega o comando processado e renderiza na tela.
		byte[] processosByte = administrativoBO.returnComandoTypePrint(usuario_id);
		if(processosByte != null){
			this.processosTela = new String (processosByte, "ISO-8859-1").replaceAll("null", "").replaceAll("\n\n", "")
					.replaceAll("Nome da imagem ", "Nome do processo");
			
			administrativoBO.deleteRetunrComando(usuario_id);
			
			RequestContext.getCurrentInstance().update("painel");
			
			//Atualiza a imagem PF('leitura_processo').show();
			//RequestContext.getCurrentInstance().execute("PF('statusDialog').hide();PF('leitura_processo').show();");
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao processar evento. "
					+ "Verifique se o usuario esta realmente online. Ou atualize sua tela. Caso erro persista entre contato com Jaderson.",""));
		}
	}
	
	public void exportTxt() {
		HttpServletResponse responce = ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse());
		administrativoBO.exportTxt(responce, this.processosTela);
	}
	
	public void comandoPrintDoUsuario(int usuarioId) throws Exception {
		//executa comanod de print
		administrativoBO.executeComandoPrint(usuarioId);
		
		//da um tempo para o agente trom processar no desktop.
		Thread.sleep(8000);
		
		//Pega o comando processado e renderiza na tela.
		this.printTelaBytes = administrativoBO.returnComandoTypePrint(usuarioId);	
		if(this.printTelaBytes != null){
			byte[] fotoBase64 = Base64.encodeBase64(this.printTelaBytes);
			this.printTela =  "data:image/png;base64,"+new String(fotoBase64);
			
			administrativoBO.deleteRetunrComando(usuarioId);
			
			//Atualiza a imagem 
			RequestContext.getCurrentInstance().update("painel");
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao processar evento. Verifique se o usuario esta realmente online.",""));
		}
	}
	
	private void comandoFechaServidor(){
		//executa comando para fechar o servidor
		administrativoBO.executaComandoFechaServidor(this.serialPlayer.getId());
	}
	
	public void cadastrarAdmin(){
		if(administrativoBO.cadastrarAdmin(admin)){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Admin Cadastrado com sucesso!",""));
			admin = new Admin();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não foi possivel cadastrar Admin!",""));
		}
	}

	public void liberarServidorLiga(){
		if(serialPlayerBO.isTempoAtingido(this.serialPlayer.getTempoLiberado(), 
				this.serialPlayer.getId())){
			FacesContext.getCurrentInstance().addMessage("msg-status-servidor", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Desculpe seu tempo chegou no limite!",""));
			RequestContext.getCurrentInstance().update("msg-status-servidor");
			return;
		}
		if(new SerialPlayerBO().updateStatusServidor(this.serialPlayer.getId(), this.serialPlayer.isStatusServidor())){
			//Verifica se o servidor esta on ou off
			if(this.serialPlayer.isStatusServidor()){
				//Starta o contador
				Integer id = contadorTempoBO.startContador(this.serialPlayer.getId(), new Date(), admin.getId());
				contadorTempo = contadorTempoBO.findById(id);
			}else{
				//Desliga o contador
				contadorTempoBO.desligarContador(contadorTempo, new Date(), admin.getId());
				contadorTempo = contadorTempoBO.findById(contadorTempo.getId());
				
				//Fecha o sistema para todos os usuarios.
				this.comandoFechaServidor();
			}
			FacesContext.getCurrentInstance().addMessage("msg-status-servidor", 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Status do servidor foi alterado com sucesso !",""));
			RequestContext.getCurrentInstance().update("msg-status-servidor");
		}else{
			FacesContext.getCurrentInstance().addMessage("msg-status-servidor", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro ao alterar status do servidor !",""));
			RequestContext.getCurrentInstance().update("msg-status-servidor");
		}
	}
	
	public List<Player> getPlayers() throws Exception {
		return (this.serialPlayer != null && this.serialPlayer.getId() != null) ? 
				new PlayerBO().findAll(this.serialPlayer.getId()) : new ArrayList<Player>();
	}
	
	public void gerarHashAtomatico(){
		this.serialPlayer.setSerialHash(Util.parseHashMD5(this.siglaParaHash));
		RequestContext.getCurrentInstance().execute("PF('mdl-sorte-hash').hide();");
	}
	
	public List<SerialPlayer> getSerialPlayers(){
		return new SerialPlayerBO().findAll();
	}
	
	public void deleteSerialPlayer(int id){
		if(serialPlayerBO.delete(id)){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Liga deletada com sucesso!",""));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não foi possivel deletar a liga!",""));
		}
	}
	
	public void atualizaSerialPlayer(){
		if(serialPlayerBO.update(serialPlayer)){
			serialPlayer = new SerialPlayer();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Serial Player Ataulizado com sucesso!",""));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO,"Não foi possivel atualizar Serial Player!",""));
		}
	}
	
	public void atualizaSerialPlayer(SerialPlayer serialPlayer){
		this.serialPlayer = serialPlayer;
	}
	
	public void deleteAdmin(int id){
		if(administrativoBO.deleteAdmin(id)){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Admin deletada com sucesso!",""));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não foi possivel deletar o Admin!",""));
		}
	}
	
	public void atualizaAdmin(){
		if(administrativoBO.updateAdmin(admin)){
			admin = new Admin();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Admin atualizado com sucesso!",""));
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Não possivel atualizar admin!",""));
		}
	}
	
	public void atualizaAdmin(Admin admin){
		this.admin = admin;
	}
	
	public void restricao() {
		HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
		if("admin".equals(request.getParameter("token"))){
			RequestContext.getCurrentInstance().execute("PF('admin_login_paienl').show();");
			//RequestContext.getCurrentInstance().execute("PF('blackList').show();");
		}else if("admin-system".equals(request.getParameter("token"))){
			RequestContext.getCurrentInstance().execute("PF('login-admin-system').show();");
		}
	}
	
	public void closedDialog(CloseEvent event){
		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public String getMsgContadorTempo(){
		StringBuilder builder = new StringBuilder();
		
		if(contadorTempo != null && contadorTempo.getInicio() != null && contadorTempo.getTermino() == null){
			builder.append("Servidor iniciado em: ");
			builder.append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(contadorTempo.getInicio()));
			builder.append(" , pelo administrador: ");
			builder.append(admin.getId().equals(contadorTempo.getInicio_admin_id()) ? 
					admin.getEmail(): administrativoBO.getAdmin(contadorTempo.getInicio_admin_id()).getEmail());
		}else{
			if(serialPlayer != null && serialPlayer.getId() != null){
				ContadorTempo contadorTempoParaDemonstracao = contadorTempoBO.retornaUtilmaConcluidaBySerialPlayerId(serialPlayer.getId());
				if(contadorTempoParaDemonstracao.getId() != null){//Caso seja a primeira vez, ainda nao exite um contador
					builder.append("Ultimo log do servidor ao ser desativado foi em: ");
					builder.append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(contadorTempoParaDemonstracao.getInicio()));
					builder.append(" , pelo administrador: ");
					builder.append(admin.getId().equals(contadorTempoParaDemonstracao.getInicio_admin_id()) ? 
							admin.getEmail(): administrativoBO.getAdmin(contadorTempoParaDemonstracao.getInicio_admin_id()).getEmail());
					builder.append(", e foi finalizado em: ");
					builder.append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(contadorTempoParaDemonstracao.getTermino()));
					builder.append(" pelo administrador: ");
					builder.append(admin.getId().equals(contadorTempoParaDemonstracao.getTermino_admin_id()) ? 
							admin.getEmail(): administrativoBO.getAdmin(contadorTempoParaDemonstracao.getTermino_admin_id()).getEmail());
					builder.append(".\n Com um total de tempo de ");
					String[] hsm = contadorTempoParaDemonstracao.getTotalHoras().split(":");
					builder.append(hsm[0]+" horas: "+hsm[1]+" minutos: "+hsm[2]+" segundos");
				}
			}
		}
		
		return builder.toString();
	}
	
	public void getContadorTemposBySerialPlayer(){
		if(this.serialPlayer.getId() != null){
			contadorTempos = contadorTempoBO.getContadorTemposBySerialPlayer(serialPlayer.getId());
			totalHoraContadorTempo = contadorTempoBO.getContadorTempoRelatorio(serialPlayer.getId());
		}
	}
	
	public String getViewTableTotalHoraContadorTempo(int serialPlayerId){
		return contadorTempoBO.getContadorTempoRelatorio(serialPlayerId).replace("Total de horas: ", "");
	}
	
	public String indexRedirect(){
		return "index.xhtml";
	}
	
	public List<Admin> getAdmins(){
		return administrativoBO.findAdminAll();
	}
	
	public Integer getTotalContasUsadas(Integer id){
		return id != null ? this.serialPlayerBO.getCountSerialToUsuarioId(id) : 0; 
	}
	
	public String getStatusServidorSerialPlayer(boolean status){
		return status ? "Ativo" : "Inativo";
	}
	
	public boolean isNotNullRendered(String value){
		return value != null && !value.isEmpty();
	}
	
	public boolean isNotNullPrintTelaBytes(){
		return this.printTelaBytes != null;
	}
	
	public boolean isNotNullProcessosTela(){
		return this.processosTela != null;
	}
	
	public boolean rederedViewAdminIsNotNull(){
		return admin.getId() != null;
	}
	
	public boolean rederedViewSerialPlayerIsNotNull(){
		return  serialPlayer.getId() != null;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPrintTela() {
		return printTela;
	}

	public void setPrintTela(String printTela) {
		this.printTela = printTela;
	}
	
	public SerialPlayer getSerialPlayer() {
		return serialPlayer;
	}
	
	public void setSerialPlayer(SerialPlayer serialPlayer) {
		this.serialPlayer = serialPlayer;
	}
	
	public String getProcessosTela() {
		return processosTela;
	}
	
	public void setProcessosTela(String processosTela) {
		this.processosTela = processosTela;
	}
	
	public String getSiglaParaHash() {
		return siglaParaHash;
	}
	
	public void setSiglaParaHash(String siglaParaHash) {
		this.siglaParaHash = siglaParaHash;
	}
	
	public Admin getAdmin() {
		return admin;
	}
	
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	public ContadorTempo getContadorTempo() {
		return contadorTempo;
	}
	
	public void setContadorTempo(ContadorTempo contadorTempo) {
		this.contadorTempo = contadorTempo;
	}
	
	public List<ContadorTempo> getContadorTempos() {
		return contadorTempos;
	}
	
	public void setContadorTempos(List<ContadorTempo> contadorTempos) {
		this.contadorTempos = contadorTempos;
	}
	
	public String getTotalHoraContadorTempo() {
		return totalHoraContadorTempo;
	}
	
	public void setTotalHoraContadorTempo(String totalHoraContadorTempo) {
		this.totalHoraContadorTempo = totalHoraContadorTempo;
	}
	
	public List<JogadorWarface> getBlackList() {
		return blackList;
	}
	
	public void setBlackList(List<JogadorWarface> blackList) {
		this.blackList = blackList;
	}
	
	public String getCaminhoAudio() {
		return caminhoAudio;
	}
	
	public void setCaminhoAudio(String caminhoAudio) {
		this.caminhoAudio = caminhoAudio;
	}

	public StreamedContent getFile() {
		InputStream stream = new ByteArrayInputStream(this.printTelaBytes);
        file = new DefaultStreamedContent(stream, "image/jpg", "Print.jpg");
        return file;
    }
	
	public void limpaCachVar(){
		this.email = null;
		this.senha = null;
	}
	
	public void modoDesenvolvimentoAdminSystem(){
		serialPlayer = new SerialPlayer();
		serialPlayerBO = new SerialPlayerBO();
		admin = new Admin();
		administrativoBO.autenticarAdminSystem("admin", "admin");
		RequestContext.getCurrentInstance().execute("PF('painel-admin-system').show();");
	}
	
	public void modoDesenvolvimentoAdmin(){
		admin = new Admin();
		serialPlayer = new SerialPlayer();
		serialPlayerBO = new SerialPlayerBO();
		contadorTempoBO = new ContadorTempoBO();
		
		admin = administrativoBO.autenticar("lbw", "123");
		contadorTempo = contadorTempoBO.retornaUltimoContadorAtivo(admin.getSerialPlayerId());
		this.serialPlayer = serialPlayerBO.findById(admin.getSerialPlayerId());
		RequestContext.getCurrentInstance().execute("PF('admin_painel').show();");
	}

}