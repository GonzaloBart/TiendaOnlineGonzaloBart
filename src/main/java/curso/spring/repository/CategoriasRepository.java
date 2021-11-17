package curso.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.spring.model.Categorias;
import curso.spring.model.Productos;

public interface CategoriasRepository extends JpaRepository<Categorias, Integer>{

}
