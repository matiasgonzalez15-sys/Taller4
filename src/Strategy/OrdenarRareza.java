package Strategy;

import java.util.LinkedList;
import java.util.List;

import Dominio.Carta;

public class OrdenarRareza implements EstrategiaOrdenar {
	
	public OrdenarRareza() {
	}

	@Override
	public List<Carta> ordenar(List<Carta> cartas) {
		List<Carta> copia = new LinkedList<Carta>(cartas);
		for(int a = 0; a < copia.size() - 1; a++) {
			for(int b = a+1; b < copia.size(); b++) {
				if(copia.get(a).getRareza() < copia.get(b).getRareza()) {
					Carta temp = copia.get(a);
					copia.set(a, copia.get(b));
					copia.set(b, temp);
				}
			}
		}
		return copia;
	}

}
