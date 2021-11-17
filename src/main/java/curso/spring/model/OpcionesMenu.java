package curso.spring.model;

// default package
// Generated 2 Nov 2021, 09:58:38 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OpcionesMenu {

	@Id @GeneratedValue
	private Integer id;
	@ManyToOne
	private Roles roles;
	private String nombreOpcion;
	private String urlOpcion;


}
