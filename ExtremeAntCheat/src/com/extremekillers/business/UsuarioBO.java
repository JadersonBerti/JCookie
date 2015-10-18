package com.extremekillers.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import com.extremekillers.dao.UsuarioDAO;
import com.extremekillers.entity.Usuario;

public class UsuarioBO {
	
	private UsuarioDAO usuarioDAO;
	
	public UsuarioBO() {
		usuarioDAO = new UsuarioDAO();
	}

	public Usuario save(Usuario usuario,String projeto) throws Exception {
		usuario.setId(usuarioDAO.insert(usuario));
		
		if(usuario.getId() != null){
			//this.savefotoFolder(usuario.getFotoByte(), projeto,usuario.getId());
			return usuario;
		}
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private void savefotoFolder(byte[] foto,String projeto,Long id) throws Exception {
		String separator = System.getProperty("file.separator");
		File caminhoFoto = new File(projeto+separator+"upload-foto"+separator+id+".png");
		if(!caminhoFoto.exists()){
			caminhoFoto.createNewFile();
			try(OutputStream escritor = new FileOutputStream(caminhoFoto)){
				escritor.write(foto);  
			}
		}
	}

	public List<Usuario> findAll() {
		return usuarioDAO.findAll();
	}

	public boolean update(Usuario usuario) {
		return usuarioDAO.update(usuario);
	}

	public boolean detele(Long id) {
		return usuarioDAO.delete(id);
	}

	public Usuario autentica(String senha, String email) {
		return usuarioDAO.autentica(senha, email);
	}

	public Usuario findByFilterNameOrNick(String nomeUsuario) {
		return usuarioDAO.findByFilterNameOrNick(nomeUsuario);
	}

	public Integer getCountUsuarios() {
		return usuarioDAO.getCountUsuarios();
	}

	public List<Usuario> findFilter(int id,int resutadoMaxUsuario) {
		if(id == 0){
			return usuarioDAO.findAllLimit(5);
		}else{
			if(id < 10){
				return usuarioDAO.findFilter(id, id);
			}
			return usuarioDAO.findFilter(id, 5);
		}
	}

	public boolean validaNick(String nickJogo) {
		Integer id = usuarioDAO.validaNick(nickJogo);
		if(id == null || id == 0){
			return false;
		}
		return true;
	}

}
