package curso.spring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Configuracion {


	@Id @GeneratedValue
	private Integer id;
	private String clave;
	private String valor;
	private String tipo;

	public Configuracion(String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
	}
}
