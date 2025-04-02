package estructuras;

import estructuras.Enumerativos.Movimiento;
import estructuras.Enumerativos.TipoBloque;

public class InformacionMovimiento {

	private Movimiento movimiento;
	private TipoBloque esquina;
	private int cantidadBloquesMovidos;
	
	public InformacionMovimiento() {
	}
	
	public InformacionMovimiento(Movimiento movimiento, TipoBloque esquina, int cantidadBloquesMovidos) {
		super();
		this.movimiento = movimiento;
		this.esquina = esquina;
		this.cantidadBloquesMovidos = cantidadBloquesMovidos;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}

	public TipoBloque getEsquina() {
		return esquina;
	}

	public void setEsquina(TipoBloque esquina) {
		this.esquina = esquina;
	}

	public int getCantidadBloquesMovidos() {
		return cantidadBloquesMovidos;
	}

	public void setCantidadBloquesMovidos(int cantidadBloquesMovidos) {
		this.cantidadBloquesMovidos = cantidadBloquesMovidos;
	}		
}
