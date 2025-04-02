package ventana;

import defaultPackage.Procesamiento;
import estructuras.ColaDoblementeEnlazada;
import estructuras.Enumerativos.Movimiento;
import estructuras.Enumerativos.TipoBloque;
import estructuras.InformacionMovimiento;

public class VentanaModelo {

	
	public ColaDoblementeEnlazada<InformacionMovimiento> resolverNivel(TipoBloque[][] cuadricula, int posX, int posY) {

		if (cuadricula == null || posX < 0 || posY < 0)
			return null;

		//System.out.println("Pos X: " + posX + " | Pos Y: " + posY);
		
		ColaDoblementeEnlazada<InformacionMovimiento> Pasos =  Procesamiento.resolver(cuadricula, posX, posY);
		
		if(Pasos != null)
			if(Pasos.tieneCabezera()){
				
				InformacionMovimiento infMov = Pasos.obtener();
				TipoBloque esq;
				if(infMov.getMovimiento() == Movimiento.ABAJO || infMov.getMovimiento() == Movimiento.ARRIBA)
					esq = TipoBloque.GATOVERTICAL;
				else if(infMov.getMovimiento() == Movimiento.IZQUIERDA || infMov.getMovimiento() == Movimiento.DERECHA)
					esq = TipoBloque.GATOHORIZONTAL;
				
				else esq = null;
				
				infMov.setEsquina(esq);
			}
		
		return Pasos;
	}
}
