package ventana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import defaultPackage.GestorImagenes;
import defaultPackage.Procesamiento;
import defaultPackage.Procesamiento.TipoBloque;

public class VentanaControlador {

	private VentanaModelo modelo;
	private VentanaVista vista;

	// Basico

	private JButton[][] botonesCuadricula;
	private TipoBloque[][] cuadricula;
	private int posX = -1, posY = -1;
	private List<String> Pasos;

	// Animacion

	private TipoBloque[][] cuadriculaPasos;
	private int posPasosX = -1, posPasosY = -1;
	private int pasoActual = -1;
	private String movimientoAnterior = "";

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

				if (Pasos != null) {
					vista.getBotonPasoAnterior().setEnabled(true);
					vista.getBotonPasoSiguiente().setEnabled(true);

					vista.getPasos().setText("");

					for (String paso : Pasos)
						vista.getPasos().setText(vista.getPasos().getText() + paso + "\n");

					pasoActual = 0;
					posPasosX = posX;
					posPasosY = posY;
					movimientoAnterior = "";

					cuadriculaPasos = new TipoBloque[cuadricula.length][cuadricula[0].length];

					for (int i = 0; i < cuadricula.length; i++)
						cuadriculaPasos[i] = cuadricula[i].clone();

				} else {
					vista.getBotonPasoAnterior().setEnabled(false);
					vista.getBotonPasoSiguiente().setEnabled(false);
				}
			}
		});

		this.vista.getBotonPasoSiguiente().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Dibujar paso

				if (pasoActual >= Pasos.size())
					return;

				String movimientoActual = Pasos.get(pasoActual);

				dibujarEsquina(Procesamiento.obtenerEsquina(movimientoAnterior, movimientoActual));

				switch (movimientoActual) {
				case "Izquierda":

					dibujarIzquierda();
					break;

				case "Derecha":

					dibujarDerecha();
					break;

				case "Arriba":

					dibujarArriba();
					break;

				case "Abajo":

					dibujarAbajo();
					break;
				}

				movimientoAnterior = movimientoActual;

				pasoActual++;
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
							boton.setIcon(GestorImagenes.getImage("campo"));

						} else if (SwingUtilities.isLeftMouseButton(e)) {

							cuadricula[fila][columna] = TipoBloque.BLOQUE;
							boton.setIcon(GestorImagenes.getImage("bloque"));

						} else if (SwingUtilities.isRightMouseButton(e)) {

							if (posX >= 0 || posY >= 0)
								return;

							posX = columna;
							posY = fila;

							cuadricula[fila][columna] = TipoBloque.GATO;
							boton.setIcon(GestorImagenes.getImage("gato"));

						}
					}
				});

				if (i == 0 || i == filas - 1 || j == 0 || j == columnas - 1) {
					cuadricula[i][j] = TipoBloque.BLOQUE;
					boton.setIcon(GestorImagenes.getImage("bloque"));
				}

				else {
					cuadricula[i][j] = TipoBloque.LIBRE;
					boton.setIcon(GestorImagenes.getImage("campo"));
				}

				botonesCuadricula[i][j] = boton;

				panelCuadrilla.add(boton);
			}
		}

		vista.setSize(anchoVentana, altoVentana);
	}

	private void dibujarEsquina(TipoBloque esquina) {

		cuadriculaPasos[posPasosY][posPasosX] = esquina;

		switch (esquina) {
		case GATOESQUINAABAJODERECHA:
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("giroAbajoDerecha"));
			break;
		case GATOESQUINAABAJOIZQUIERDA:
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("giroAbajoIzquierda"));
			break;
		case GATOESQUINAARRIBADERECHA:
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("giroArribaDerecha"));
			break;
		case GATOESQUINAARRIBAIZQUIERDA:
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("giroArribaIzquierda"));
			break;
		case GATOHORIZONTAL:
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("desplazamientoHorizontal"));
			break;
		case GATOVERTICAL:
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("desplazamientoVertical"));
			break;
		default:
			break;
		}
	}

	private void dibujarIzquierda() {
		while (posPasosX - 1 >= 0 && cuadriculaPasos[posPasosY][posPasosX - 1] == TipoBloque.LIBRE) {
			posPasosX--;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATOHORIZONTAL;
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("desplazamientoHorizontal"));
		}
		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATO;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("gato"));
	}

	private void dibujarDerecha() {
		while (posPasosX + 1 < cuadriculaPasos[0].length
				&& cuadriculaPasos[posPasosY][posPasosX + 1] == TipoBloque.LIBRE) {
			posPasosX++;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATOHORIZONTAL;
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("desplazamientoHorizontal"));
		}
		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATO;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("gato"));
	}

	private void dibujarArriba() {
		while (posPasosY - 1 >= 0 && cuadriculaPasos[posPasosY - 1][posPasosX] == TipoBloque.LIBRE) {
			posPasosY--;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATOVERTICAL;
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("desplazamientoVertical"));
		}
		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATO;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("gato"));
	}

	private void dibujarAbajo() {
		while (posPasosY + 1 < cuadriculaPasos.length
				&& cuadriculaPasos[posPasosY + 1][posPasosX] == TipoBloque.LIBRE) {
			posPasosY++;
			cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATOVERTICAL;
			botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("desplazamientoVertical"));
		}
		cuadriculaPasos[posPasosY][posPasosX] = TipoBloque.GATO;
		botonesCuadricula[posPasosY][posPasosX].setIcon(GestorImagenes.getImage("gato"));
	}

}
