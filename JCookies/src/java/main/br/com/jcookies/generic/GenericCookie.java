package br.com.jcookies.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jcookies.cookieapi.CookieApi;
import br.com.jcookies.cookieapimpl.CookieApiImpl;
import br.com.jcookies.enums.TimeSleep;
import br.com.jcookies.util.Util;

public abstract class GenericCookie {

	/** How to use api  = CookieApi cookieApi = GenericCookie.getInstance(request, response); **/
	
	/** Tanto request qunato responce estao estaticos 
	 * pois serao usados constantemente **/
	protected static HttpServletRequest request = null;
	protected static HttpServletResponse response = null;
	
	/** Um load basico de dados que serao usados com default **/
	protected final int defaultTime = TimeSleep.TRINTA_DIAS.getTime(); // expira em 1 mês  
	protected String defaultComment;
	
	protected List<Cookie> cookies = new ArrayList<>();
    
	/** Construtor para carregar a api **/
    public GenericCookie(HttpServletRequest request, HttpServletResponse response) {
    	GenericCookie.request = request;
    	GenericCookie.response = response;
    	this.defaultComment = this.nameDefaultCookie();
    	cookies = arrayCookieIsNotNull() ? Arrays.asList(GenericCookie.request.getCookies()) : null;
    }
    
    /** Load da api **/
    public static CookieApi getInstance(HttpServletRequest request, HttpServletResponse response){
    	return new CookieApiImpl(request, response);
    }
    
    protected boolean thisCookieIsJsonByName(String name){
    	cookies = Arrays.asList(GenericCookie.request.getCookies());

 		if(cookies != null && !cookies.isEmpty()){
 			for (Cookie cookie : cookies) {  
 			     if (cookie.getName() != null && cookie.getName().equals(name)) {
 			    	 return Util.isJson(cookie.getValue());
 			     }  
 	         }
 		}
 		return false;
    }

    private boolean arrayCookieIsNotNull(){
    	return GenericCookie.request.getCookies() != null;
    }
	
	@SuppressWarnings("deprecation")
	private String realPath(){
		return GenericCookie.request.getRealPath("/");
	}
	
	protected String nameDefaultCookie(){
		return String.format("Cash %s", Util.getNameProjetc(this.realPath()));
	}
	
}
