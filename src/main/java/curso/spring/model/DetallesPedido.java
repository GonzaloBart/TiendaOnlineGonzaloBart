package curso.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DetallesPedido {
	
	@Id @GeneratedValue
	private Integer id;
	@ManyToOne
	private Pedidos pedido; 
	@ManyToOne
	private Productos producto;
	private Double precioUnidad;
	private Integer unidades;
	private Float impuesto;
	private Double total;

	
}
