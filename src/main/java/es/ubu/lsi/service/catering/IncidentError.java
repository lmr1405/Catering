package es.ubu.lsi.service.catering;

/**
 * Error code.
 * 
 * Listado de posibles errores que se pueden producir.
 * 
 * @author <a href="mailto:pgdiaz@ubu.es">Pablo García</a> 
 * @since 1.0
 *
 */
public enum IncidentError {
	
	// If we have more error messages, add new values to the the end of enum type...
	NOT_EXISTS_MENU("No existe menú"),
	NOT_EXISTS_CLIENT("No existe cliente"),
	ERROR_IN_DATE("Fecha y/o hora incorrecta"),
	NEGATIVE_OR_ZERO_PEOPLE("Número de personas cero o negativas"),
	NEGATIVE_OR_ZERO_IMPORT("Importe de la compra cero o negativa"),
	EXISTS_PURCHASE("Compra ya existente"),
	DATE_NULL("fecha pasada por parámetro nula");
	
	/** Text. */
	private String text;
	
	/** Constructor. */
	private IncidentError(String text) {
		this.text = text;
	}

	/**
	 * Gets text.
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}
}
