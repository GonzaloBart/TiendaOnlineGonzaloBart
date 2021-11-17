package curso.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import curso.spring.model.Categorias;
import curso.spring.model.Productos;
import curso.spring.service.CategoriasService;
import curso.spring.service.ProductosService;

/**
 * Controlador de los filtros de busqueda
 * @author Gonzalo
 *
 */
@Controller
@RequestMapping("/search")
public class BusquedasController {

	@Autowired
	CategoriasService catS;

	@Autowired
	ProductosService productoS;

	/**
	 * Filtro busqueda por categoria
	 * @param model
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/categ")
	public String buscarByCategoria(Model model , @RequestParam int id, RedirectAttributes redirectAttributes) {

		List<Productos> productListCat = productoS.getProductsByCat(id);

		redirectAttributes.addFlashAttribute("productList", productListCat);

		return "redirect:/index";
	}

	/**
	 * Filtro busqueda por precio
	 * @param precio
	 * @param precio2
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/cat/precio")
	public String buscarByPrecio(@RequestParam double precio, @RequestParam double precio2, RedirectAttributes redirectAttributes){

		List<Productos> productListPrecio = productoS.getProductsByPrecio(precio, precio2);

		redirectAttributes.addFlashAttribute("productListPrecio", productListPrecio);


		return "redirect:/index";
	}

	/**
	 * Filtro Busqueda por nombre
	 * @param keyword
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/keyword")
	public String buscarByKeyword(@RequestParam String keyword, RedirectAttributes redirectAttributes) {

		List<Productos> productList = productoS.searchByKeyword(keyword);

		redirectAttributes.addFlashAttribute("productList", productList);

		return "redirect:/index";
	}
}
