package Dominio;

public class FactoryCarta {

	public FactoryCarta() {
		
	}
	public Carta crear(String[] partes) {
		if(partes[2].equalsIgnoreCase("pokemon")) {
			return new Pokemon(partes[0], Integer.valueOf(partes[1]), Integer.valueOf(partes[3]), Integer.valueOf(partes[4]));
		}
		else if(partes[2].equalsIgnoreCase("item")) {
			return new Item(partes[0], Integer.valueOf(partes[1]), Integer.valueOf(partes[3]));
		}
		else if(partes[2].equalsIgnoreCase("supporter")){
			return new Supporter(partes[0], Integer.valueOf(partes[1]), Integer.valueOf(partes[3]));
		}
		else if(partes[2].equalsIgnoreCase("energy")) {
			return new Energy(partes[0], Integer.valueOf(partes[1]), partes[3]);
		}
		else {
			return null;
		}
	}

}
