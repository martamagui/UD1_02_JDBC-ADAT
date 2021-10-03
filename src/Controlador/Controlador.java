/**
 * @author Marta Molina Aguilera
 */
package Controlador;

import Modelo.Modelo;
import Vista.Vista;

/**
 * Clase la cual emplea distintos métodos para comunicar a la vista y el modelo.
 *
 */
public class Controlador {
	private Modelo miModelo;
	private Vista miVista;

	/**
	 * Setter del Modelo para crear comunicación con él.
	 * 
	 * @param miModelo: Objeto de la clase Modelo.
	 */
	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

	/**
	 * Setter de la Vista para crear comunicación con él.
	 * 
	 * @param miVista: Objeto de la clase Vista.
	 */
	public void setVista(Vista miVista) {
		this.miVista = miVista;
	}

	// -------- Llamadas al modelo --------

	/**
	 * Método el cual llama a la función del modelo "annadirRegistro" pasa los datos
	 * de los getters de los campos de texto a este a través de los parámetros.
	 */
	public void annadirRegistro() {
		miModelo.annadirRegistro(miVista.getTitulo(), miVista.getAutor(), miVista.getCategoria(), miVista.getPrecio());
	}

	/**
	 * Método el cual llama a la función del modelo del mismo nombre y le pasa cómo
	 * parámetro el título de la fila seleccionada de la vista.
	 */
	public void mostrarSeleccion() {
		miModelo.mostrarSeleccion(miVista.getSeleccionTitulo());
	}

	/**
	 * Método el cual llama a la función del modelo del mismo nombre, la cual pasa
	 * en un array de Strings el título seleccionado y los datos escritos en los
	 * campos de texto.
	 */
	public void modificarRegistro() {
		String[] datos = { miVista.getTituloSeleccionado(), miVista.getTitulo(), miVista.getAutor(),
				miVista.getCategoria(), miVista.getPrecio() };
		miModelo.modificarRegistro(datos);
	}

	/**
	 * Método el cual llama a la función con el mismo nombre del controlador, le
	 * pasa cómo parámetro el título del elemento seleccionado en la tabla.
	 */
	public void borrarRegistro() {
		miModelo.borrarRegistro(miVista.getTituloSeleccionado());
	}
}
