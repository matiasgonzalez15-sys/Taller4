package Dominio;

import Visitor.VisitorPoder;

public class Energy extends Carta {
	private String elemento;

	public Energy(String nombre, int rareza, String elemento) {
		super(nombre, rareza);
		this.elemento = elemento;
	}

	public String getElemento() {
		return elemento;
	}

	@Override
	public void accept(VisitorPoder visitor) {
		visitor.visit(this);		
	}
	

}
