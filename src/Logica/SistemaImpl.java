package Logica;

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
	public void crearObjetos(String[] partes) {
		if (partes.length == 0) {
			return;
		}
		FactoryCarta factory = new FactoryCarta();
		Carta c = factory.crear(partes);
		if (c != null) {
			VisitorPoder visitor = new VisitorCalcularPoder();
			c.accept(visitor);
			cartas.add(c);
		}

	}

	@Override
	public String estrategia(EstrategiaOrdenar estrategia) {
		String res = "";
		List<Carta> listaEstrategia = estrategia.ordenar(cartas);
		return res;
	}

	private String sacarExtras(Carta c) {
		String extras = "";
		if (c.getClass().getSimpleName().equalsIgnoreCase("pokemon")) {
			Pokemon p = (Pokemon) c;
			extras += " | Daño: " + p.getDaño() + " | Cant. Energia: " + p.getCantEnergia();
		} else if (c.getClass().getSimpleName().equalsIgnoreCase("item")) {
			Item i = (Item) c;
			extras += " | Bonificacion: " + i.getBonificacion();
		} else if (c.getClass().getSimpleName().equalsIgnoreCase("supporter")) {
			Supporter s = (Supporter) c;
			extras += " | Efectos Turno: " + s.getEfectosTurno();
		} else {
			Energy e = (Energy) c;
			extras += " | Elemento: " + e.getElemento();
		}
		return extras;
	}

}
