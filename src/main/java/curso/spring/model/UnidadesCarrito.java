package curso.spring.model;

import java.util.Date;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UnidadesCarrito {

	private Productos producto; 

	private Integer unidades;

	private Double total;

}
