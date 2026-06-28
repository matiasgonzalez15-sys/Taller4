package Dominio;

import Visitor.VisitorPoder;

public class Pokemon extends Carta {
	private int daño;
	private int cantEnergia;
	
	public Pokemon(String nombre, int rareza, int daño, int cantEnergia) {
		super(nombre, rareza);
		this.daño = daño;
		this.cantEnergia = cantEnergia;
	}

	public int getDaño() {
		return daño;
	}

	public int getCantEnergia() {
		return cantEnergia;
	}

	@Override
	public void accept(VisitorPoder visitor) {
		visitor.visit(this);
		
	}
	
	

}
