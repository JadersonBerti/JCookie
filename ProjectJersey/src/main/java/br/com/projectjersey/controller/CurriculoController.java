package br.com.projectjersey.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import br.com.projectjersey.business.PessoaBO;
import br.com.projectjersey.entity.Habilidade;
import br.com.projectjersey.entity.HabilidadePessoa;
import br.com.projectjersey.entity.Pessoa;
import br.com.projectjersey.exception.RestException;
import br.com.projectjersey.json.PessoaJson;
import br.com.projectjersey.util.MesageUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Path("/curriculo")
public class CurriculoController {

	private PessoaBO pessoaBO;
	
	public CurriculoController() {
		pessoaBO = new PessoaBO();
	}
	
	@POST
	@Path("/cadastro")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String cadastro(@FormParam("nome") final String nome, 
			@FormParam("formacao") String formacao,
			@FormParam("habilidades") String jsonHabilidades,
			@FormParam("curriculo") String curriculoBase64) throws Exception{
		
		if(StringUtils.isBlank(nome)){
			throw new RestException(400, String.format(MesageUtil.MESAGE_ERRO_400, "nome"));
		}

		if(StringUtils.isBlank(formacao)){
			throw new RestException(400, String.format(MesageUtil.MESAGE_ERRO_400, "formacao"));
		}else if(formacao.contains("{")){
			throw new RestException(400, String.format(MesageUtil.MESAGE_ERRO_400, "formacao"));
		}
		
		if(StringUtils.isBlank(jsonHabilidades)){
			throw new RestException(400, String.format(MesageUtil.MESAGE_ERRO_400, "habilidades"));
		}
		
		if(StringUtils.isBlank(curriculoBase64)){
			throw new RestException(400, String.format(MesageUtil.MESAGE_ERRO_400, "curriculo"));
		}

		Type listType = new TypeToken<ArrayList<Habilidade>>(){}.getType();
		List<Habilidade> habilidades = new Gson().fromJson(jsonHabilidades, listType);

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		pessoa.setFormacao(formacao);
		pessoa.setCurriculo(Base64.decodeBase64(curriculoBase64));
		
		List<HabilidadePessoa> habilidadePessoas = new ArrayList<HabilidadePessoa>();
		for(Habilidade habilidade : habilidades){
			HabilidadePessoa habilidadePessoa = new HabilidadePessoa();
			habilidadePessoa.setPessoa(pessoa);
			habilidadePessoa.setHabilidade(habilidade);
			habilidadePessoa.setNivel(habilidade.getNivel());
			habilidadePessoas.add(habilidadePessoa);
		}
		pessoa.setPessoas(habilidadePessoas);
		pessoaBO.cadastro(pessoa);
		
		return MesageUtil.MESAGE_OK;
	}
	
	@GET
	@Path("/consulta")
	@Produces(MediaType.APPLICATION_JSON)
	public String consulta() throws Exception {
		try{
			List<Pessoa> pessoas = pessoaBO.findAll();
			return PessoaJson.parseJsonConsulta(pessoas);
		}catch(Exception e){
			throw new RestException(507, MesageUtil.MESAGE_ERRO_507);	
		}
	}
	
}