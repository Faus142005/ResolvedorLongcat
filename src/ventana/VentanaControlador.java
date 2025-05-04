package ventana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import defaultPackage.GestorImagenes;
import estructuras.ColaDoblementeEnlazada;
import estructuras.Enumerativos.TipoBloque;
import estructuras.InformacionMovimiento;

public class VentanaControlador {

	private VentanaModelo modelo;
	private VentanaVista vista;

	// Basico

	private JButton[][] botonesCuadricula;
	private TipoBloque[][] cuadricula;
	private int posX = -1, posY = -1;
	private ColaDoblementeEnlazada<InformacionMovimiento> Pasos;

	// Animacion

	private TipoBloque[][] cuadriculaPasos;
	private int posPasosX = -1, posPasosY = -1;

	public VentanaControlador(VentanaModelo modelo, VentanaVista vista) {

		this.modelo = modelo;
		this.vista = vista;

		this.aniadirActionListeners();
	}

	private void aniadirActionListeners() {

		this.vista.getActualizarGrilla().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				posX = posY = -1;
				crearCuadrilla();
				vista.getBotonPasoAnterior().setEnabled(false);
				vista.getBotonPasoSiguiente().setEnabled(false);
				vista.getPasos().setText("");
				vista.revalidate();
				vista.repaint();
			}
		});

		this.vista.getResolverNivel().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Pasos = modelo.resolverNivel(cuadricula, posX, posY);

				vista.getPasos().setText("");

				if (Pasos != null) {
					vista.getBotonPasoSiguiente().setEnabled(true);

					for (InformacionMovimiento paso : Pasos)
						vista.getPasos().setText(vista.getPasos().getText() + paso.getMovimiento() + "\n");

					posPasosX = posX;
					posPasosY = posY;

					cuadriculaPasos = new TipoBloque[cuadricula.length][cuadricula[0].length];

					for (int i = 0; i < cuadricula.length; i++)
						cuadriculaPasos[i] = cuadricula[i].clone();

					Pasos.insertar(null);

				} else {
					vista.getBotonPasoAnterior().setEnabled(false);
					vista.getBotonPasoSiguiente().setEnabled(false);
				}
			}
		});

		this.vista.getBotonPasoAnterior().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				dibujarEsquina(TipoBloque.LIBRE);

				InformacionMovimiento infMov = Pasos.obtener();

				switch (infMov.getMovimiento()) {
				case IZQUIERDA:

					limpiarDerecha(infMov);
					break;

				case DERECHA:

					limpiarIzquierda(infMov);
					break;

				case ARRIBA:

					limpiarAbajo(infMov);
					break;

				case ABAJO:

					limpiarArriba(infMov);
					break;
				}

				Pasos.retroceder();

				if (!Pasos.tieneAnterior())
					vista.getBotonPasoAnterior().setEnabled(false);

				dibujarEsquina(TipoBloque.GATO);

				vista.revalidate();
				vista.repaint();
			}
		});

		this.vista.getBotonPasoSiguiente().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Dibujar paso

				Pasos.avanzar();
				InformacionMovimiento infMov = Pasos.obtener();
				dibujarEsquina(infMov.getEsquina());

				switch (infMov.getMovimiento()) {
				case IZQUIERDA:

					dibujarIzquierda(infMov);
					break;

				case DERECHA:

					dibujarDerecha(infMov);
					break;

				case ARRIBA:

					dibujarArriba(infMov);
					break;

				case ABAJO:

					dibujarAbajo(infMov);
					break;
				}

				if (!Pasos.tieneSiguiente())
					vista.getBotonPasoSiguiente().setEnabled(false);

				if (!vista.getBotonPasoAnterior().isEnabled())
					vista.getBotonPasoAnterior().setEnabled(true);

				vista.revalidate();
				vista.repaint();
			}
		});
	}

	private void crearCuadrilla() {

		final JTextField filasTextField = vista.getFilasTextField();
		final JTextField columnasTextField = vista.getFilasTextField();
		final JPanel panelCuadrilla = vista.getPanelCuadrilla();

		final int tamanioCuadradito = vista.getTamanioCuadradito();
		final int anchoMin = vista.getAnchoMin();
		final int altoMin = vista.getAltoMin();

		final JScrollPane scrollPanePasos = vista.getScrollPanePasos();

		final JButton botonPasoAnterior = vista.getBotonPasoAnterior();
		final JButton botonPasoSiguiente = vista.getBotonPasoSiguiente();

		int filas = Integer.parseInt(filasTextField.getText());
		int columnas = Integer.parseInt(columnasTextField.getText());

		// -------------------------------------------------------//

		panelCuadrilla.removeAll();

		if (filas < 1 || columnas < 1)
			return;

		cuadricula = new TipoBloque[filas][columnas];
		botonesCuadricula = new JButton[filas][columnas];

		// -------------------------------------------------------//

		final int altoCuadrilla = filas * tamanioCuadradito;
		final int anchoCuadrilla = columnas * tamanioCuadradito;

		int anchoVentana = anchoMin;
		int altoVentana = altoCuadrilla + 20 + altoMin;

		if (anchoCuadrilla + 10 + 100 + 10 + 10 + 100 + 10 > anchoMin)
			anchoVentana = anchoCuadrilla + 10 + 120 + 10 + 10 + 120 + 10; // Margenes

		final int margenSuperior = 10;
		final int centrarCuadrilla = (anchoVentana - anchoCuadrilla) / 2;

		// -------------------------------------------------------//

		scrollPanePasos.setBounds(centrarCuadrilla + anchoCuadrilla + 10, 10, 120, altoCuadrilla);

		botonPasoAnterior.setBounds(10, altoCuadrilla / 2 - 20, 120, 30);
		botonPasoSiguiente.setBounds(10, altoCuadrilla / 2 + 20, 120, 30);

		panelCuadrilla.add(scrollPanePasos);
		panelCuadrilla.add(botonPasoAnterior);
		panelCuadrilla.add(botonPasoSiguiente);

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {

				JButton boton = new JButton();
				boton.putClientProperty("fila", i);
				boton.putClientProperty("columna", j);

				boton.setBounds(centrarCuadrilla + j * tamanioCuadradito, margenSuperior + i * tamanioCuadradito,
						tamanioCuadradito, tamanioCuadradito);

				boton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						int fila = (int) boton.getClientProperty("fila");
						int columna = (int) boton.getClientProperty("columna");

						if (cuadricula[fila][columna] != TipoBloque.LIBRE) {

							if (cuadricula[fila][columna] == TipoBloque.GATO)
								posX = posY = -1;

							cuadricula[fila][columna] = TipoBloque.LIBRE;
							boton.setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));

						} else if (SwingUtilities.isLeftMouseButton(e)) {

							cuadricula[fila][columna] = TipoBloque.BLOQUE;
							boton.setIcon(GestorImagenes.getImage(TipoBloque.BLOQUE.toString()));

						} else if (SwingUtilities.isRightMouseButton(e)) {

							if (posX >= 0 || posY >= 0)
								return;

							posX = columna;
							posY = fila;

							cuadricula[fila][columna] = TipoBloque.GATO;
							boton.setIcon(GestorImagenes.getImage(TipoBloque.GATO.toString()));

						}
					}
				});

				if (i == 0 || i == filas - 1 || j == 0 || j == columnas - 1) {
					cuadricula[i][j] = TipoBloque.BLOQUE;
					boton.setIcon(GestorImagenes.getImage(TipoBloque.BLOQUE.toString()));
				}

				else {
					cuadricula[i][j] = TipoBloque.LIBRE;
					boton.setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));
				}

				botonesCuadricula[i][j] = boton;

				panelCuadrilla.add(boton);
			}
		}

		vista.setSize(anchoVentana, altoVentana);
	}

	private void dibujarEsquina(TipoBloque esquina) {

		cuadriculaPasos[posPasosY][posPasosX] = esquina;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(esquina.toString()));
	}

	private void dibujarIzquierda(InformacionMovimiento infMov) {

		for (int i = 0; i < infMov.getCantidadBloquesMovidos() - 1; i++) {
			posPasosX--;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATOHORIZONTAL;
			botonesCuadricula[posPasosY][posPasosX]
					.setIcon(GestorImagenes.getImage(TipoBloque.GATOHORIZONTAL.toString()));
		}

		posPasosX--;

		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATO;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.GATO.toString()));
	}

	private void dibujarDerecha(InformacionMovimiento infMov) {

		for (int i = 0; i < infMov.getCantidadBloquesMovidos() - 1; i++) {
			posPasosX++;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATOHORIZONTAL;
			botonesCuadricula[posPasosY][posPasosX]
					.setIcon(GestorImagenes.getImage(TipoBloque.GATOHORIZONTAL.toString()));
		}

		posPasosX++;

		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATO;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.GATO.toString()));
	}

	private void dibujarArriba(InformacionMovimiento infMov) {

		for (int i = 0; i < infMov.getCantidadBloquesMovidos() - 1; i++) {
			posPasosY--;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATOVERTICAL;
			botonesCuadricula[posPasosY][posPasosX]
					.setIcon(GestorImagenes.getImage(TipoBloque.GATOVERTICAL.toString()));
		}

		posPasosY--;

		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATO;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.GATO.toString()));
	}

	private void dibujarAbajo(InformacionMovimiento infMov) {

		for (int i = 0; i < infMov.getCantidadBloquesMovidos() - 1; i++) {
			posPasosY++;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATOVERTICAL;
			botonesCuadricula[posPasosY][posPasosX]
					.setIcon(GestorImagenes.getImage(TipoBloque.GATOVERTICAL.toString()));
		}

		posPasosY++;

		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATO;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.GATO.toString()));
	}

	private void limpiarIzquierda(InformacionMovimiento infMov) {

		for (int i = 0; i < infMov.getCantidadBloquesMovidos() - 1; i++) {
			posPasosX--;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.LIBRE;
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));
		}

		posPasosX--;

		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.LIBRE;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));
	}

	private void limpiarDerecha(InformacionMovimiento infMov) {

		for (int i = 0; i < infMov.getCantidadBloquesMovidos() - 1; i++) {
			posPasosX++;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.LIBRE;
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));
		}

		posPasosX++;

		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.LIBRE;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));
	}

	private void limpiarArriba(InformacionMovimiento infMov) {

		for (int i = 0; i < infMov.getCantidadBloquesMovidos() - 1; i++) {
			posPasosY--;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.LIBRE;
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));
		}

		posPasosY--;

		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.LIBRE;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));
	}

	private void limpiarAbajo(InformacionMovimiento infMov) {

		for (int i = 0; i < infMov.getCantidadBloquesMovidos() - 1; i++) {
			posPasosY++;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.LIBRE;
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));
		}

		posPasosY++;

		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.LIBRE;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage(TipoBloque.LIBRE.toString()));
	}
}
