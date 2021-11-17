package curso.spring.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import curso.spring.model.Categorias;
import curso.spring.model.Productos;
import curso.spring.model.Roles;
import curso.spring.model.UnidadesCarrito;
import curso.spring.model.Usuarios;
import curso.spring.service.CategoriasService;
import curso.spring.service.ProductosService;
import curso.spring.service.UsuariosService;
import curso.spring.service.utils.Util;
import curso.spring.service.email.SendMail;

/**
 * Main Controller para index, login y signup 
 * @author Gonzalo
 *
 */
@Controller
@RequestMapping("")
public class MainController {
	

	@Autowired
	ProductosService productosService;

	@Autowired
	CategoriasService catServ;

	@Autowired
	UsuariosService uService;

	Logger logger = LogManager.getLogger(MainController.class);

	/**
	 * Filtro para cargar atributos de session: Multilenguaje y Carrito
	 * @param model
	 * @param sesion
	 * @return redireccion a /index pagina principal
	 */
	@GetMapping("")
	public String inicial(Model model, HttpSession sesion) {

		
		Util util = new Util();

		ResourceBundle rb = util.cargarIdioma("es");
		
		sesion.setAttribute("diccionario", rb);

		logger.info("Nuevo carrito");

		if (sesion.getAttribute("carrito") == null) {
			ArrayList<UnidadesCarrito> carrito = new ArrayList<UnidadesCarrito>();
			sesion.setAttribute("carrito", carrito);
			sesion.setAttribute("unitsCarrito", null);	
			
			
		}


		return "redirect:/index";
	} 

	/**
	 * Carga todos los productos en la pagina principal
	 * @param model
	 * @param sesion
	 * @return /index pagina principal
	 */
	@GetMapping("/index")
	public String index (Model model, HttpSession sesion) {
		
		List<Categorias> catList = catServ.getAllCategorias();

		model.addAttribute("catList", catList);

		model.addAttribute("categoria", new Categorias());

		model.addAttribute("producto", new Productos());

		List<Productos> productList;

		if (model.getAttribute("productList") != null) {

			productList = (List<Productos>) model.getAttribute("productList");

		} else {

			if (model.getAttribute("productListPrecio") != null) {
				productList = (List<Productos>) model.getAttribute("productListPrecio");
			} else {
				productList = productosService.getAllProducts();
			}
		}


		model.addAttribute("productList", productList);

		

		return "index";
	}
	
	/**
	 * Cambia el idioma y regresa a la pagina principal
	 * @param model
	 * @param idioma
	 * @param session
	 * @return /index pagina principal
	 */
	@GetMapping("/idioma")
	public String index (Model model, @RequestParam String idioma, HttpSession session) {
		
		Util util = new Util();

		ResourceBundle rb = util.cargarIdioma(idioma);
		
		session.setAttribute("diccionario", rb);
		
		return "redirect:/index";
	}
	
	
	/**
	 * Carga el modelo de usuario para realizar el login y muestra pagina de login
	 * @param model
	 * @return /access/login - pagina de login
	 */
	@GetMapping("/login")
	public String loginForm(Model model) {

		model.addAttribute("usuario", new Usuarios());
		return "access/login";
	}

	/**
	 * Comprueba los datos de usuario para realizar el login
	 * @param sesion
	 * @param model
	 * @param usuario
	 * @return /index pagina principal o pagina de login /access/login en caso de errores 
	 */
	@PostMapping("/login/submit")
	public String logar(HttpSession sesion , Model model, @ModelAttribute Usuarios usuario) {

		
		
		if(uService.checkUserLogin(usuario)) { 
			Usuarios user = uService.getUserByEmail(usuario.getEmail());
			sesion.setAttribute("usuario", user);
			return "redirect:/";
		}
		else {
			model.addAttribute("mensaje", "El usuario y/o la contraseña no son correctos.");
			model.addAttribute("usuario", new Usuarios());
			
			return "access/login";

		}
	}

	/**
	 * El usuario sale de sesion y limpia los atributos de sesion 
	 * @param sesion
	 * @return pagina principal
	 */
	@GetMapping("/logout")
	public String logout(HttpSession sesion) {

		sesion.setAttribute("usuario", null);
		sesion.setAttribute("unitsCarrito", null);
		sesion.setAttribute("carrito", null);

		return "redirect:/";
	}

	/**
	 * Carga modelo usuario para crear cuenta de usuario
	 * @param model
	 * @return /access/signup 
	 */
	@GetMapping("/signup")
	public String signUpForm(Model model) {
		model.addAttribute("usuario", new Usuarios());
		
		
		return "access/signup";
	}
	/**
	 * Crea cuenta con nombre, email y clave, y rol cliente, y redirige a completar los datos de usuario y envia email 
	 * al usuario 
	 * @param redirectAttributes
	 * @param sesion
	 * @param model
	 * @param usuario
	 * @param bindingResult
	 * @return redirect:/users/form 
	 */
	@PostMapping("/signup/submit")
	public String signUp( @Valid @ModelAttribute Usuarios usuario, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession sesion , Model model) {

		if(bindingResult.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "access/signup";
		} else {

			boolean user = uService.checkUserExist(usuario.getEmail());

			if(user) { 
				model.addAttribute("mensaje", "El usuario ya tiene una cuenta. Por favor entre en su cuenta");
				return "access/login";
			}
			else {
				usuario.setClave(uService.encriptarPass(usuario.getClave()));
				Roles rol = new Roles();
				rol.setId(1);
				usuario.setRol(rol);
				uService.addUsuario(usuario);
				redirectAttributes.addFlashAttribute("usuario", usuario);
				
				SendMail sendMail = new SendMail();
				sendMail.sendEMail(null);
				return "redirect:/users/form";
				
			}
		}
	}
	
	/**
	 * Carga vista de cambio de contraseña
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/pass")
	public String cambiarPass(Model model, HttpSession session) {
		model.addAttribute("usuario", session.getAttribute("usuario"));

		return "access/pass";
	}
	
	/**
	 * Realiza cambio de contraseña con doble validación 
	 * @param clave
	 * @param clave2
	 * @param session
	 * @param model
	 * @return "redirect:/users/profile/user" si ontraseñas coinciden
	 * @return "access/pass" vuelta a la vista si ontraseñas no coinciden
	 * 
	 */
	@PostMapping("/pass/submit")
	public String submitPass(@RequestParam String clave, @RequestParam String clave2, HttpSession session, Model model) {
		
		if (clave.equals(clave2)) {
			Usuarios u = (Usuarios) session.getAttribute("usuario");
			u.setClave(uService.encriptarPass(clave2));
			uService.editUsuario(u);
			return "redirect:/users/profile/user";			


		} else {
			
			model.addAttribute("mensaje", "Las contraseñas no son iguales");
			model.addAttribute("usuario", session.getAttribute("usuario"));
		return "access/pass";
		
		}
	
	}
	
	
}
