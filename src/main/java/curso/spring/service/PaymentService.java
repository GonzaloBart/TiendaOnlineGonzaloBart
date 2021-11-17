package curso.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import curso.spring.controller.MainController;
import curso.spring.model.Configuracion;
import curso.spring.model.DetallesPedido;
import curso.spring.model.MetodosPago;
import curso.spring.model.Pedidos;
import curso.spring.model.Productos;
import curso.spring.model.UnidadesCarrito;
import curso.spring.model.Usuarios;
import curso.spring.repository.ConfiguracionRepository;


/**
 * Clase Servicio logica proceso de pago
 * @author Newland
 *
 */
@Service
public class PaymentService {
	
	Logger logger = LogManager.getLogger(PaymentService.class);

	
	@Autowired
	PedidosService pedidoS;
	
	@Autowired
	DetallesPedidoService detalleS;
	
	@Autowired
	MetodosPagoService metodoS;
	

	
	@Autowired
	ProductosService productS;
	
	/**
	 * Añade pedido a la DB despues de realizar pago
	 * @param usuario
	 * @param id
	 * @param total
	 * @param estado
	 * @return
	 */
	public Pedidos addPedido(Usuarios usuario, Integer id, Double total, String estado) {
		
		Pedidos pedido = new Pedidos();
		
		pedido.setMetodoPago(metodoS.getMetodosPagoById(id));		
		pedido.setUsuario(usuario);
		pedido.setEstado(estado);
		pedido.setTotal(total);
		
		pedidoS.addPedido(pedido);
				
		return pedido;
			
	}
	
	/**
	 * Puebla DB con detalles de pedido
	 * @param carrito
	 * @param pedido
	 */
	public void addDetallesPedido(ArrayList<UnidadesCarrito> carrito, Pedidos pedido) {
		

		for (UnidadesCarrito unidades : carrito) {
			
			DetallesPedido detalles = new DetallesPedido();
			
			detalles.setPedido(pedido);
			detalles.setProducto(unidades.getProducto());
			
			// Cambiar a tabla
			detalles.setImpuesto((float) 21);
			
			detalles.setPrecioUnidad(unidades.getProducto().getPrecio());
			detalles.setUnidades(unidades.getUnidades());
			
			// Crear metodo calcular total detalles pedido
			detalles.setTotal(unidades.getUnidades() * unidades.getProducto().getPrecio());
			try {
				detalleS.addDetallePedido(detalles);
			} catch (Exception e) {
				logger.error(e + "Error añadir detalles Pedido durante pago");
			}
			
			try {
				productS.actualizarStockCompra(unidades.getProducto().getId(), unidades.getUnidades());
			} catch (Exception e) {
				logger.error(e + "Error actualizar durante pago");
			}

		}
		
		
	}
	
	
}
