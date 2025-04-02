package defaultPackage;

import estructuras.ColaDoblementeEnlazada;
import ventana.VentanaControlador;
import ventana.VentanaModelo;
import ventana.VentanaVista;

public class ClasePrincipal {

	public static void main(String[] args) {
		GestorImagenes.cargarImagenes();
				
		
		new VentanaControlador(new VentanaModelo(), new VentanaVista());
	}
}
