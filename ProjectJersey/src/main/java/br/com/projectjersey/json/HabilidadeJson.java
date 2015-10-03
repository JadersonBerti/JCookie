package br.com.projectjersey.json;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.apache.commons.lang.StringUtils;

import br.com.projectjersey.entity.Habilidade;

public class HabilidadeJson {

	public static String parseJsonConsulta(List<Habilidade> habilidades){
		JsonArrayBuilder jsonArrayHabilidade = Json.createArrayBuilder();
		for(Habilidade habilidade : habilidades){
			if(StringUtils.isNotBlank(habilidade.getDescricao())){
				jsonArrayHabilidade.add(habilidade.getDescricao());
			}	
		}
		JsonObjectBuilder jsonHabilidade = Json.createObjectBuilder().add("Habilidade", jsonArrayHabilidade);
		
		return Json.createArrayBuilder().add(jsonHabilidade).build().toString();
	}
	
}
