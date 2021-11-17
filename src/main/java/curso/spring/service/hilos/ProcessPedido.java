package curso.spring.service.hilos;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import curso.spring.controller.ProductosController;
import curso.spring.model.Pedidos;
import curso.spring.service.PedidosService;

@Component
public class ProcessPedido {
	
	@Autowired
	PedidosService ps;
	
	Logger logger = LogManager.getLogger(ProductosController.class);

	
	private final String ESTADO = "pendiente";
	private final String ESTADO_ENVIADO = "enviado";


	@Scheduled(fixedRate = 600000, initialDelay = 600000)
	public void procesarPedidos() {
		
		List<Pedidos> listPedidos = ps.procesoPedidos(ESTADO);
		
		for (Pedidos pedido : listPedidos) {
			if (ps.procesarEstado(pedido, ESTADO_ENVIADO)) {
				logger.info("Lista pedidos pendiente, cambio de estado correctamente");
			}else {
				logger.error("Lista pedidos pendiente, cambio de estado correctamente");

			}
		}
		
	}
}
