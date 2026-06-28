package Strategy;

import java.util.LinkedList;
import java.util.List;

import Dominio.Carta;

public class OrdenarNombre implements EstrategiaOrdenar {
	

	public OrdenarNombre() {
	}

	@Override
	public List<Carta> ordenar(List<Carta> cartas) {
		List<Carta> copia = new LinkedList<Carta>(cartas);
		for(int a = 0; a < copia.size() - 1; a++) {
			for(int b = a+1; b < copia.size(); b++) {
				char c1 = Character.toLowerCase(copia.get(a).getNombre().charAt(0));
				char c2 = Character.toLowerCase(copia.get(b).getNombre().charAt(0));
				if(c1 < c2) {
					Carta temp = copia.get(a);
					copia.set(a, copia.get(b));
					copia.set(b, temp);
				}
			}
		}
		return copia;
	}

}
