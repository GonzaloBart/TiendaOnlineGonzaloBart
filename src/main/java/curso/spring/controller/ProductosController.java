package curso.spring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import curso.spring.model.Productos;
import curso.spring.model.UnidadesCarrito;
import curso.spring.model.Valoraciones;
import curso.spring.service.ProductosService;
import curso.spring.service.UnidadesCarritoService;
import curso.spring.service.ValoracionesService;
import jxl.read.biff.BiffException;

/**
 * Controlador Gestion de pedidos
 * @author Gonzalo
 *
 */
@Controller
@RequestMapping("/product")
public class ProductosController {

	Logger logger = LogManager.getLogger(ProductosController.class);

	@Autowired
	ProductosService productoS;

	@Autowired
	ValoracionesService valServ;

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable int id) {

		productoS.deleteProduct(id);

		return "redirect:/product/list";
	}

	@GetMapping("/edit/{id}")
	public String actualizacionForm(@PathVariable int id, Model model) {

		Productos p = productoS.getProductoById(id);
		model.addAttribute("productos", p);

		return "product/edit";
	}

	@PostMapping("/edit/submit")
	public String actualizar(@ModelAttribute Productos producto) {
		productoS.editProduct(producto);
		return "redirect:/product/list";

	}

	@GetMapping("/add")
	public String addProduct(Model model) {

		model.addAttribute("producto", new Productos());

		return "product/new";
	}

	@PostMapping("/add/submit")
	public String submitProduct(@ModelAttribute Productos producto) {

		productoS.addProduct(producto);

		return "redirect:/product/list";	
	}	

	@GetMapping("/list")
	public String listProducts(Model model) {

		List<Productos> productList = productoS.getAllProducts();

		model.addAttribute("productList", productList);

		return "product/list";
	}


	/**
	 * Añade unidad al carrito comprobando stock
	 * @param session
	 * @param model
	 * @param id
	 * @return "redirect:/payment" - carrito
	 */
	@GetMapping("/addCarrito/{id}")
	public String addProductCarrito(HttpSession session, Model model, @PathVariable int id) {

		UnidadesCarritoService uCarritoS = new UnidadesCarritoService();

		UnidadesCarrito linea = new UnidadesCarrito();

		ArrayList<UnidadesCarrito> carrito = (ArrayList<UnidadesCarrito>) session.getAttribute("carrito");

		Productos producto = productoS.getProductById(id);

		if (producto != null) {

			if (productoS.checkStock(producto.getId()) > 0) {

				linea = uCarritoS.getLineaByProduct(carrito, producto);

				if (linea != null) {

					linea.setUnidades(linea.getUnidades()+1);
					linea.setTotal(uCarritoS.calcularTotal(linea));

				} else {

					linea = uCarritoS.crearLineaCarrito(producto);
					carrito.add(linea);
				}
			} else {
				model.addAttribute("mensaje", "Falta Stock para ese producto");
			}
		} 

		session.setAttribute("unitsCarrito", uCarritoS.getUnitsCarrito(carrito));	

		return "redirect:/payment";
	} 


	/**
	 * Eliminar unidad del carrito 
	 * @param session
	 * @param model
	 * @param id
	 * @return index
	 */ 
	@GetMapping("/delCarrito/{id}")
	public String eliminarProductCarrito(HttpSession session, Model model, @PathVariable int id) {

		UnidadesCarritoService uCarritoS = new UnidadesCarritoService();

		UnidadesCarrito linea = new UnidadesCarrito();

		ArrayList<UnidadesCarrito> carrito = (ArrayList<UnidadesCarrito>) session.getAttribute("carrito");

		Productos producto = productoS.getProductoById(id);

		if (producto != null) {

			linea = uCarritoS.getLineaByProduct(carrito, producto);

			if (linea != null) {

				linea.setUnidades(linea.getUnidades()-1);

				if (linea.getUnidades()==0) {

					carrito.remove(linea);

				} else {

					linea.setTotal(uCarritoS.calcularTotal(linea));
				}
			} 
		} 

		int uCart = uCarritoS.getUnitsCarrito(carrito);

		if (uCart == 0) {
			session.setAttribute("carrito", null);
		}

		session.setAttribute("unitsCarrito", uCarritoS.getUnitsCarrito(carrito));	

		return "redirect:/payment";
	} 

	/**
	 * Muestra detalles de Producto
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/detail/{id}")
	public String verDetalle(@PathVariable int id, Model model) {

		Productos producto = new Productos();

		producto = productoS.getProductoById(id);

		model.addAttribute("producto" , producto);

		List<Valoraciones> listVal = valServ.getValoracionesByIdProduct(id);


		int numVal = valServ.getNumValoraciones(listVal);

		if (numVal == 0) {
			model.addAttribute("numVal", numVal);
			return "product/detail";

		}
		int media = valServ.getMediaVal(listVal);
		model.addAttribute("numVal", numVal);
		model.addAttribute("media", media);
		model.addAttribute("listVal", listVal);

		return "product/detail";
	}

	/**
	 * Export excel Productos
	 * @return
	 */
	@GetMapping("/export")
	public String descargarExcel() {

		productoS.descargarExcelProductos();

		return "redirect:/product/list";
	}

	/**
	 * Import Excel Productos
	 * @return
	 */
	@GetMapping("/import")
	public String importarExcel() {

		try {
			productoS.importarExcelProductos();
		} catch (BiffException e) {
			logger.error(e+"Error importando excel de productos");
		} catch (IOException e) {
			logger.error(e+"Error importando excel de productos");
		}

		return "redirect:/product/list";
	}

}
