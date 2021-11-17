package curso.spring.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.spring.model.Configuracion;
import curso.spring.model.MetodosPago;
import curso.spring.repository.ConfiguracionRepository;
import curso.spring.repository.MetodosPagoRepository;

@Service
public class MetodosPagoService {
	
	
	@Autowired
	MetodosPagoRepository metodosRepo; 
	public void editMetodosPago(MetodosPago metodos) {

		metodosRepo.save(metodos);
	}

	public void deleteMetodosPago(Integer id) {

		MetodosPago c = metodosRepo.getById(id);
		metodosRepo.delete(c);
	}

	public void addMetodosPago(MetodosPago metodos) {

		metodosRepo.save(metodos);
	}

	public List<MetodosPago> getAllMetodosPago(){

		List<MetodosPago> listMetodos = metodosRepo.findAll();

		return listMetodos;
	}

	public MetodosPago getMetodosPagoById(Integer id) {

		return metodosRepo.getById(id);

	}

}
