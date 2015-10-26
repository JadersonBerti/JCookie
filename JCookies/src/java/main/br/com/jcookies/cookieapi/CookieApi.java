package br.com.jcookies.cookieapi;

import java.util.List;

import javax.servlet.http.Cookie;

public interface CookieApi {

	/** 
	 * Metodo para criar uma cookie rapida e simples
	 * @param String value, String nameCookie
	 * */
	public void createCookiesBasicDefault(String value, String nameCookie);
	 
	/**
	 * Metodo cria cookie personalizada
	 *  
	 *  @param String value, String comment, String domain, Integer time, 
	 * 	Integer version,  String nameCookie
	 * */
	public void createCookiesCustom(String value, String comment, String domain, Integer time, 
	    		Integer version,  String nameCookie);
	
    /**
     * Caso retorne false e por que 
     * nao existe uma cookie com esse nome
     * 
     * @param String name, String value, String comment, 
	    		String domain, Integer time, Integer version
     * @return boolean status
     */
	public boolean updateCookieByName(String name, String value, String comment, 
	    		String domain, Integer time, Integer version);
	 
	/**
     * Caso retorne false e por que 
     * nao existe uma cookie com esse nome
     * 
     * @param String name
     * @return boolean status
     */
	public boolean updateCookieByName(String name, String value);
	
	/**
     * Metodo para remover uma cookie pelo nome
     * 
     * @param String name
     * @return boolean status
     */
	public boolean removeCookie(String name);
	 
	/**
     * Busca uma cookie pelo nome
     * 
     * @param String name
     * @return Cookie
     */
	public Cookie getCookie(String name);
	
	/**
     * Busca uma lista de cookies por Array de nomes
     * 
     * @param String name
     * @return List<Cookie>
     */
	public List<Cookie> getCookiesByNames(String[] names);
	
	/**
     * Busca uma lista de cookies por list de nomes
     * 
     * @param String name
     * @return List<Cookie>
     */
	public List<Cookie> getCookiesByNames(List<String> names);
	 
	/**
     * Metodo salva apenas uma cookie por ves
     * 
     * @param Cookie cookie
     */
	public void saveCookie(Cookie cookie);
		
	/**
     * Metodo salva varias cookies
     * 
     * @param Cookie cookie
     */
	public void saveCookies(List<Cookie> cookies);
	
	/**
     * Verifica cookie pelo nome
     * 
     * @param String name
     * @return boolean
     */
	public boolean existCookieByName(String name);
	 
}