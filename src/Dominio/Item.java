package Dominio;

import Visitor.VisitorPoder;

public class Item extends Carta {
	private int bonificacion;

	public Item(String nombre, int rareza, int bonificacion) {
		super(nombre, rareza);
		this.bonificacion = bonificacion;
	}

	public int getBonificacion() {
		return bonificacion;
	}

	@Override
	public void accept(VisitorPoder visitor) {
		visitor.visit(this);
		
	}
	

}
