package curso.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.spring.model.Configuracion;
import curso.spring.model.Impuestos;
import curso.spring.model.Productos;
import curso.spring.model.Roles;
import curso.spring.repository.ImpuestosRepository;
import curso.spring.repository.RolesRepository;

@Service
public class ImpuestosService {
	
	@Autowired
	ImpuestosRepository impuestoRepo;
		
	
	public void addImpuesto(Impuestos impuesto) {

		impuestoRepo.save(impuesto);
	}
	
	public Impuestos getImpuestoById(Integer id) {

		return impuestoRepo.getById(id);

	}

}
