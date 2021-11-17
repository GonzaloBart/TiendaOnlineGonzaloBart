package curso.spring.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import curso.spring.model.Usuarios;
import curso.spring.repository.UsuariosRepository;

@Service
public class UsuariosService {

	@Autowired
	UsuariosRepository userRepo;
	
	
	public Usuarios getUserByEmail(String email) {

		Usuarios user = userRepo.findByEmail(email);
		
		return user;
	}
	
	public Integer getIdUsuarioByEmail(String email) {
		Usuarios u = userRepo.findByEmail(email);
		return u.getId();
		
	}
	

	public boolean checkUserExist(String email) {
		
		if (userRepo.findByEmail(email) != null) {
			return true;
		}
				
		return false;	
	}

	/**
	 * User login metodo, comprueba usuario email y clave
	 * @param usuario
	 * @return true or false si existe 
	 */
	public boolean checkUserLogin(Usuarios usuario) {

		boolean login = false;
		
		List<Usuarios> user = userRepo.findUsuarioLogin(usuario.getEmail(), encriptarPass(usuario.getClave()));

		if (!user.isEmpty()) {
			login = true;
		}

		return login;
	}


	/**
	 * Lista todos los usuarios independientemente del rol
	 * @return
	 */
	public List<Usuarios> getListaUsuarios() {
		
		return userRepo.findAll();
	}
	
	public void addUsuario(Usuarios usuario) {
		userRepo.save(usuario);
	}
	
	public void delUsuario(int id) {

		Usuarios u = userRepo.findById(id).get(0);
		u.setBaja(true);
		userRepo.save(u); 
	}
	
	public void editUsuario(Usuarios usuario) {
		userRepo.save(usuario);		
	}

	public Usuarios getUsuarioById(int id) {
		Usuarios u = userRepo.getById(id);
		return u;
	}
	
	
	/**
	 * Encriptacion Contraseña Servidor
	 * @param pass
	 * @return clave encriptada
	 */
	//TODO
	public String encriptarPass(String pass) {
		Base64 base64 = new Base64();

		String encriptada = new String(base64.encode(pass.getBytes()));
		
		return encriptada;
		
	}
	
	/**
	 * Lista los usuarios que tenga un rol especifico
	 * @param rol_id
	 * @return
	 */
	public List<Usuarios> getUsersByIdRol(int rol_id){
		return userRepo.getUsuariosByIdRol(rol_id);	
	}
	

}
