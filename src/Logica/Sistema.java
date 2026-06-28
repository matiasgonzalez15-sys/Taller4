package Logica;

import Strategy.EstrategiaOrdenar;

public interface Sistema {
	void crearObjetos(String[] partes);
	String estrategia(EstrategiaOrdenar estrategia);

}
