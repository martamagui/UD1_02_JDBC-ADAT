package Controlador;

import Modelo.Modelo;
import Vista.Vista;

public class Launcher {
	private Modelo miModelo;
	private Vista miVista;
	
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
	public void setModelo (Modelo miModelo) {
		this.miModelo= miModelo;
	}
	

}
