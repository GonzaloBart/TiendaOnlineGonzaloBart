package curso.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import curso.spring.controller.ProductosController;
import curso.spring.model.DetallesPedido;
import curso.spring.model.Pedidos;
import curso.spring.model.Productos;
import curso.spring.repository.DetallesPedidoRepository;
import curso.spring.repository.ProductosRepository;
import curso.spring.service.pdf.GenerarPDF;

/**
 * Clase Servicio logica Detalles de Pedido
 * @author Newland
 *
 */
@Service
public class DetallesPedidoService {

	Logger logger = LogManager.getLogger(ProductosController.class);


	@Autowired
	private DetallesPedidoRepository detallesRepo;

	@Autowired
	private ProductosService productoServ;

	@Autowired
	private PedidosService pedidoServ;



	public void editDetallePedido(DetallesPedido detallesP) {

		detallesRepo.save(detallesP);
	}

	public void deleteDetallePedido(Integer id) {

		DetallesPedido p = detallesRepo.getById(id);
		detallesRepo.delete(p);
	}

	public void addDetallePedido(DetallesPedido detallesP) {

		detallesRepo.save(detallesP);
	}

	public List<DetallesPedido> getAllDetallesPedido(){

		List<DetallesPedido> listDetallesP = detallesRepo.findAll();

		return listDetallesP;
	}


	public DetallesPedido getDetallePedidoById(Integer id) {

		return detallesRepo.getById(id);

	}

	/**
	 * Lista Detalles de Pedido por ID de pedido 
	 * @param id_pedido
	 * @return List Detalles de Pedido de un pedido
	 */
	public List<DetallesPedido> getDetallesByPedido(Integer id_pedido) {

		List<DetallesPedido> listDetallesP = detallesRepo.getDetailsByPedido(id_pedido);

		return listDetallesP;
	}

	/**
	 * Cancela el producto del pedido y actualiza stock. Si el el pedido solo 
	 * consta de ese producto elimina pedido
	 * @param id_pedido
	 * @return boolean para validacion
	 */
	public boolean cancelarDetalle(Integer id_detalle) {

		boolean flag = false;

		DetallesPedido detalles = detallesRepo.getById(id_detalle);

		int numPedido = detalles.getPedido().getId();

		productoServ.actualizarStockCancelacion(detalles.getProducto().getId(), detalles.getUnidades());

		detallesRepo.delete(detalles);

		List<DetallesPedido> list = detallesRepo.getDetailsByPedido(numPedido);

		if (list.isEmpty()) {
			pedidoServ.deletePedido(numPedido);
		} else {
			actualizarPedidoCancelDetalle(numPedido);
		}

		return flag;		
	}

	/**
	 * Actualizar Pedido al cancelar parcialmente
	 * @param numPedido
	 */
	public void actualizarPedidoCancelDetalle(Integer numPedido){

		Pedidos p = pedidoServ.getPedidoById(numPedido);
		double total = 0;


		try {
			List<DetallesPedido> list = detallesRepo.getDetailsByPedido(numPedido);
			for (DetallesPedido detalle : list) {
				total+= detalle.getTotal();
			}
			p.setTotal(total);

		} catch (Exception e) {
			logger.error(e+ "Error accediendo detalles de un Pedido");
		}

		pedidoServ.editPedido(p);

	}
	
	
}
