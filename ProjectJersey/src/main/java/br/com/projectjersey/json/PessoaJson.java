package br.com.projectjersey.json;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import br.com.projectjersey.entity.HabilidadePessoa;
import br.com.projectjersey.entity.Pessoa;


public class PessoaJson {
	
	public static String parseJsonConsulta(List<Pessoa> pessoas){
		JsonArrayBuilder jsonArrayPessoa = Json.createArrayBuilder();
		for(Pessoa pessoa : pessoas){
			JsonObjectBuilder jsonObject = Json.createObjectBuilder()
					.add("nome", pessoa.getNome())
					.add("formacao", pessoa.getFormacao())
					.add("habilidade", parsePessoaToHabilidade(pessoa.getPessoas()))
					.add("arquivo", Base64.encodeBase64String(pessoa.getCurriculo()));
			jsonArrayPessoa.add(jsonObject);
		}
		
		return Json.createArrayBuilder().add(jsonArrayPessoa).build().toString();
	}

	public static JsonArrayBuilder parsePessoaToHabilidade(List<HabilidadePessoa> habilidades){
		JsonArrayBuilder jsonArrayHabilidade = Json.createArrayBuilder();
		for(HabilidadePessoa habilidade : habilidades){
			if(StringUtils.isNotBlank(habilidade.getHabilidade().getDescricao())){
				jsonArrayHabilidade.add(habilidade.getHabilidade().getDescricao());
			}	
		}
		return jsonArrayHabilidade;
	}
	
}
