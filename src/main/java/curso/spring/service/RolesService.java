package curso.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.spring.model.Configuracion;
import curso.spring.model.Productos;
import curso.spring.model.Roles;
import curso.spring.repository.RolesRepository;

@Service
public class RolesService {
	
	@Autowired
	RolesRepository rolesRepo;
		
	
	public void addRol(Roles rol) {

		rolesRepo.save(rol);
	}
	
	public Roles getRolById(Integer id) {

			
		
		return rolesRepo.getById(id);

	}
	
	public List<Roles> getAllRoles(){
		
		List<Roles> list = rolesRepo.findAll();
		
		return list;	
	}

}
