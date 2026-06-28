package Dominio;

import Visitor.VisitorPoder;

public abstract class Carta {
	protected String nombre;
	protected int rareza;
	protected float poder;
	
	public Carta(String nombre, int rareza) {
		this.nombre = nombre;
		this.rareza = rareza;

	}

	public String getNombre() {
		return nombre;
	}

	public int getRareza() {
		return rareza;
	}

	public float getPoder() {
		return poder;
	}

	public void setPoder(float poder) {
		this.poder = poder;
	}

	public abstract void accept(VisitorPoder visitor);
	

}
