package curso.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.spring.model.DetallesPedido;

public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Integer>{

	@Query(value="select * from detalles_pedido where pedido_id=?1", nativeQuery=true)
	List<DetallesPedido> getDetailsByPedido(Integer id_pedido);
	
	@Query(value="select * from detalles_pedido where pedido_id=?1", nativeQuery=true)
	List<DetallesPedido> getByPedidoId(Integer pedido_id);
	
	

}
