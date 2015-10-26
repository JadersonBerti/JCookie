package br.com.jcookies.generic;

import java.util.List;

public interface Cookie {

	public void createCookiesBasicDefault(String value, String nameCookie);
	 
	public void createCookiesCustom(String value, String comment, String domain, Integer time, 
	    		Integer version,  String nameCookie);
	 
	public boolean updateCookieByName(String name, String value, String comment, 
	    		String domain, Integer time, Integer version);
	 
	public boolean updateCookieByName(String name, String value);
	
	public boolean removeCookie(String name);
	 
	public CookieApi getCookie(String name);
	 
	public List<CookieApi> getCookiesByNames(List<String> names);
	 
	public void saveCookie(CookieApi cookie);
		
	public void saveCookies(List<CookieApi> cookies);
	 
}
