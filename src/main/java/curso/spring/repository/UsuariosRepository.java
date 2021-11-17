package curso.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import curso.spring.model.Usuarios;


public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{

	List<Usuarios> findById(int id);
	
	@Query(value="select * from usuarios where email=?1", nativeQuery=true)
	Usuarios findByEmail(String email);
	
	
	@Query(value="select * from usuarios where email=?1 and clave=?2 and baja=0", nativeQuery=true)
	List<Usuarios> findUsuarioLogin(String email, String clave);
	
	@Query(value="select * from usuarios where rol_id=?1", nativeQuery=true)
	List<Usuarios> getUsuariosByIdRol(Integer rol_id);
	
}
