package ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaVista extends JFrame{

	JPanel panelBotones, panelCuadrilla;
	JTextField filasTextField, columnasTextField;
	JButton actualizarGrilla, resolverNivel;
	
	JScrollPane scrollPanePasos;
	JTextArea pasos;
	JButton botonPasoAnterior, botonPasoSiguiente;

	final int anchoMin = 400;
	final int altoMin = 135;
	final int tamanioCuadradito = 40;
	
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
		innerPanel.add(actualizarGrilla);
		innerPanel.add(Box.createHorizontalStrut(10)); // Espacio entre componentes

		resolverNivel = new JButton("Resolver");
		innerPanel.add(resolverNivel);
		innerPanel.add(Box.createHorizontalStrut(10)); // Espacio entre componentes

		// Centrar verticalmente el innerPanel dentro de panelBotones
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
		panelBotones.add(Box.createVerticalGlue());
		panelBotones.add(innerPanel);
		panelBotones.add(Box.createVerticalGlue());

		panelCuadrilla = new JPanel();
		panelCuadrilla.setLayout(null); // Se adapta dinámicamente
		panelCuadrilla.setBackground(Color.WHITE);
		this.add(panelCuadrilla, BorderLayout.CENTER);
		
		pasos = new JTextArea();
		pasos.setEditable(false);
		pasos.setFocusable(false);
		pasos.setVisible(true);
		scrollPanePasos = new JScrollPane(pasos);
		
		botonPasoAnterior = new JButton("Paso anterior");
		botonPasoAnterior.setVisible(true);
		panelCuadrilla.add(botonPasoAnterior);
		
		botonPasoSiguiente = new JButton("Paso siguiente");
		botonPasoSiguiente.setVisible(true);
		panelCuadrilla.add(botonPasoSiguiente);
	}

	public JPanel getPanelBotones() {
		return panelBotones;
	}

	public void setPanelBotones(JPanel panelBotones) {
		this.panelBotones = panelBotones;
	}

	public JPanel getPanelCuadrilla() {
		return panelCuadrilla;
	}

	public void setPanelCuadrilla(JPanel panelCuadrilla) {
		this.panelCuadrilla = panelCuadrilla;
	}

	public JTextField getFilasTextField() {
		return filasTextField;
	}

	public void setFilasTextField(JTextField filasTextField) {
		this.filasTextField = filasTextField;
	}

	public JTextField getColumnasTextField() {
		return columnasTextField;
	}

	public void setColumnasTextField(JTextField columnasTextField) {
		this.columnasTextField = columnasTextField;
	}

	public JButton getActualizarGrilla() {
		return actualizarGrilla;
	}

	public void setActualizarGrilla(JButton actualizarGrilla) {
		this.actualizarGrilla = actualizarGrilla;
	}

	public JButton getResolverNivel() {
		return resolverNivel;
	}

	public void setResolverNivel(JButton resolverNivel) {
		this.resolverNivel = resolverNivel;
	}

	public JScrollPane getScrollPanePasos() {
		return scrollPanePasos;
	}

	public void setScrollPanePasos(JScrollPane scrollPanePasos) {
		this.scrollPanePasos = scrollPanePasos;
	}

	public JTextArea getPasos() {
		return pasos;
	}

	public void setPasos(JTextArea pasos) {
		this.pasos = pasos;
	}

	public JButton getBotonPasoAnterior() {
		return botonPasoAnterior;
	}

	public void setBotonPasoAnterior(JButton botonPasoAnterior) {
		this.botonPasoAnterior = botonPasoAnterior;
	}

	public JButton getBotonPasoSiguiente() {
		return botonPasoSiguiente;
	}

	public void setBotonPasoSiguiente(JButton botonPasoSiguiente) {
		this.botonPasoSiguiente = botonPasoSiguiente;
	}

	public int getAnchoMin() {
		return anchoMin;
	}

	public int getAltoMin() {
		return altoMin;
	}

	public int getTamanioCuadradito() {
		return tamanioCuadradito;
	}

	
	
	
}