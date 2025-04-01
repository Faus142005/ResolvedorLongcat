package ventana;

import java.util.List;

import defaultPackage.Procesamiento;
import defaultPackage.Procesamiento.TipoBloque;

public class VentanaModelo {

	
	public List<String> resolverNivel(TipoBloque[][] cuadricula, int posX, int posY) {

		if (cuadricula == null || posX < 0 || posY < 0)
			return null;

		//System.out.println("Pos X: " + posX + " | Pos Y: " + posY);
		
		return Procesamiento.resolver(cuadricula, posX, posY);
	}
}
