package br.com.projectjersey.webserviceteste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")//DefaultHttpClient
public class WebServiceTeste {

	/**
	 * Informacoes dos testes.
	 * Antes de inicar os teste e necessario inicar 
	 * o servidor com projeto. 
	 * 
	 * */
	
	private byte[] curriculo = new byte[1024];
	
	@Before
	public void init(){
		try(InputStream in = getClass().getResourceAsStream("/curriculo/berti.pdf")){
			IOUtils.read(in, curriculo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void cadastroCurriculo(){
		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost("http://localhost:8080/ProjectJersey/curriculo/cadastro");
	    try {
	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      nameValuePairs.add(new BasicNameValuePair("nome", "JadersonPost"));
	      nameValuePairs.add(new BasicNameValuePair("formacao", "FormacaoPost"));
	      nameValuePairs.add(new BasicNameValuePair("habilidades", "[{\"id\":1, \"nivel\":2}, {\"id\":15, \"nivel\":3}]"));
	      nameValuePairs.add(new BasicNameValuePair("curriculo", Base64.encodeBase64String(curriculo)));
	      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 
	      HttpResponse response = client.execute(post);
	      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	      String line,retorno = "";
	      while ((line = rd.readLine()) != null) {
	    	  retorno += line;
	      }

	      boolean sucess = retorno.contains("200");
	      
	      Assert.assertTrue(sucess);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	@Test
	public void consultaCurriculo() throws Exception{
		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://localhost:8080/ProjectJersey/curriculo/consulta");
		
		HttpResponse response = client.execute(request);
		
		// Get the response
		BufferedReader rd = new BufferedReader
		  (new InputStreamReader(response.getEntity().getContent()));
		    
		String line = "";
		StringBuilder retorno = new StringBuilder();
		while ((line = rd.readLine()) != null) {
		  retorno.append(line);
		} 
		
		boolean responce = true;
		if(retorno.toString().contains("400")){
			responce = false;
		}else if(retorno.toString().contains("507")){
			responce = false;
		}
		
		Assert.assertTrue(responce);
	}
	
	@Test
	public void cadastroHabilidade(){
		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost("http://localhost:8080/ProjectJersey/habilidade/cadastro");
	    try {
	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      nameValuePairs.add(new BasicNameValuePair("descricao","testePost"));
	      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 
	      HttpResponse response = client.execute(post);
	      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	      String line,retorno = "";
	      while ((line = rd.readLine()) != null) {
	    	  retorno += line;
	      }

	      boolean sucess = retorno.contains("200");
	      
	      Assert.assertTrue(sucess);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	@Test
	public void consultaHabilidade() throws Exception{
		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://localhost:8080/ProjectJersey/habilidade/consulta");
		
		HttpResponse response = client.execute(request);
		
		// Get the response
		BufferedReader rd = new BufferedReader
		  (new InputStreamReader(response.getEntity().getContent()));
		    
		String line = "";
		StringBuilder retorno = new StringBuilder();
		while ((line = rd.readLine()) != null) {
		  retorno.append(line);
		} 
		
		boolean responce = true;
		if(retorno.toString().contains("400")){
			responce = false;
		}else if(retorno.toString().contains("507")){
			responce = false;
		}
		
		Assert.assertTrue(responce);
	}
	
}
