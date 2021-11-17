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
import curso.spring.model.Pedidos;
import curso.spring.model.Productos;
import curso.spring.model.Usuarios;
import curso.spring.service.CategoriasService;
import curso.spring.service.PedidosService;
import curso.spring.service.pdf.GenerarPDF;


/**
 * Controlador CRUD pedidos y gestion de pedidos
 * @author Gonzalo
 *
 */
@Controller
@RequestMapping("/order")
public class PedidosController {
	
	@Autowired
	PedidosService pedS;
	
	
	
	/**
	 * Lista los pedidos de un usuario / Perfil usuario
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/list/user/{id}")
	public String listPedidosUser(Model model, @PathVariable int id) {
		
		model.addAttribute("list", pedS.getPedidosByUsuario(id));
		return "profile/user/list";	
		
	}
	
	
	@GetMapping("/list/user")
	public String listPedidoUser(Model model, HttpSession session) {
		
		Usuarios usuario = (Usuarios) session.getAttribute("usuario");
		model.addAttribute("list", pedS.getPedidosByUsuario(usuario.getId()));
		return "profile/user/list";	
		
	}
	
	/**
	 * Cancela Pedido desde usuario /Perfil Admin
	 * @param pedido
	 * @return
	 */
	@PostMapping("/cancel")
	public String cancelarPedido(@ModelAttribute Pedidos pedido){

		pedS.cancelarPedido(pedido.getId());

		return "redirect:/users/profile/user";

	}
	
	/**
	 * Solicita cancelacion Pedido desde usuario /Perfil Usuario
	 * @param pedido
	 * @return
	 */
	@PostMapping("/user/cancel")
	public String cancelarPedidoUser(@ModelAttribute Pedidos p){

		Pedidos pedido = pedS.getPedidoById(p.getId());
		pedS.procesarEstado(pedido, "pendiente de cancelacion");

		return "redirect:/users/profile/user";

	}
	
	/**
	 * Gestiona estado del pedido /Perfil Admin-Employee
	 * @param pedido
	 * @return "redirect:/order/list"
	 */
	@PostMapping("/status")
	public String gestionEstadoPedido(@ModelAttribute Pedidos pedido){

		//devuelve true or false si realiza la operacion
		pedS.procesarEstado(pedido, pedido.getEstado());
		return "redirect:/order/list";

	}
	
	// CRUD 
	@GetMapping("/list")
	public String listPedidos(Model model) {
		model.addAttribute("list", pedS.getAllPedidos());
		return "order/list";
	}
	
	@GetMapping("/add")
	public String addcat(Model model) {
		model.addAttribute("pedido", new Pedidos());
		return "order/new";
	}
	
	@PostMapping("/add/submit")
	public String createCat(@ModelAttribute Pedidos pedidos) {
		pedS.addPedido(pedidos);
		return "redirect:/order/list";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePedidos(@PathVariable int id) {
		pedS.deletePedido(id);
		return "redirect:/order/list";
	}
	
	@GetMapping("/edit/{id}")
	public String actualizacionForm(@PathVariable int id, Model model) {
		Pedidos c = pedS.getPedidoById(id);
		model.addAttribute("pedidos", c);
		return "order/edit";
	}
	
	@PostMapping("/edit/submit")
	public String actualizar(@ModelAttribute Pedidos pedidos) {
		pedS.editPedido(pedidos);
		return "redirect:/order/list";

	}


}
