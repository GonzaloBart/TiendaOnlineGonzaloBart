package curso.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.spring.model.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
	
	@Query(value="select * from productos where categoria_id=?1", nativeQuery=true)
	List<Productos> getProductsByCategoria(Integer id_categoria);

	@Query(value="select * from productos where precio<=?1 and precio >=?2", nativeQuery=true)
	List<Productos> getProductsByPrecio(Double precio1, Double precio2);
	
	@Query(value="select * from productos where nombre=?1", nativeQuery=true)
    public List<Productos> search(String keyword);
	
	@Query(value="select stock from productos p where id=?1", nativeQuery=true)
    public int checkStock(int id);
	
	public Productos findById(int id);

}
