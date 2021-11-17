package curso.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.spring.model.Categorias;
import curso.spring.model.Configuracion;
import curso.spring.service.CategoriasService;
import curso.spring.service.ConfiguracionService;

/**
 * CRUD entidad Configuraciones
 * @author Gonzalo
 *
 */
@Controller
@RequestMapping("/config")
public class ConfiguracionController {
	
	@Autowired
	ConfiguracionService configS;
	
	@GetMapping("/list")
	public String listConfig(Model model) {
		model.addAttribute("list", configS.getAllConfiguracion());
		return "config/list";
	}
	
	@GetMapping("/add")
	public String addConfig(Model model) {
		model.addAttribute("configuracion", new Configuracion());
		return "config/new";
	}
	
	@PostMapping("/add/submit")
	public String createConfig(@ModelAttribute Configuracion configuracion) {
		configS.addConfiguracion(configuracion);
		return "redirect:/config/list";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteConfig(@PathVariable int id) {
		configS.deleteConfiguracion(id);
		return "redirect:/config/list";
	}
	
	@GetMapping("/edit/{id}")
	public String actualizacionForm(@PathVariable int id, Model model) {
		Configuracion c = configS.getConfiguracionById(id);
		model.addAttribute("configuracion", c);
		return "config/edit";
	}
	
	@PostMapping("/edit/submit")
	public String actualizar(@ModelAttribute Configuracion configuracion) {
		configS.editConfiguracion(configuracion);
		return "redirect:/config/list";

	}

}
