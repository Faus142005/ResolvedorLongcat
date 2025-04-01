package defaultPackage;

import java.util.HashMap;

import javax.swing.ImageIcon;

public class GestorImagenes {

	private static int tamanioImagenes = 40;

	private static HashMap<String, ImageIcon> imagenes;

	public static void cargarImagenes() {

		imagenes = new HashMap<String, ImageIcon>();
		
		//Gato
		
		imagenes.put("gato", new ImageIcon(new ImageIcon("imagenes/gato.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		//Bloques

		imagenes.put("bloque", new ImageIcon(new ImageIcon("imagenes/bloque.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put("campo", new ImageIcon(new ImageIcon("imagenes/campo.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		//Desplazamientos
		
		imagenes.put("desplazamientoVertical", new ImageIcon(new ImageIcon("imagenes/desplazamientoVertical.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put("desplazamientoHorizontal", new ImageIcon(new ImageIcon("imagenes/desplazamientoHorizontal.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		//Giros de esquina
		
		imagenes.put("giroAbajoDerecha", new ImageIcon(new ImageIcon("imagenes/giroAbajoDerecha.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put("giroAbajoIzquierda", new ImageIcon(new ImageIcon("imagenes/giroAbajoIzquierda.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put("giroArribaDerecha", new ImageIcon(new ImageIcon("imagenes/giroArribaDerecha.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
		
		imagenes.put("giroArribaIzquierda", new ImageIcon(new ImageIcon("imagenes/giroArribaIzquierda.png").getImage()
				.getScaledInstance(tamanioImagenes, tamanioImagenes, java.awt.Image.SCALE_SMOOTH)));
	}

	public static ImageIcon getImage(String nombreImagen) {

		return imagenes.get(nombreImagen);
	}
}
