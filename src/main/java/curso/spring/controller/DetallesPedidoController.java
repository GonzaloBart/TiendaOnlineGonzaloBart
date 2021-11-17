package curso.spring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.spring.model.Categorias;
import curso.spring.model.DetallesPedido;
import curso.spring.model.Pedidos;
import curso.spring.model.Productos;
import curso.spring.model.Usuarios;
import curso.spring.service.CategoriasService;
import curso.spring.service.DetallesPedidoService;
import curso.spring.service.PedidosService;
import curso.spring.service.pdf.GenerarPDF;

/**
 * Controladores gestion detalles de pedido
 * @author Gonzalo
 *
 */
@Controller
@RequestMapping("/details")
public class DetallesPedidoController {
	
	@Autowired
	DetallesPedidoService detalleS;
	
	@Autowired
	PedidosService pedidoS;
	

	/**
	 * Lista todos los detalles - probablement sin uso
	 * @param model
	 * @return 
	 */
	@GetMapping("/list")
	public String listDetalles(Model model) {
		model.addAttribute("list", detalleS.getAllDetallesPedido());
		return "details/list";
	}
	
	
	/**
	 * Lista los detalles de un pedido
	 * @param model
	 * @param id de Detalle de Pedido
	 * @return profile/user/details
	 */
	@GetMapping("/list/order/{id}")
	public String listDetallesPedido(Model model, @PathVariable int id) {
		
		Pedidos pedido =  pedidoS.getPedidoById(id);
		model.addAttribute("list", detalleS.getDetallesByPedido(pedido.getId()));
		model.addAttribute("estado", pedido.getEstado());
		model.addAttribute("detalle", new DetallesPedido());
		
		return "profile/user/details";

	}
	
	
	
	/**
	 * Cancelar detalles de pedido / pedido parcial
	 * @param detalles
	 * @return "profile/admin" if admin
	 * @return "profile/employee" if empleado
	 * @return "redirect:/order/list/user" if usuario
	 * @return "/" inicio si no loggeado
	 */
	@PostMapping("/cancel")
	public String cancelarDetallePedido(@ModelAttribute DetallesPedido detalles, HttpSession session) {
		
		detalleS.cancelarDetalle(detalles.getId());
		
		//TODO: actualiza pedido
		
		Usuarios u = (Usuarios) session.getAttribute("usuario");
		if (u.getRol().getId() == 3) {
			return "profile/admin";

		} else if (u.getRol().getId() == 2) {
			return "profile/employee";

		} else if (u.getRol().getId() == 1) {
			
			return "redirect:/order/list/user";
			//return "profile/user/details";

		} else {
			return "redirect:/";
		}
		
	}
	
	
	//CRUD Basico
	
	@GetMapping("/add")
	public String addcat(Model model) {
		model.addAttribute("detalles", new DetallesPedido());
		return "details/new";
	}
	
	@PostMapping("/add/submit")
	public String createCat(@ModelAttribute DetallesPedido detalles) {
		detalleS.addDetallePedido(detalles);
		return "redirect:/details/list";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePedidos(@PathVariable int id) {
		detalleS.deleteDetallePedido(id);
		return "redirect:/details/list";
	}

	@GetMapping("/edit/{id}")
	public String actualizacionForm(@PathVariable int id, Model model) {
		DetallesPedido c = detalleS.getDetallePedidoById(id);
		model.addAttribute("detalles", c);
		return "details/edit";
	}
	
	@PostMapping("/edit/submit")
	public String actualizar(@ModelAttribute DetallesPedido detalles) {
		detalleS.editDetallePedido(detalles);
		return "redirect:/details/list";

	}
	
	/**
	 * Usuario descarga pdf y crea factura en pdf
	 * @param id
	 * @return "redirect:/users/profile/user"
	 */
	@GetMapping("/pdf/{id}")
	public String createPDF(@PathVariable int id){
		
		
		List<DetallesPedido> list = detalleS.getDetallesByPedido(id);
		
		Pedidos pedido = pedidoS.getPedidoById(id);
		
		GenerarPDF pdf = new GenerarPDF();
		
		try {
			pdf.getPDF(pedido, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return "redirect:/users/profile/user";
	
	}
}
