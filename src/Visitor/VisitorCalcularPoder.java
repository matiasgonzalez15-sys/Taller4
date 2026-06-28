package Visitor;

import Dominio.Energy;
import Dominio.Item;
import Dominio.Pokemon;
import Dominio.Supporter;

public class VisitorCalcularPoder implements VisitorPoder {
	private float poder;
	

	public VisitorCalcularPoder() {
		this.poder = 0;
	}

	@Override
	public void visit(Pokemon p) {
		poder = ((p.getDaño() / p.getCantEnergia()) * 100);
		p.setPoder(poder);

	}

	@Override
	public void visit(Item i) {
		poder = (i.getBonificacion() * 20);
		i.setPoder(poder);

	}

	@Override
	public void visit(Supporter s) {
		poder = (s.getEfectosTurno() * 50);
		s.setPoder(poder);
	}

	@Override
	public void visit(Energy e) {
		e.setPoder(1);

	}

}
