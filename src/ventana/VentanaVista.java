package ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import defaultPackage.GestorImagenes;
import defaultPackage.Procesamiento;
import defaultPackage.Procesamiento.TipoBloque;

public class VentanaVista extends JFrame implements ActionListener {

	JPanel panelBotones, panelCuadrilla;
	JTextField filasTextField, columnasTextField;
	JButton actualizarGrilla, resolverNivel, pasoAnterior, pasoSiguiente;
	JButton cuadriculaBotones[][];
	TipoBloque cuadricula[][];

	final int anchoMin = 600;
	final int altoMin = 135;
	final int tamanioCuadradito = 30;

	int posX = -1, posY = -1;

	public VentanaVista() {
		this.setSize(anchoMin, altoMin);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());

		this.crearComponentes();

		this.setVisible(true);
	}

	private void crearComponentes() {
		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		panelBotones.setPreferredSize(new Dimension(anchoMin, 100));
		panelBotones.setBackground(Color.LIGHT_GRAY);
		this.add(panelBotones, BorderLayout.NORTH);

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));
		innerPanel.setOpaque(false);

		panelBotones.add(Box.createHorizontalGlue());
		panelBotones.add(innerPanel);
		panelBotones.add(Box.createHorizontalGlue());

		filasTextField = new JTextField(3);
		filasTextField.setHorizontalAlignment(SwingConstants.CENTER);
		filasTextField.setPreferredSize(new Dimension(50, 50));
		filasTextField.setMinimumSize(new Dimension(50, 50));
		filasTextField.setMaximumSize(new Dimension(50, 50));
		innerPanel.add(filasTextField);
		innerPanel.add(Box.createHorizontalStrut(10)); // Espacio entre componentes

		columnasTextField = new JTextField(3);
		columnasTextField.setHorizontalAlignment(SwingConstants.CENTER);
		columnasTextField.setPreferredSize(new Dimension(50, 50));
		columnasTextField.setMinimumSize(new Dimension(50, 50));
		columnasTextField.setMaximumSize(new Dimension(50, 50));
		innerPanel.add(columnasTextField);
		innerPanel.add(Box.createHorizontalStrut(10)); // Espacio entre componentes

		actualizarGrilla = new JButton("Actualizar");
		actualizarGrilla.addActionListener(this);
		innerPanel.add(actualizarGrilla);
		innerPanel.add(Box.createHorizontalStrut(10)); // Espacio entre componentes

		resolverNivel = new JButton("Resolver");
		resolverNivel.addActionListener(this);
		innerPanel.add(resolverNivel);
		innerPanel.add(Box.createHorizontalStrut(10)); // Espacio entre componentes

		pasoAnterior = new JButton("Paso Anterior");
		pasoAnterior.addActionListener(this);
		innerPanel.add(pasoAnterior);
		innerPanel.add(Box.createHorizontalStrut(10)); // Espacio entre componentes

		pasoSiguiente = new JButton("Paso Siguiente");
		pasoSiguiente.addActionListener(this);
		innerPanel.add(pasoSiguiente);

		// Centrar verticalmente el innerPanel dentro de panelBotones
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
		panelBotones.add(Box.createVerticalGlue());
		panelBotones.add(innerPanel);
		panelBotones.add(Box.createVerticalGlue());

		panelCuadrilla = new JPanel();
		panelCuadrilla.setLayout(null); // Se adapta dinámicamente
		panelCuadrilla.setBackground(Color.WHITE);
		this.add(panelCuadrilla, BorderLayout.CENTER);
	}

	private void crearCuadrilla() {
		int filas = Integer.parseInt(filasTextField.getText());
		int columnas = Integer.parseInt(columnasTextField.getText());
		panelCuadrilla.removeAll();

		if (filas < 1 || columnas < 1)
			return;

		cuadriculaBotones = new JButton[filas][columnas];
		cuadricula = new TipoBloque[filas][columnas];

		int ancho = anchoMin;
		int alto = filas * tamanioCuadradito + 20 + altoMin;

		if (columnas * tamanioCuadradito + 20 > anchoMin)
			ancho = columnas * tamanioCuadradito + 20;

		int margen = (ancho - columnas * tamanioCuadradito) / 2;

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {

				JButton boton = new JButton();
				boton.setBackground(Color.WHITE);
				boton.putClientProperty("fila", i);
				boton.putClientProperty("columna", j);

				boton.setBounds(margen + j * tamanioCuadradito, 10 + i * tamanioCuadradito, tamanioCuadradito,
						tamanioCuadradito);

				boton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						int fila = (int) boton.getClientProperty("fila");
						int columna = (int) boton.getClientProperty("columna");

						if (cuadricula[fila][columna] != TipoBloque.LIBRE) {

							if (cuadricula[fila][columna] == TipoBloque.GATO)
								posX = posY = -1;

							cuadricula[fila][columna] = TipoBloque.LIBRE;
							boton.setIcon(null);

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
				
				if(i == 0 || i == filas - 1|| j == 0 || j == columnas - 1) {
					cuadricula[i][j] = TipoBloque.BLOQUE;
					boton.setIcon(GestorImagenes.getImage("bloque"));
				}
				
				else {
					cuadricula[i][j] = TipoBloque.LIBRE;
					boton.setIcon(GestorImagenes.getImage("campo"));
				}
				
				panelCuadrilla.add(boton);
				cuadriculaBotones[i][j] = boton;
			}
		}

		this.setSize(ancho, alto);
	}

	private void resolverNivel() {

		if (cuadricula == null || cuadriculaBotones == null)
			return;

		System.out.println("Pos X: " + posX + " | Pos Y: " + posY);

		// Procesamiento.imprimirGrilla(cuadricula);

		if (!(posX < 0 && posY < 0))
			Procesamiento.imprimirLista(Procesamiento.resolver(cuadricula, posX, posY));
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(actualizarGrilla)) {
			this.crearCuadrilla();
			this.revalidate();
			this.repaint();
		}

		if (e.getSource().equals(resolverNivel)) {
			this.resolverNivel();
		}
	}
}