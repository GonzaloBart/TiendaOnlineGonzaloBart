package curso.spring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Productos {
	
	@Id @GeneratedValue
	private Integer id;
	@ManyToOne
	private Categorias categoria;
	private String nombre;
	private String descripcion;
	private Double precio;
	private Integer stock;
	private Date fechaAlta;
	private Date fechaBaja;
	@ManyToOne
	private Impuestos impuesto;
	private String imagen;



	

	public Productos(Date fechaAlta, Date fechaBaja) {
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
	}

	
}
