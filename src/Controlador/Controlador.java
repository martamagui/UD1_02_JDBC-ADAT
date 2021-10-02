package Controlador;

import Modelo.Modelo;
import Vista.Vista;

public class Controlador {
	private Modelo miModelo;
	private Vista miVista;

	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

	public void setVista(Vista miVista2) {
		this.miVista = miVista2;
	}

	/* Llamadas al modelo */
	public void annadirRegistro() {
		miModelo.annadirRegistro(miVista.getTitulo(), miVista.getAutor(), miVista.getCategoria(), miVista.getPrecio());
	}

	public void mostrarSeleccion() {
		miModelo.mostrarSeleccion(miVista.getSeleccion());
	}

	public void modificarRegistro() {
		String[] datos = { miVista.getTituloSeleccionado(), miVista.getTitulo(), miVista.getAutor(),
				miVista.getCategoria(), miVista.getPrecio() };
		miModelo.modificarRegistro(datos);
	}

	public void borrarRegistro() {
		miModelo.borrarRegistro(miVista.getTituloSeleccionado());
	}
}
