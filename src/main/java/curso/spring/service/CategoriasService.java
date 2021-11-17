package curso.spring.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.spring.model.Categorias;
import curso.spring.model.Productos;
import curso.spring.repository.CategoriasRepository;

@Service
public class CategoriasService {

	@Autowired
	CategoriasRepository catRepo; 

	public void editCategoria(Categorias categoria) {

		catRepo.save(categoria);
	}

	public void deleteCategoria(Integer id) {

		Categorias c = catRepo.getById(id);
		catRepo.delete(c);
	}

	public void addCategoria(Categorias categoria) {

		catRepo.save(categoria);
	}

	public List<Categorias> getAllCategorias(){

		List<Categorias> listProducts = catRepo.findAll();

		return listProducts;
	}

	public Categorias getCategoriaById(Integer id) {

		return catRepo.getById(id);

	}
	
}
