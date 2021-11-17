package curso.spring.model;

import java.util.Date;

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
public class Pedidos {

	@Id @GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Usuarios usuario;
	private Date fecha;
	@ManyToOne
	private MetodosPago metodoPago;
	private String estado;
	private String numFactura;
	private Double total;
	
	
}
