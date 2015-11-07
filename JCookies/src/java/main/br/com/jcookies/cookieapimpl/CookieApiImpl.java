package br.com.jcookies.cookieapimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jcookies.cookieapi.CookieApi;
import br.com.jcookies.generic.GenericCookie;

public class CookieApiImpl extends GenericCookie implements CookieApi {
    
	public CookieApiImpl(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	@Override
	public void createCookiesBasicDefault(String value, String nameCookie){
		Cookie cookie = new Cookie(nameCookie, value);  
		cookie.setComment(this.defaultComment);  
	    cookie.setMaxAge(this.defaultTime);
	    cookie.setPath(GenericCookie.request.getContextPath());
	    cookie.setVersion(1);
	    
	    saveCookie(cookie);
	}
    
	@Override
    public void createCookiesCustom(String value, String comment, String domain, Integer time, 
    		Integer version,  String nameCookie){
		Cookie cookie = new Cookie(nameCookie, value);  
		cookie.setComment(comment != null && !comment.isEmpty() ? comment : this.defaultComment);  
	    cookie.setMaxAge(time != null ? time : this.defaultTime);
	    cookie.setPath(GenericCookie.request.getContextPath());
	    if(domain != null)
	    		cookie.setDomain(domain);
	    	
	    cookie.setVersion(version != null ? version : 1);
	    cookie.setValue(value);
	    saveCookie(cookie);
	}
    
	@Override
    public boolean updateCookieByName(String value, String name, String comment, 
    		String domain, Integer time, Integer version){
    	Cookie cookie = getCookie(name);
    	
    	if(cookie != null){
    		cookie.setComment(comment != null && !comment.isEmpty() ? comment : this.defaultComment);  
    	    cookie.setMaxAge(time != null ? time : this.defaultTime);
    	    cookie.setPath(GenericCookie.request.getContextPath());
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
    
	@Override
    public boolean updateCookieByName(String value, String name){
    	Cookie cookie = getCookie(name);
    	
    	if(cookie != null){
    		cookie.setComment(this.defaultComment);  
    	    cookie.setMaxAge(this.defaultTime);
    	    cookie.setPath(GenericCookie.request.getContextPath());
    	    cookie.setVersion(1);
    	    cookie.setValue(value);
    	    
    	    this.saveCookie(cookie);
    	    return true;
    	}else{
    		return false;
    	}
    }
    
	@Override
    public boolean removeCookie(String name){
    	Cookie cookie = getCookie(name);
    	
    	if(cookie != null){
    	    cookie.setMaxAge(0);
    	    cookie.setPath(GenericCookie.request.getContextPath());
    	    this.saveCookie(cookie);
    	    return true;
    	}else{
    		return false;
    	}
    }

	@Override
	public Cookie getCookie(String name){
 		cookies = Arrays.asList(GenericCookie.request.getCookies());

 		if(cookies != null && !cookies.isEmpty()){
 			for (Cookie cookie : cookies) {  
 			     if (cookie.getName() != null && cookie.getName().equals(name)) {
 			    	 return cookie;  
 			     }  
 	         }
 		}
 		return null;
    }
	
	@Override
	public List<Cookie> getCookiesByNames(String[] names){
 		cookies = Arrays.asList(GenericCookie.request.getCookies());
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
	
	@Override
	public List<Cookie> getCookiesByNames(List<String> names){
 		cookies = Arrays.asList(GenericCookie.request.getCookies());
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
    
	@Override
	public void saveCookie(Cookie cookie){
		GenericCookie.response.addCookie(cookie);
	}
	
	@Override
	public void saveCookies(List<Cookie> cookies){
		for(Cookie cookie : cookies){
			GenericCookie.response.addCookie(cookie);
		}
	}
	
	@Override
    public boolean existCookieByName(String name){
		boolean hasValueCookie = GenericCookie.request.getCookies() != null && 
				GenericCookie.request.getCookies().length > 0;
				
		cookies = hasValueCookie ? Arrays.asList(GenericCookie.request.getCookies()) : null;
 		if(cookies != null && !cookies.isEmpty()){
 			for (Cookie cookie : cookies) {  
 			     if (cookie.getName() != null && cookie.getName().equals(name)) {
 			    	 return true;  
 			     }  
 	         }
 		}
 		return false;
    }
	
}
