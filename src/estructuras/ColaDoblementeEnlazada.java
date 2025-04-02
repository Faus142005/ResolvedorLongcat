package estructuras;

import java.util.Iterator;

final class Nodo<E> {
	E valor;
	Nodo<E> anterior, siguiente;

	public Nodo() {
		anterior = siguiente = null;
	}

	public Nodo(E valor) {
		super();
		this.valor = valor;
	}
}

public class ColaDoblementeEnlazada<E> implements Iterable<E> {

	private Nodo<E> apuntando;
	private int tamanio;

	public ColaDoblementeEnlazada() {
		apuntando = null;
		tamanio = 0;
	}

	public void insertar(E valor) {

		// Crea el nodo con su anterior y siguiente

		Nodo<E> nuevoNodo = new Nodo<E>(valor);
		nuevoNodo.siguiente = apuntando;

		if (apuntando != null) {

			nuevoNodo.anterior = apuntando.anterior;

			// Modificar nodo anterior
			if (apuntando.anterior != null)
				apuntando.anterior.siguiente = nuevoNodo;

			// Modificar nodo siguiente
			apuntando.anterior = nuevoNodo;
		}

		apuntando = nuevoNodo;
		tamanio++;
	}

	public void eliminar() {

		if (apuntando == null)
			return;

		if (apuntando.anterior != null)
			apuntando.anterior.siguiente = apuntando.siguiente;

		if (apuntando.siguiente != null)
			apuntando.siguiente.anterior = apuntando.anterior;

		if (apuntando.anterior != null)
			apuntando = apuntando.anterior;
		else
			apuntando = apuntando.siguiente;

		tamanio--;
	}

	public boolean tieneSiguiente() {
		return apuntando != null && apuntando.siguiente != null;
	}

	public void avanzar() {
		apuntando = apuntando.siguiente;
	}

	public boolean tieneAnterior() {
		return apuntando != null && apuntando.anterior != null;
	}

	public void retroceder() {
		apuntando = apuntando.anterior;
	}

	public boolean tieneCabezera() {
		return apuntando != null;
	}

	public E obtener() {
		return apuntando.valor;
	}

	public int obtenerTamanio() {
		return tamanio;
	}

	@Override
	public Iterator<E> iterator() {
		return new ColaDoblementeEnlazadaIterator(apuntando);
	}

	private class ColaDoblementeEnlazadaIterator implements Iterator<E> {

		private Nodo<E> actual;

		public ColaDoblementeEnlazadaIterator(Nodo<E> inicio) {
			this.actual = inicio;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return actual != null;
		}

		@Override
		public E next() {
            Nodo<E> temp = actual;
            actual = actual.siguiente;
            return temp.valor;
		}

	}
}
