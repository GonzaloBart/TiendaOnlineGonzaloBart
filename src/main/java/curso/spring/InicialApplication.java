package curso.spring;

import java.net.URL;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import curso.spring.model.Categorias;
import curso.spring.model.Impuestos;
import curso.spring.model.MetodosPago;
import curso.spring.model.Productos;
import curso.spring.model.Roles;
import curso.spring.model.Usuarios;
import curso.spring.repository.UsuariosRepository;
import curso.spring.service.CategoriasService;
import curso.spring.service.ImpuestosService;
import curso.spring.service.MetodosPagoService;
import curso.spring.service.ProductosService;
import curso.spring.service.RolesService;
import curso.spring.service.UsuariosService;

@SpringBootApplication
@EnableScheduling
public class InicialApplication {

	@Autowired
	UsuariosService userS;
	
	@Autowired
	RolesService rolesS;
	
	@Autowired
	ProductosService productoS;
	
	@Autowired
	CategoriasService categoriaS;
	
	@Autowired
	ImpuestosService impuestoS;
	
	@Autowired
	MetodosPagoService metodoS;
	
	
	public static void main(String[] args) {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("log4j.properties");
		PropertyConfigurator.configure(url);
		
		SpringApplication.run(InicialApplication.class, args);
		
	
	}
	
	/*
	@PostConstruct
	public void cargarDatos(){
		
		Roles roles = new Roles(0, "Cliente");
		Roles roles1 = new Roles(0, "Empleado");
		Roles roles2 = new Roles(0, "Admin");
		
		rolesS.addRol(roles);
		rolesS.addRol(roles1);		
		rolesS.addRol(roles2);
		
		Usuarios usuario1 = new Usuarios(0,rolesS.getRolById(1) , "cliente@tienda.com", "MTIzNA==", "Tere", "Sanchez", "Ramiro", "Calle la Resurreccion", "Palencia", "Palencia", "654983738", "119825341Y");
		Usuarios usuario2 = new Usuarios(0,rolesS.getRolById(1) , "cliente1@tienda.com", "MTIzNA==", "Mari", "Tenorio", "Perez", "Calle Pasion", "Valladolid", "Valladolid", "654233738", "459825341Y");
		Usuarios usuario3 = new Usuarios(0,rolesS.getRolById(2) , "admin@tienda.com", "MTIzNA==", "Eulogio", "Martinez", "Caro", "Avd Cardenal", "Leon", "Leon", "42142421", "71927971I");
		Usuarios usuario4 = new Usuarios(0,rolesS.getRolById(3) , "empleado@tienda.com", "MTIzNA==", "Luca", "Martuero", "Lea", "Calle Convento", "Salamanca", "Leon", "97392744", "23287364T");

		userS.addUsuario(usuario1);
		userS.addUsuario(usuario2);
		userS.addUsuario(usuario3);
		userS.addUsuario(usuario4);
		
		Categorias categoria1 = new Categorias(0,"Denominacion de Origen Toro", "Toro");
		Categorias categoria2 = new Categorias(0,"Denominacion de Origen Rioja", "Rioja");
		Categorias categoria3 = new Categorias(0,"Ribera del Duero", "Ribera del Duero");
		
		
		categoriaS.addCategoria(categoria1);
		categoriaS.addCategoria(categoria2);
		categoriaS.addCategoria(categoria3);
		
		Impuestos impuesto = new Impuestos(0, (float) 21);
		impuestoS.addImpuesto(impuesto);
		
		Productos producto1 = new Productos(0, categoriaS.getCategoriaById(1), "Matsu El Pícaro 2020", "Toro", 7.0, 54, new Date(),null, impuestoS.getImpuestoById(1) , "/img/productos/25.png");
		Productos producto2 = new Productos(0, categoriaS.getCategoriaById(1), "Madre Mia 2020", "Toro", 8.5, 54, new Date(),null,impuestoS.getImpuestoById(1) , "/img/productos/28.png");
		Productos producto3 = new Productos(0, categoriaS.getCategoriaById(3), "Valduero Crianza 2016", "Ribera del Duero", 37.0, 54,new Date(),null, impuestoS.getImpuestoById(1) , "/img/productos/1.png");
		Productos producto4 = new Productos(0, categoriaS.getCategoriaById(3), "Matarromera Crianza 2016", "Ribera del Duero", 41.0, 54,new Date(),null, impuestoS.getImpuestoById(1), "/img/productos/3.png");
		//Productos producto5 = new Productos(25, 1, "Matsu El Pícaro 2020", "Toro", 7.0, 54, (float) 21.0 , "src/main/resources/static/img/productos/25png");
		//Productos producto6 = new Productos(25, 1, "Matsu El Pícaro 2020", "Toro", 7.0, 54, (float) 21.0 , "src/main/resources/static/img/productos/25png");

		productoS.addProduct(producto1);
		productoS.addProduct(producto2);
		productoS.addProduct(producto3);
		productoS.addProduct(producto4);
		
		
		MetodosPago metodo = new MetodosPago(0, "Debito Mastercard");
		MetodosPago metodo1 = new MetodosPago(0, "Debito Visa");
		MetodosPago metodo2 = new MetodosPago(0, "Paypal");
		
		
		metodoS.addMetodosPago(metodo);
		metodoS.addMetodosPago(metodo1);
		metodoS.addMetodosPago(metodo2);
		
	}

*/
}
