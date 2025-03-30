package defaultPackage;

import ventana.VentanaVista;

public class ClasePrincipal {

	public static void main(String[] args) {
		GestorImagenes.cargarImagenes();
		new VentanaVista();
	}
}
