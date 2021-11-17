package curso.spring.model;

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
public class MetodosPago {
	

	@Id @GeneratedValue
	private Integer id;
	private String metodoPago;
	
	public MetodosPago(String string) {
		// TODO Auto-generated constructor stub
	}

}
