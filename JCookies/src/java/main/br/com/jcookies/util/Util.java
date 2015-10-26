package br.com.jcookies.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	public static String getNameProjetc(String realPath){
		realPath = realPath.replaceAll("\\\\", "&");
		String[] projeto = realPath.split("&");
		return projeto[projeto.length - 1];	
	}
	
    public static boolean isJson(String value) {
    	String[] pattener = {"{","}"};
    	boolean parameterOne = String.valueOf(value.charAt(0)).equals(pattener[0]);
    	boolean parameterTwo = String.valueOf(value.charAt(value.length())).equals(pattener[1]);
    	
    	return parameterOne && parameterTwo;
	}
	
	public static String gerarHashMD5 (String senha) throws Exception {  
	    MessageDigest md = MessageDigest.getInstance("MD5");  
	    BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
	    String crypto = hash.toString(16);  
	    if (crypto.length() %2 != 0)  
	        crypto = "0" + crypto;  
	    return crypto;  
	}
	
	public static String parseHashMD5(String value){
		try {
			MessageDigest securty;
			securty = MessageDigest.getInstance("MD5");
		    securty.update(value.getBytes(),0,value.length());
		    BigInteger hash = new BigInteger(1, securty.digest(value.getBytes()));  

		    return hash.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static byte[] inputStreamToByte(InputStream inputStream, Long size) throws Exception {
		byte[] bytes = new byte[size.intValue()];  
		int offset = 0;  
		int numRead = 0;  
		while (offset < bytes.length && (numRead=inputStream.read(bytes, offset, bytes.length-offset)) >= 0) {  
		    offset += numRead;  
		}  
		          
		if (offset < bytes.length) {  
		    throw new IOException("Could not completely read file ");  
		}
		
		return bytes;
	}
	
	public static InputStream byteArrayToInpuStream(byte[] arquivo){
		try(ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arquivo)){
			arrayInputStream.read(arquivo);
			return arrayInputStream;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void timeOut(long timeInillis) {
		try{
			Thread.sleep(timeInillis);;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
