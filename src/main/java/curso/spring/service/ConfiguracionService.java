package curso.spring.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.spring.model.Configuracion;
import curso.spring.repository.ConfiguracionRepository;


@Service
public class ConfiguracionService {

	@Autowired
	ConfiguracionRepository configRepo; 

	public void editConfiguracion(Configuracion configuracion) {

		configRepo.save(configuracion);
	}

	public void deleteConfiguracion(Integer id) {

		Configuracion c = configRepo.getById(id);
		configRepo.delete(c);
	}

	public void addConfiguracion(Configuracion configuracion) {

		configRepo.save(configuracion);
	}

	public List<Configuracion> getAllConfiguracion(){

		List<Configuracion> listConfig = configRepo.findAll();

		return listConfig;
	}

	public Configuracion getConfiguracionById(Integer id) {

		return configRepo.getById(id);

	}
	
	public List<Configuracion>  getConfigByClave(String clave){
		
		
		return configRepo.getConfigByClave(clave);
	}
	
}
