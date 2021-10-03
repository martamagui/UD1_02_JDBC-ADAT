/**
 * @author Marta Molina Aguilera
 */
package Controlador;

import Modelo.Modelo;
import Vista.Vista;

public class Launcher {
	@SuppressWarnings("unused")
	private Modelo miModelo;
	@SuppressWarnings("unused")
	private Vista miVista;

	/*
	 * Main de la clase la cual crea un objeto de Vista, Modelo y Controlador. Hace
	 * visible crea y hace visible la ventana.
	 */
	public static void main(String[] args) {
		Vista miVista = new Vista();
		Modelo miModelo = new Modelo();
		Controlador miControlador = new Controlador();

		miControlador.setModelo(miModelo);
		miControlador.setVista(miVista);

		miModelo.setVista(miVista);

		miVista.setModelo(miModelo);
		miVista.setControlador(miControlador);

		miVista.setVisible(true);

	}

	/**
	 * Setter del modelo, el cual contiene logica del código. Consiguiendo así la
	 * comunicación entre clases.
	 * 
	 * @param miModelo
	 */
	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

}
