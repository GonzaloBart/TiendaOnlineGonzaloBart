package curso.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Valoraciones {
	
	@Id @GeneratedValue
	private Integer id;
	@ManyToOne
	private Productos producto;	
	@ManyToOne
	private Usuarios usuario;
	private Integer valoracion;
	private String comentario;


}
