package ventana;

import defaultPackage.Procesamiento;
import defaultPackage.Procesamiento.TipoBloque;

public class VentanaModelo {

	
	public void resolverNivel(TipoBloque[][] cuadricula, int posX, int posY) {

		if (cuadricula == null)
			return;

		System.out.println("Pos X: " + posX + " | Pos Y: " + posY);

		// Procesamiento.imprimirGrilla(cuadricula);

		if (!(posX < 0 && posY < 0))
			Procesamiento.imprimirLista(Procesamiento.resolver(cuadricula, posX, posY));
	}
}
