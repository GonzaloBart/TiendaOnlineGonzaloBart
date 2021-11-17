package curso.spring.controller;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.spring.model.Roles;
import curso.spring.model.Usuarios;
import curso.spring.service.RolesService;
import curso.spring.service.UsuariosService;



@Controller
@RequestMapping("/users")
public class UsuariosController {
	
	Logger logger = LogManager.getLogger(UsuariosController.class);

	@Autowired
	UsuariosService us;
	
	@Autowired
	RolesService rs;

	@GetMapping("/list")
	public String listarUsuarios(Model model) {
		model.addAttribute("rol",new Roles());
		model.addAttribute("rolList", rs.getAllRoles());
		model.addAttribute("lista", us.getListaUsuarios());
		return "users/list";
	}
	/**
	 * Filtra usuarios por rol
	 * @param model
	 * @return
	 */
	@GetMapping("/filter/list")
	public String filtrarUsuarios( @RequestParam int id, Model model) {
		model.addAttribute("rol",new Roles());
		model.addAttribute("rolList", rs.getAllRoles());
		model.addAttribute("lista", us.getUsersByIdRol(id));
		return "users/list";
	}

	@GetMapping("/add")
	public String nuevoForm(Model model, HttpSession session) {
		model.addAttribute("usuario", new Usuarios());
		
		Usuarios u = (Usuarios) session.getAttribute("usuario");
		if (u == null) {
			return "/login";
		} else {
			return "users/new";
		}
		
	}

	@PostMapping("/add/submit")
	public String crear(@ModelAttribute Usuarios usuario, HttpSession session) {
		us.addUsuario(usuario);
		Usuarios u = (Usuarios) session.getAttribute("usuario");
		if (u.getRol().getId()==3) {
			return "redirect:/users/list";

		} else if (u.getRol().getId()==2) {
			return "redirect:/users//employee/list";

		} else {
			return "redirect:/users/profile/user";

		}
		
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable int id) {
		us.delUsuario(id);
		return "redirect:/users/list";
	}

	@GetMapping("/edit/{id}")
	public String actualizacionForm(@PathVariable int id, Model model) {
		Usuarios u = us.getUsuarioById(id);
		model.addAttribute("usuario", u);
		return "users/edit";
	}

	@PostMapping("/edit/submit")
	public String actualizar(@ModelAttribute Usuarios usuario) {
		us.editUsuario(usuario);
		return "redirect:/users/list";

	}

	/**
	 * Carga formulario para completar datos de usuario una vez dado de alta
	 * @param model
	 * @return
	 */
	@GetMapping("/form")
	public String darAlta(Model model) {

		Usuarios user = (Usuarios) model.getAttribute("usuario");

		model.addAttribute("usuario", user);
		return "users/form";

	}
	/**
	 * Guarda datos del perfil de usuario
	 * @param usuario
	 * @return
	 */
	@PostMapping("/form/submit")
	public String darAltaSubmit(@ModelAttribute Usuarios usuario, HttpSession session) {
		
		//model.addAttribute("usuario", u);
		us.editUsuario(usuario);

		session.setAttribute("usuario", usuario);
		return "redirect:/";

	}

	/**
	 * Carga perfil de usuario
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/profile/user")
	public String profileUser(Model model, HttpSession session) {

		Usuarios usuario = (Usuarios) session.getAttribute("usuario");

		if (usuario==null) {

			return "redirect:/login";
		} else {

			model.addAttribute("usuario", usuario);
			return "profile/user";
		}
	}

	/**
	 * Modifica datos del perfil de usuario
	 * @param usuario
	 * @return
	 */
	@PostMapping("/profile/user/submit")
	public String profileUserUpdate(@ModelAttribute Usuarios usuario, Model model) {

		us.editUsuario(usuario);
		Usuarios u = us.getUsuarioById(usuario.getId());

		model.addAttribute("usuario", u);

		return "profile/user";

	}

	
	/**
	 * Carga Perfil de Admin
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/profile/admin")
	public String profileAdmin(Model model, HttpSession session) {

		Usuarios usuario = (Usuarios) session.getAttribute("usuario");

		model.addAttribute("user", usuario);
		return "profile/admin";

	}

	@PostMapping("/profile/admin/submit")
	public String profileAdminUpdate(@ModelAttribute Usuarios usuario, Model model) {
		us.editUsuario(usuario);
		Usuarios u = us.getUsuarioById(usuario.getId());
		model.addAttribute("user", u);

		return "profile/admin";

	}
	
	/**
	 * Carga datos perfil de empleado
	 * @param model
	 * @param session
	 * @return /users/profile/employee
	 */
	@GetMapping("/profile/employee")
	public String profileEmp(Model model, HttpSession session) {

		Usuarios usuario = (Usuarios) session.getAttribute("usuario");

		model.addAttribute("user", usuario);
		return "profile/employee";

	}

	@PostMapping("/profile/employee/submit")
	public String profileEmpUpdate(@ModelAttribute Usuarios usuario, Model model, HttpSession session) {
		us.editUsuario(usuario);
		session.setAttribute("usuario", usuario);
		Usuarios u = us.getUsuarioById(usuario.getId());
		model.addAttribute("user", u);

		return "profile/employee";

	}
	
	@GetMapping("/employee/list")
	public String listarUsuariosByRol(Model model) {
		
		
		model.addAttribute("lista", us.getUsersByIdRol(1));
		return "users/list";
	}

	@GetMapping("/employee/delete/{id}")
	public String delByEmp(@PathVariable int id) {
		us.delUsuario(id);
		return "redirect:/users/employee/list";
	}

	@GetMapping("/employee/edit/{id}")
	public String actualizacionFormByEmp(@PathVariable int id, Model model) {
		Usuarios u = us.getUsuarioById(id);
		model.addAttribute("usuario", u);
		return "profile/employee/edit";
	}

	@PostMapping("/employee/edit/submit")
	public String actualizarByEmp(@ModelAttribute Usuarios usuario) {
		us.editUsuario(usuario);
		return "redirect:/users/employee/list";

	}
	
}
