package curso.spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.spring.model.MetodosPago;
import curso.spring.model.Pedidos;
import curso.spring.model.UnidadesCarrito;
import curso.spring.model.Usuarios;
import curso.spring.service.MetodosPagoService;
import curso.spring.service.PaymentService;
import curso.spring.service.UnidadesCarritoService;

/**
 * Controlador proceso de pago: carrito, selección metodo pago y ticket de compra
 * @author Gonzalo
 *
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	MetodosPagoService metodoS;

	@Autowired
	PaymentService payS;
	
	UnidadesCarritoService unitsC = new UnidadesCarritoService();

	/**
	 * Carga los datos del carrito para enviar a la vista
	 * @param session
	 * @param model
	 * @return /payment/cart Vista carrito
	 */
	@GetMapping("")
	public String irCarrito(HttpSession session, Model model) {

		ArrayList<UnidadesCarrito> carrito = (ArrayList<UnidadesCarrito>) session.getAttribute("carrito");
		
		if (carrito != null) {
			Double total = unitsC.getTotalCarrito(carrito);
			model.addAttribute("total",total);
		} else {
			model.addAttribute("total",0);
		}

		return "payment/cart";
	}


	/**
	 * Carga los metodos de pago y el total a pagar. En caso de no estar logeado envia login 
	 * @param model
	 * @param session
	 * @return Vista selección metodos de pago
	 */
	@GetMapping("/method")
	public String selectMetodo(Model model, HttpSession session) {
		if (session.getAttribute("usuario") == null) {
			return "redirect:/login";
		}
		Double total = unitsC.getTotalCarrito((ArrayList<UnidadesCarrito>) session.getAttribute("carrito"));
		model.addAttribute("total" , total);
		
		List<MetodosPago> listMetodos = metodoS.getAllMetodosPago();
		model.addAttribute("listMetodos", listMetodos);
		model.addAttribute("metodoPago", new MetodosPago());
		return "payment/method";
	}


	/**
	 * Realiza el pago y se crea el pedido en BD
	 * @param metodo
	 * @param sesion
	 * @param model
	 * @return 
	 */
	@PostMapping("/submit")
	public String realizaPago(@ModelAttribute MetodosPago metodo, HttpSession sesion, Model model) {

		Usuarios usuario = (Usuarios) sesion.getAttribute("usuario");
		
		ArrayList<UnidadesCarrito> carrito = (ArrayList<UnidadesCarrito>) sesion.getAttribute("carrito");

		Double total = unitsC.getTotalCarrito(carrito);

		if (usuario == null) {
			model.addAttribute("mensaje", "Para proceder a la compra necesita iniciar sesion");
			return "redirect:/login";
			
		} else {

			Pedidos pedido = payS.addPedido(usuario, metodo.getId(), total , "pendiente");
			
			payS.addDetallesPedido(carrito, pedido);

			model.addAttribute("pedido", pedido);
			
			return "payment/ticket";
		}
		
		
	}
	/**
	 * Envia los datos para crear factura
	 * @param metodo
	 * @param sesion
	 * @param model
	 * @param id
	 * @return Vista Factura y pago completado
	 */
	@GetMapping("/submit/{id}")
	public String createTicket(@ModelAttribute MetodosPago metodo, HttpSession sesion, Model model, @PathVariable int id) {

		Usuarios usuario = (Usuarios) sesion.getAttribute("usuario");
		
		ArrayList<UnidadesCarrito> carrito = (ArrayList<UnidadesCarrito>) sesion.getAttribute("carrito");

		Double total = unitsC.getTotalCarrito(carrito);

		if (usuario == null) {
			model.addAttribute("mensaje", "Para proceder a la compra necesita iniciar sesion");
			return "redirect:/login";
			
		} else {

			Pedidos pedido = payS.addPedido(usuario, metodo.getId(), total , "pendiente");
			
			payS.addDetallesPedido(carrito, pedido);

			model.addAttribute("pedido", pedido);
			
			return "payment/ticket";
		}
		
		
	}

	/**
	 * Limpia carrito y vuelve a index
	 * @param sesion
	 * @return
	 */
	@GetMapping("/end")
	public String salirFactura2(HttpSession sesion) {


		sesion.setAttribute("carrito", null);

		return "redirect:/";
	}


}
