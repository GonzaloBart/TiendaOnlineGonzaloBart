package curso.spring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Categorias {


	@Id @GeneratedValue
	private Integer id;
	private String nombre;
	private String descripcion;

	
	public Categorias(String string, String string2) {
		// TODO Auto-generated constructor stub
	}

}