package br.com.projectjersey.controller;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import br.com.projectjersey.business.HabilidadeBO;
import br.com.projectjersey.entity.Habilidade;
import br.com.projectjersey.exception.RestException;
import br.com.projectjersey.json.HabilidadeJson;
import br.com.projectjersey.util.MesageUtil;

@Path("habilidade")
public class HabilidadesController {

	private HabilidadeBO habilidadeBO; 
	
	public HabilidadesController() {
		habilidadeBO = new HabilidadeBO();
	}
	
	@POST
	@Path("/cadastro")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String cadastro(@FormParam("descricao") String descricao) throws Exception{
		
		if(StringUtils.isBlank(descricao)){
			throw new RestException(400, String.format(MesageUtil.MESAGE_ERRO_400, "descricao"));
		}
		
		try{
			Habilidade habilidade = new Habilidade();
			habilidade.setDescricao(descricao);
			habilidadeBO.cadastrar(habilidade);
			
			return MesageUtil.MESAGE_OK;
		}catch(Exception e){
			throw new RestException(507, "Problema ao persistir as informações");
		}
	}
	
	@GET
	@Path("/consulta")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String consulta() throws Exception {
		try{
			List<Habilidade> habilidades = habilidadeBO.findAll();
			return HabilidadeJson.parseJsonConsulta(habilidades);
		}catch(Exception e){
			throw new RestException(507, MesageUtil.MESAGE_ERRO_507);
		}
	}
	
	
}
