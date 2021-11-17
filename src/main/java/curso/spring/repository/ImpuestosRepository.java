package curso.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.spring.model.Impuestos;
import curso.spring.model.Productos;
import curso.spring.model.Roles;

@Repository
public interface ImpuestosRepository extends JpaRepository<Impuestos, Integer>{
	
	public Impuestos getById(int id);


}
