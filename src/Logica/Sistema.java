package Logica;

import java.io.IOException;
import java.util.List;

import Dominio.Carta;
import Strategy.EstrategiaOrdenar;

public interface Sistema {
	void crearObjetos(String[] partes) throws Exception;
	List<Carta> estrategia(EstrategiaOrdenar estrategia);
	List<Carta> getCartas();
	void eliminarCarta(int indice) throws Exception;
	void guardarCartas() throws Exception;
		

}
