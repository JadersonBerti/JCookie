package br.com.jcookies.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jcookies.util.Util;

public class GenericCookie {

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    private final int defaultTime = 60 * 60 * 24 * 30; // expira em 1 mês  
    private final String defaultComment = this.nameDefaultCookie();
    private List<Cookie> cookies = new ArrayList<>();
    
    public GenericCookie(HttpServletRequest request, HttpServletResponse response) {
    	this.request = request;
    	this.response = response;
    	cookies = Arrays.asList(this.request.getCookies());
    }
    	
    public void createCookiesBasicDefault(String value, String nameCookie){
		Cookie cookie = new Cookie(nameCookie, value);  
		cookie.setComment(this.defaultComment);  
	    cookie.setMaxAge(this.defaultTime);
	    cookie.setPath(this.request.getContextPath());
	    cookie.setVersion(1);
	    
	    saveCookie(cookie);
	}
    
    public void createCookiesCustom(String value, String comment, String domain, Integer time, 
    		Integer version,  String nameCookie){
		Cookie cookie = new Cookie(nameCookie, value);  
		cookie.setComment(comment != null && !comment.isEmpty() ? comment : this.defaultComment);  
	    cookie.setMaxAge(time != null ? time : this.defaultTime);
	    cookie.setPath(this.request.getContextPath());
	    if(domain != null)
	    		cookie.setDomain(domain);
	    	
	    cookie.setVersion(1);
	    saveCookie(cookie);
	}
    
    /**
     * Caso retorne false e por que 
     * nao existe uma cookie com esse nome
     * 
     * @param String name
     * @return boolean status
     */
    public boolean updateCookieByName(String name, String value, String comment, 
    		String domain, Integer time, Integer version){
    	Cookie cookie = getCookie(name);
    	
    	if(cookie != null){
    		cookie.setComment(comment != null && !comment.isEmpty() ? comment : this.defaultComment);  
    	    cookie.setMaxAge(time != null ? time : this.defaultTime);
    	    cookie.setPath(this.request.getContextPath());
    	    if(domain != null)
    	    		cookie.setDomain(domain);
    	    	
    	    cookie.setVersion(1);
    	    saveCookie(cookie);
    	    
    	    this.saveCookie(cookie);
    	    return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean updateCookieByName(String name, String value){
    	Cookie cookie = getCookie(name);
    	
    	if(cookie != null){
    		cookie.setComment("Cash ExtremeAntCheat");  
    	    cookie.setMaxAge(this.defaultTime);
    	    cookie.setPath(this.request.getContextPath());
    	    cookie.setVersion(1);
    	    
    	    this.saveCookie(cookie);
    	    return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean removeCookie(String name){
    	Cookie cookie = getCookie(name);
    	
    	if(cookie != null){
    	    cookie.setMaxAge(0);
    	    this.saveCookie(cookie);
    	    return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean existCookieByName(String name){
 		cookies = Arrays.asList(this.request.getCookies());

 		if(cookies != null && !cookies.isEmpty()){
 			for (Cookie cookie : cookies) {  
 			     if (cookie.getName() != null && cookie.getName().equals(name)) {
 			    	 return true;  
 			     }  
 	         }
 		}
 		return false;
    }
    
    public boolean thisCookieIsJsonByName(String name){
    	cookies = Arrays.asList(this.request.getCookies());

 		if(cookies != null && !cookies.isEmpty()){
 			for (Cookie cookie : cookies) {  
 			     if (cookie.getName() != null && cookie.getName().equals(name)) {
 			    	 return Util.isJson(cookie.getValue());
 			     }  
 	         }
 		}
 		return false;
    }

	public Cookie getCookie(String name){
 		cookies = Arrays.asList(this.request.getCookies());

 		if(cookies != null && !cookies.isEmpty()){
 			for (Cookie cookie : cookies) {  
 			     if (cookie.getName() != null && cookie.getName().equals(name)) {
 			    	 return cookie;  
 			     }  
 	         }
 		}
 		return null;
    }
	
	public List<Cookie> getCookiesByNames(String[] names){
 		cookies = Arrays.asList(this.request.getCookies());
 		List<Cookie> newCookis = new ArrayList<>();
 		
 		for (int i = 0; i < names.length; i++) {
 			if(cookies != null && !cookies.isEmpty()){
 	 			for (Cookie cookie : cookies) {  
 	 			     if (cookie.getName() != null && cookie.getName().equals(names[i])) {
 	 			    	 newCookis.add(cookie);  
 	 			     }  
 	 	         }
 	 		}
		}
 		
 		return null;
    }
	
	public List<Cookie> getCookiesByNames(List<String> names){
 		cookies = Arrays.asList(this.request.getCookies());
 		List<Cookie> newCookis = new ArrayList<>();
 		
 		for (String name : names) {
 			if(cookies != null && !cookies.isEmpty()){
 	 			for (Cookie cookie : cookies) {  
 	 			     if (cookie.getName() != null && cookie.getName().equals(name)) {
 	 			    	 newCookis.add(cookie);  
 	 			     }  
 	 	         }
 	 		}
		}
 		
 		return null;
    }
    
	private void saveCookie(Cookie cookie){
		this.response.addCookie(cookie);
	}
	
	public void saveCookies(List<Cookie> cookies){
		for(Cookie cookie : cookies){
			this.response.addCookie(cookie);
		}
	}
	
	@SuppressWarnings("deprecation")
	private String realPath(){
		return this.request.getRealPath("/");
	}
	
	private String nameDefaultCookie(){
		return String.format("Cash %s", Util.getNameProjetc(this.realPath()));
	}
	
}
