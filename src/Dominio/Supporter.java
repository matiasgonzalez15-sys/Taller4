package Dominio;

import Visitor.VisitorPoder;

public class Supporter extends Carta {
	private int efectosTurno;

	public Supporter(String nombre, int rareza, int efectosTurno) {
		super(nombre, rareza);
		this.efectosTurno = efectosTurno;
	}

	public int getEfectosTurno() {
		return efectosTurno;
	}

	@Override
	public void accept(VisitorPoder visitor) {
		visitor.visit(this);		
	}
	

}
