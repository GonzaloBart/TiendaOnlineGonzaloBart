package curso.spring.service;

import java.util.ArrayList;

import curso.spring.model.Productos;
import curso.spring.model.UnidadesCarrito;



public class UnidadesCarritoService {

	// Añadir linea de pedido al carrito
	public UnidadesCarrito crearLineaCarrito(Productos producto) {
		UnidadesCarrito linea = new UnidadesCarrito();
		linea.setProducto(producto);
		linea.setUnidades(1);
		linea.setTotal(producto.getPrecio());

		return linea;
	}

	public Double calcularTotal(UnidadesCarrito linea) {

		Double total = linea.getProducto().getPrecio() * linea.getUnidades();
		return total;
	}


	//Get num de articulos
	public Integer getUnitsCarrito(ArrayList<UnidadesCarrito> carrito) {

		Integer total = 0;

		for (UnidadesCarrito linea : carrito) {
			total += linea.getUnidades();
		}	
		return total;
	} 

	//---------- Carrito
	//Get total de carrito y de pedido
	public Double getTotalCarrito(ArrayList<UnidadesCarrito> carrito) {

		Double total = (double) 0;

		for (UnidadesCarrito linea : carrito) {
			total += linea.getUnidades() * linea.getProducto().getPrecio();
		}	
		return total;
	} 

	//Get Linea de Pedido por producto
	public UnidadesCarrito getLineaByProduct(ArrayList<UnidadesCarrito> carrito, Productos producto) {
		for (UnidadesCarrito linea : carrito) {
			if (linea.getProducto().getId() == producto.getId()) {
				return linea;
			}
		}
		return null;
	}
	
	public int getUnitsLinea(ArrayList<UnidadesCarrito> carrito, Productos producto) {

		Integer total = 0;
		UnidadesCarrito linea = new UnidadesCarrito();
		linea = getLineaByProduct(carrito, producto);
		
		total = linea.getUnidades();
		
		return total;
	} 

}
