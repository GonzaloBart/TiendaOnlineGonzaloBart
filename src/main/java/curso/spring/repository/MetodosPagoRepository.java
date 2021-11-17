package curso.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.spring.model.MetodosPago;


public interface MetodosPagoRepository extends JpaRepository<MetodosPago, Integer>{

}
