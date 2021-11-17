package curso.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.spring.model.Configuracion;
import curso.spring.model.Productos;


public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer>{
	
	@Query(value="select * from configuracion where clave=?1", nativeQuery=true)
	List<Configuracion> getConfigByClave(String numFactura);

}
