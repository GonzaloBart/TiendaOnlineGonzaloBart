package curso.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.spring.model.Productos;
import curso.spring.model.Valoraciones;


public interface ValoracionesRepository extends JpaRepository<Valoraciones, Integer>{
	
	@Query(value="select * from valoraciones where producto_id=?1", nativeQuery=true)
	List<Valoraciones> getValByIdProduct(Integer id_producto);

}
