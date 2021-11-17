package curso.spring.model;
// default package
// Generated 2 Nov 2021, 09:58:38 by Hibernate Tools 4.3.5.Final


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuarios {


	@Id @GeneratedValue
	private Integer id;
	@ManyToOne
	private Roles rol;
	@Email
	private String email;
	@Size(min=2, max=16, message = "Tienes que introducir un email válido")
	private String clave;
	@NotNull
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String direccion;
	private String provincia;
	private String localidad;
	private String telefono;
	private String dni;
	private boolean baja;

	
	public Usuarios(int id2, int i, String email2, String clave2, String nombre2, String apellido12, String apellido22,
			String direccion2, String provincia2, String localidad2, String telefono2, String dni2) {
		// TODO Auto-generated constructor stub
	}


	public Usuarios(Integer id, Roles rol, @Email String email,
			@Size(min = 2, max = 16, message = "Tienes que introducir un email válido") String clave,
			@NotNull String nombre, String apellido1, String apellido2, String direccion, String provincia,
			String localidad, String telefono, String dni) {
		super();
		this.id = id;
		this.rol = rol;
		this.email = email;
		this.clave = clave;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.provincia = provincia;
		this.localidad = localidad;
		this.telefono = telefono;
		this.dni = dni;
	}

	
}
