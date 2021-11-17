package curso.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.spring.model.Productos;
import curso.spring.model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer>{
	
	public Roles getById(int id);
	
	@Query(value="select * from roles", nativeQuery=true)
	public List<Roles> getAllRoles();
	


}
