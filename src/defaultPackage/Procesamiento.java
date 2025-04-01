package defaultPackage;

import java.util.LinkedList;
import java.util.List;

public class Procesamiento {

	public enum TipoBloque {
		LIBRE, BLOQUE, GATO, GATOHORIZONTAL, GATOVERTICAL, GATOESQUINAABAJODERECHA, GATOESQUINAABAJOIZQUIERDA,
		GATOESQUINAARRIBADERECHA, GATOESQUINAARRIBAIZQUIERDA
	};

	public static TipoBloque obtenerEsquina(String movimientoAnterior, String movimientoActual) {

		switch (movimientoAnterior) {
		case "Izquierda":
			if (movimientoActual.equals("Arriba"))
				return TipoBloque.GATOESQUINAABAJODERECHA;
			else if (movimientoActual.equals("Abajo"))
				return TipoBloque.GATOESQUINAARRIBADERECHA;

			return TipoBloque.GATOHORIZONTAL;
		case "Derecha":
			if (movimientoActual.equals("Arriba"))
				return TipoBloque.GATOESQUINAABAJOIZQUIERDA;
			else if (movimientoActual.equals("Abajo"))
				return TipoBloque.GATOESQUINAARRIBAIZQUIERDA;
			return TipoBloque.GATOHORIZONTAL;
		case "Arriba":
			if (movimientoActual.equals("Izquierda"))
				return TipoBloque.GATOESQUINAARRIBAIZQUIERDA;
			else if (movimientoActual.equals("Derecha"))
				return TipoBloque.GATOESQUINAARRIBADERECHA;
			return TipoBloque.GATOVERTICAL;
		case "Abajo":
			if (movimientoActual.equals("Izquierda"))
				return TipoBloque.GATOESQUINAABAJOIZQUIERDA;
			else if (movimientoActual.equals("Derecha"))
				return TipoBloque.GATOESQUINAABAJODERECHA;
			return TipoBloque.GATOVERTICAL;
		default:

			if (movimientoActual.equals("Abajo") || movimientoActual.equals("Arriba"))
				return TipoBloque.GATOVERTICAL;

			else if (movimientoActual.equals("Izquierda") || movimientoActual.equals("Derecha"))
				return TipoBloque.GATOHORIZONTAL;
			break;
		}

		return TipoBloque.LIBRE;
	}

	private static List<String> moverIzquierda(TipoBloque[][] grilla, int posX, int posY) {

		if (posX - 1 < 0 || grilla[posY][posX - 1] != TipoBloque.LIBRE)
			return null;

		TipoBloque[][] aux = new TipoBloque[grilla.length][grilla[0].length];
		for (int i = 0; i < grilla.length; i++)
			aux[i] = grilla[i].clone();

		do {
			posX--;
			aux[posY][posX] = TipoBloque.BLOQUE;
		} while (posX - 1 >= 0 && aux[posY][posX - 1] == TipoBloque.LIBRE);

		List<String> Resultado = resolver(aux, posX, posY);

		if (Resultado != null) {
			Resultado.addFirst("Izquierda");
			return Resultado;
		}

		return null;
	}

	private static List<String> moverDerecha(TipoBloque[][] grilla, int posX, int posY) {

		if (posX >= grilla[0].length - 1 || grilla[posY][posX + 1] != TipoBloque.LIBRE)
			return null;

		TipoBloque[][] aux = new TipoBloque[grilla.length][grilla[0].length];
		for (int i = 0; i < grilla.length; i++)
			aux[i] = grilla[i].clone();

		do {
			posX++;
			aux[posY][posX] = TipoBloque.BLOQUE;
		} while (posX < aux[0].length - 1 && aux[posY][posX + 1] == TipoBloque.LIBRE);

		List<String> Resultado = resolver(aux, posX, posY);

		if (Resultado != null) {
			Resultado.addFirst("Derecha");
			return Resultado;
		}

		return null;

	}

	private static List<String> moverArriba(TipoBloque[][] grilla, int posX, int posY) {

		if (posY - 1 < 0 || grilla[posY - 1][posX] != TipoBloque.LIBRE)
			return null;

		TipoBloque[][] aux = new TipoBloque[grilla.length][grilla[0].length];
		for (int i = 0; i < grilla.length; i++)
			aux[i] = grilla[i].clone();

		do {
			posY--;
			aux[posY][posX] = TipoBloque.BLOQUE;
		} while (posY - 1 >= 0 && aux[posY - 1][posX] == TipoBloque.LIBRE);

		List<String> Resultado = resolver(aux, posX, posY);

		if (Resultado != null) {
			Resultado.addFirst("Arriba");
			return Resultado;
		}

		return null;
	}

	private static List<String> moverAbajo(TipoBloque[][] grilla, int posX, int posY) {

		if (posY >= grilla.length - 1 || grilla[posY + 1][posX] != TipoBloque.LIBRE)
			return null;

		TipoBloque[][] aux = new TipoBloque[grilla.length][grilla[0].length];
		for (int i = 0; i < grilla.length; i++)
			aux[i] = grilla[i].clone();

		do {
			posY++;
			aux[posY][posX] = TipoBloque.BLOQUE;
		} while (posY < aux.length - 1 && aux[posY + 1][posX] == TipoBloque.LIBRE);

		List<String> Resultado = resolver(aux, posX, posY);

		if (Resultado != null) {
			Resultado.addFirst("Abajo");
			return Resultado;
		}

		return null;
	}

	public static List<String> resolver(TipoBloque[][] grilla, int posX, int posY) {

		// imprimirGrilla(grilla);
		if (grillaCompleta(grilla))
			return new LinkedList<String>();

		List<String> Resultado;
		TipoBloque[][] aux;

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
