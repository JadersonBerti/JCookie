Api - JCookies (Versao 1.0.0)

- JCookies é uma api para gerar cookies de forma rapida e simples.
Você pode tanto criar cookies de forma rapida com createCookiedefault da api como criar
cookies personalizada com createCookiesCustom.

Forma de ultilizar a Api JCookie.
Para ultilzar o Jcookie é necessario fazer um load na api com "GenericCookie.getInstance(request, response);"
esse load retorna "CookieApi cookieApi".
Alguns exemplos: 
	//Carregando a Api com (HttpServletRequest request, HttpServletResponse response)
	CookieApi cookieApi = GenericCookie.getInstance(request, response);
	
	//Forma rapida de criar uma cookie (String value, String nameCookie)
	cookieApi.createCookiesBasicDefault("valorASerGuardado","nomeDaCookie");
	
	//Forma Personalizada de criar uma cookie (String value, String comment, String domain, 
	Integer time, Integer version,  String nameCookie) 	
	cookie.createCookiesCustom("valorASerGuardado", "Cookie de login", "/localhost:8080", 
	Integer 10000, "1","nomeDaCookie")
	
	//Buscando uma cookie (String name)
	cookie.getCookie("nomeDaCookie")
	
	//Verificando cookie pelo nome (String name) 
	cookie.existCookieByName("nomeDaCookie")
	
	
Tamanho da api: 9.492  bytes
Dependencia: 
Donwload do jar: https://github.com/JadersonBerti/JCookie/blob/master/JCookies/src/main/resources/JCookies.jar?raw=true