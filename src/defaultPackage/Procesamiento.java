package defaultPackage;

import java.util.List;

import estructuras.ColaDoblementeEnlazada;
import estructuras.Enumerativos.Movimiento;
import estructuras.Enumerativos.TipoBloque;
import estructuras.InformacionMovimiento;

public class Procesamiento {

	public static TipoBloque obtenerEsquina(Movimiento movimientoAnterior, Movimiento movimientoActual) {

		switch (movimientoAnterior) {
		case IZQUIERDA:
			if (movimientoActual == Movimiento.ARRIBA)
				return TipoBloque.GATOESQUINAABAJODERECHA;
			else if (movimientoActual == Movimiento.ABAJO)
				return TipoBloque.GATOESQUINAARRIBADERECHA;

			return TipoBloque.GATOHORIZONTAL;
		case DERECHA:
			if (movimientoActual == Movimiento.ARRIBA)
				return TipoBloque.GATOESQUINAABAJOIZQUIERDA;
			else if (movimientoActual == Movimiento.ABAJO)
				return TipoBloque.GATOESQUINAARRIBAIZQUIERDA;
			return TipoBloque.GATOHORIZONTAL;
		case ARRIBA:
			if (movimientoActual == Movimiento.IZQUIERDA)
				return TipoBloque.GATOESQUINAARRIBAIZQUIERDA;
			else if (movimientoActual == Movimiento.DERECHA)
				return TipoBloque.GATOESQUINAARRIBADERECHA;
			return TipoBloque.GATOVERTICAL;
		case ABAJO:
			if (movimientoActual == Movimiento.IZQUIERDA)
				return TipoBloque.GATOESQUINAABAJOIZQUIERDA;
			else if (movimientoActual == Movimiento.DERECHA)
				return TipoBloque.GATOESQUINAABAJODERECHA;
			return TipoBloque.GATOVERTICAL;
		default:

			if (movimientoActual == Movimiento.ABAJO || movimientoActual == Movimiento.ARRIBA)
				return TipoBloque.GATOVERTICAL;

			else if (movimientoActual == Movimiento.IZQUIERDA || movimientoActual == Movimiento.DERECHA)
				return TipoBloque.GATOHORIZONTAL;
			break;
		}

		return TipoBloque.LIBRE;
	}

	private static ColaDoblementeEnlazada<InformacionMovimiento> moverIzquierda(TipoBloque[][] grilla, int posX,
			int posY) {

		if (posX - 1 < 0 || grilla[posY][posX - 1] != TipoBloque.LIBRE)
			return null;

		int posInicial = posX;
		TipoBloque[][] aux = new TipoBloque[grilla.length][grilla[0].length];

		for (int i = 0; i < grilla.length; i++)
			aux[i] = grilla[i].clone();

		do {
			posX--;
			aux[posY][posX] = TipoBloque.BLOQUE;
		} while (posX - 1 >= 0 && aux[posY][posX - 1] == TipoBloque.LIBRE);

		ColaDoblementeEnlazada<InformacionMovimiento> Resultado = resolver(aux, posX, posY);

		if (Resultado != null) {

			InformacionMovimiento inf = new InformacionMovimiento();
			inf.setMovimiento(Movimiento.IZQUIERDA);
			inf.setCantidadBloquesMovidos(Math.abs(posInicial - posX));
			
			//Modificar siguiente movimiento (Esquina)

			if (Resultado.tieneCabezera()) {
				InformacionMovimiento infSiguiente = Resultado.obtener();
				infSiguiente.setEsquina(obtenerEsquina(Movimiento.IZQUIERDA, infSiguiente.getMovimiento()));
			}
			
			//------------------------------------//

			Resultado.insertar(inf);
			return Resultado;
		}

		return null;
	}

	private static ColaDoblementeEnlazada<InformacionMovimiento> moverDerecha(TipoBloque[][] grilla, int posX,
			int posY) {

		if (posX >= grilla[0].length - 1 || grilla[posY][posX + 1] != TipoBloque.LIBRE)
			return null;

		int posInicial = posX;
		TipoBloque[][] aux = new TipoBloque[grilla.length][grilla[0].length];

		for (int i = 0; i < grilla.length; i++)
			aux[i] = grilla[i].clone();

		do {
			posX++;
			aux[posY][posX] = TipoBloque.BLOQUE;
		} while (posX < aux[0].length - 1 && aux[posY][posX + 1] == TipoBloque.LIBRE);

		ColaDoblementeEnlazada<InformacionMovimiento> Resultado = resolver(aux, posX, posY);

		if (Resultado != null) {

			InformacionMovimiento inf = new InformacionMovimiento();
			inf.setMovimiento(Movimiento.DERECHA);
			inf.setCantidadBloquesMovidos(Math.abs(posInicial - posX));

			//Modificar siguiente movimiento (Esquina)

			if (Resultado.tieneCabezera()) {
				InformacionMovimiento infSiguiente = Resultado.obtener();
				infSiguiente.setEsquina(obtenerEsquina(Movimiento.DERECHA, infSiguiente.getMovimiento()));
			}
			
			//------------------------------------//
			
			Resultado.insertar(inf);
			return Resultado;
		}

		return null;

	}

	private static ColaDoblementeEnlazada<InformacionMovimiento> moverArriba(TipoBloque[][] grilla, int posX,
			int posY) {

		if (posY - 1 < 0 || grilla[posY - 1][posX] != TipoBloque.LIBRE)
			return null;

		int posInicial = posY;
		TipoBloque[][] aux = new TipoBloque[grilla.length][grilla[0].length];

		for (int i = 0; i < grilla.length; i++)
			aux[i] = grilla[i].clone();

		do {
			posY--;
			aux[posY][posX] = TipoBloque.BLOQUE;
		} while (posY - 1 >= 0 && aux[posY - 1][posX] == TipoBloque.LIBRE);

		ColaDoblementeEnlazada<InformacionMovimiento> Resultado = resolver(aux, posX, posY);

		if (Resultado != null) {

			InformacionMovimiento inf = new InformacionMovimiento();
			inf.setMovimiento(Movimiento.ARRIBA);
			inf.setCantidadBloquesMovidos(Math.abs(posInicial - posY));

			//Modificar siguiente movimiento (Esquina)

			if (Resultado.tieneCabezera()) {
				InformacionMovimiento infSiguiente = Resultado.obtener();
				infSiguiente.setEsquina(obtenerEsquina(Movimiento.ARRIBA, infSiguiente.getMovimiento()));
			}
			
			//------------------------------------//
			
			Resultado.insertar(inf);
			return Resultado;
		}

		return null;
	}

	private static ColaDoblementeEnlazada<InformacionMovimiento> moverAbajo(TipoBloque[][] grilla, int posX, int posY) {

		if (posY >= grilla.length - 1 || grilla[posY + 1][posX] != TipoBloque.LIBRE)
			return null;

		int posInicial = posY;
		TipoBloque[][] aux = new TipoBloque[grilla.length][grilla[0].length];

		for (int i = 0; i < grilla.length; i++)
			aux[i] = grilla[i].clone();

		do {
			posY++;
			aux[posY][posX] = TipoBloque.BLOQUE;
		} while (posY < aux.length - 1 && aux[posY + 1][posX] == TipoBloque.LIBRE);

		ColaDoblementeEnlazada<InformacionMovimiento> Resultado = resolver(aux, posX, posY);

		if (Resultado != null) {

			InformacionMovimiento inf = new InformacionMovimiento();
			inf.setMovimiento(Movimiento.ABAJO);
			inf.setCantidadBloquesMovidos(Math.abs(posInicial - posY));

			//Modificar siguiente movimiento (Esquina)

			if (Resultado.tieneCabezera()) {
				InformacionMovimiento infSiguiente = Resultado.obtener();
				infSiguiente.setEsquina(obtenerEsquina(Movimiento.ABAJO, infSiguiente.getMovimiento()));
			}
			
			//------------------------------------//

			Resultado.insertar(inf);
			return Resultado;
		}

		return null;
	}

	public static ColaDoblementeEnlazada<InformacionMovimiento> resolver(TipoBloque[][] grilla, int posX, int posY) {

		// imprimirGrilla(grilla);
		if (grillaCompleta(grilla))
			return new ColaDoblementeEnlazada<InformacionMovimiento>();

		ColaDoblementeEnlazada<InformacionMovimiento> Resultado;

		// ---------------------------------------------//
		// Izquierda

		if ((Resultado = Procesamiento.moverIzquierda(grilla, posX, posY)) != null)
			return Resultado;
		// ---------------------------------------------//
		// Derecha

		if ((Resultado = Procesamiento.moverDerecha(grilla, posX, posY)) != null)
			return Resultado;

		// ---------------------------------------------//
		// Arriba

		if ((Resultado = Procesamiento.moverArriba(grilla, posX, posY)) != null)
			return Resultado;

		// ---------------------------------------------//
		// Abajo

		if ((Resultado = Procesamiento.moverAbajo(grilla, posX, posY)) != null)
			return Resultado;

		return null;
	}

	private static boolean grillaCompleta(TipoBloque[][] grilla) {

		for (int i = 0; i < grilla.length; i++)
			for (int j = 0; j < grilla[0].length; j++)
				if (grilla[i][j] == TipoBloque.LIBRE)
					return false;

		return true;
	}

	public static void imprimirLista(List<String> Pasos) {

		for (String paso : Pasos) {
			System.out.println(paso);
		}
	}
}
