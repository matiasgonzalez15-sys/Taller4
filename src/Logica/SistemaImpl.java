package Logica;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import Dominio.*;
import Strategy.EstrategiaOrdenar;
import Visitor.VisitorCalcularPoder;
import Visitor.VisitorPoder;

public class SistemaImpl implements Sistema {
	private List<Carta> cartas;
	private static SistemaImpl instancia;

	private SistemaImpl() {
		this.cartas = new LinkedList<Carta>();
	}

	public static SistemaImpl getInstancia() {
		if (instancia == null) {
			instancia = new SistemaImpl();
		}
		return instancia;
	}

	@Override
	public void crearObjetos(String[] partes) throws Exception {
		if (partes.length == 0) {
			return;
		}
		FactoryCarta factory = new FactoryCarta();
		Carta c = factory.crear(partes);
		if (c != null) {
			VisitorPoder visitor = new VisitorCalcularPoder();
			c.accept(visitor);
			cartas.add(c);
			guardarCartas();
		}
		

	}

	@Override
	public List<Carta> estrategia(EstrategiaOrdenar estrategia) {
		return estrategia.ordenar(cartas);
	}

	@Override
	public List<Carta> getCartas() {
		return cartas;
	}

	@Override
	public void eliminarCarta(int indice) throws Exception {
		cartas.remove(indice);
		guardarCartas();
		
	}

	@Override
	public void guardarCartas() throws Exception {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("Sobres.txt"))){;
			for(Carta c : cartas) {
				String linea = c.getNombre() + ";" + c.getRareza();
				if(c.getClass().getSimpleName().equalsIgnoreCase("pokemon")) {
					Pokemon p = (Pokemon) c;
					linea += ";Pokemon;" + p.getDaño() + ";" + p.getCantEnergia();
				}
				if(c.getClass().getSimpleName().equalsIgnoreCase("item")){
					Item i = (Item) c;
					linea += ";Item;" + i.getBonificacion();
				}
				if(c.getClass().getSimpleName().equalsIgnoreCase("supporter")) {
					Supporter s = (Supporter) c;
					linea += ";Supporter;" + s.getEfectosTurno();
				}
				if(c.getClass().getSimpleName().equalsIgnoreCase("energy")) {
					Energy e = (Energy) c;
					linea += ";Energy;" + e.getElemento();
				}
				bw.write(linea);
				bw.newLine();
			}
			bw.close();
		}
	}	

	

	

}
