package curso.spring.service.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Util {

	public ResourceBundle cargarIdioma(String code) {

		Locale locale = new Locale(code);
		ResourceBundle rb = ResourceBundle.getBundle("idioma", locale);

		return rb;
	}
}
