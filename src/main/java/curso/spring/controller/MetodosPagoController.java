package curso.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.spring.model.MetodosPago;
import curso.spring.service.MetodosPagoService;

/**
 * CRUD para metodos de pago
 * @author Gonzalo
 *
 */
@Controller
@RequestMapping("/method")
public class MetodosPagoController {
	
	
	@Autowired
	MetodosPagoService metodoS;
	
	@GetMapping("/list")
	public String listMetodo(Model model) {
		model.addAttribute("list", metodoS.getAllMetodosPago());
		return "method/list";
	}
	
	@GetMapping("/add")
	public String addMetodo(Model model) {
		model.addAttribute("metodo", new MetodosPago());
		return "method/new";
	}
	
	@PostMapping("/add/submit")
	public String createMetodo(@ModelAttribute MetodosPago metodo) {
		metodoS.addMetodosPago(metodo);
		return "redirect:/method/list";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMetodo(@PathVariable int id) {
		metodoS.deleteMetodosPago(id);
		return "redirect:/method/list";
	}
	
	@GetMapping("/edit/{id}")
	public String actualizacionFormMetodo(@PathVariable int id, Model model) {
		MetodosPago c = metodoS.getMetodosPagoById(id);
		model.addAttribute("metodo", c);
		return "method/edit";
	}
	
	@PostMapping("/edit/submit")
	public String actualizarMetodo(@ModelAttribute MetodosPago metodo) {
		metodoS.editMetodosPago(metodo);
		return "redirect:/method/list";

	}
			
	
}
