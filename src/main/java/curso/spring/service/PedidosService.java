package curso.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.spring.model.Configuracion;
import curso.spring.model.DetallesPedido;
import curso.spring.model.Pedidos;
import curso.spring.repository.PedidosRepository;
import curso.spring.service.pdf.GenerarPDF;

/**
 * Servicio Pedidos logica para poblar DB con datos de la compra tras realizar pago
 * @author 
 *
 */
@Service
public class PedidosService {
	
	@Autowired
	private PedidosRepository pedidosRepo;
	
	@Autowired
	private ConfiguracionService configS;
	
	@Autowired
	private DetallesPedidoService dS;


	

	public void editPedido(Pedidos pedido) {

		pedidosRepo.save(pedido);
	}

	public void deletePedido(Integer id) {

		Pedidos p = pedidosRepo.getById(id);
		pedidosRepo.delete(p);
	}

	public void addPedido(Pedidos pedido) {

		pedidosRepo.save(pedido);
	}

	public List<Pedidos> getAllPedidos(){

		List<Pedidos> listPedidos = pedidosRepo.findAll();

		return listPedidos;
	}


	public Pedidos getPedidoById(Integer id) {

		return pedidosRepo.getById(id);

	}

	public List<Pedidos> getPedidosByUsuario(int id_usuario) {

		List<Pedidos> listPedidos = pedidosRepo.getPedidoByUser(id_usuario);
		
		return listPedidos;

	}
	
	
	/**
	 * Procesa el estado del pedido, crea numFactura
	 * @param pedido
	 * @param estado
	 * @return
	 */
	public boolean procesarEstado(Pedidos pedido, String estado) {
		
		boolean flag = false;
				
		pedido.setEstado(estado);
		
		editPedido(pedido);
		
		if (pedido.getEstado().equals("enviado")) {
			
			pedido.setNumFactura(crearNumFactura("numFactura"));
			
			editPedido(pedido);
			
			flag = true;
		}
		
		return flag;		
	}

	/**
	 * 
	 * @param clave from configuración
	 * @return
	 * 
	 * Crea el num de Factura con los datos de la tabla Configuraciones y actualiza el valor en esta
	 */
	
	public String crearNumFactura(String clave) {
		
		List<Configuracion> config = configS.getConfigByClave(clave);

				
		for (Configuracion configuracion : config) {
			
			String valor = configuracion.getValor();
			
			int contador = Integer.parseInt(configuracion.getValor()) + 1;
			
			configuracion.setValor(Integer.toString(contador));
			
			configS.editConfiguracion(configuracion);
			
			
			return valor;
		} 
			
			return null;
}

	/**
	 * Canecla Pedido entero y Detalles de Pedido
	 * @param pedidoId
	 * @return
	 */
	public boolean cancelarPedido(int pedidoId) {
		
		Pedidos pedido = pedidosRepo.getById(pedidoId);
		
		List<DetallesPedido> list = dS.getDetallesByPedido(pedidoId);
		
		for (DetallesPedido detalle : list) {
			dS.cancelarDetalle(detalle.getId());
		}
		
		deletePedido(pedidoId);
		
		return false;
		
	}

	/**
	 * Metodo para listar todos los pedidos por estado - usado para proceso hilo
	 * @param estado
	 * @return
	 */
	public List<Pedidos> procesoPedidos(String estado) {
		List<Pedidos> list = pedidosRepo.getPedidoByEstado(estado);
		
		return list;
	}
}
