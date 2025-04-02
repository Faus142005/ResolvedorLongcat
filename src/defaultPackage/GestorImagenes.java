package defaultPackage;

import java.util.HashMap;

import javax.swing.ImageIcon;

import estructuras.Enumerativos.TipoBloque;

public class GestorImagenes {

	private static int tamanioImagenes = 40;

	private static HashMap<String, ImageIcon> imagenes;

	public static void cargarImagenes() {

		imagenes = new HashMap<String, ImageIcon>();
		
		//Gato
		
		imagenes.put(TipoBloque.GATO.toString(), new ImageIcon(new ImageIcon("imagenes/gato.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		//Bloques

		imagenes.put(TipoBloque.BLOQUE.toString(), new ImageIcon(new ImageIcon("imagenes/bloque.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put(TipoBloque.LIBRE.toString(), new ImageIcon(new ImageIcon("imagenes/campo.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		//Desplazamientos
		
		imagenes.put(TipoBloque.GATOVERTICAL.toString(), new ImageIcon(new ImageIcon("imagenes/desplazamientoVertical.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put(TipoBloque.GATOHORIZONTAL.toString(), new ImageIcon(new ImageIcon("imagenes/desplazamientoHorizontal.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		//Giros de esquina
		
		imagenes.put(TipoBloque.GATOESQUINAABAJODERECHA.toString(), new ImageIcon(new ImageIcon("imagenes/giroAbajoDerecha.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put(TipoBloque.GATOESQUINAABAJOIZQUIERDA.toString(), new ImageIcon(new ImageIcon("imagenes/giroAbajoIzquierda.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put(TipoBloque.GATOESQUINAARRIBADERECHA.toString(), new ImageIcon(new ImageIcon("imagenes/giroArribaDerecha.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put(TipoBloque.GATOESQUINAARRIBAIZQUIERDA.toString(), new ImageIcon(new ImageIcon("imagenes/giroArribaIzquierda.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
	}

	public static ImageIcon getImage(String nombreImagen) {

		return imagenes.get(nombreImagen);
	}
}
