package Logica;

import java.util.*;

import Dominio.*;
import Visitor.VisitorCalcularPoder;
import Visitor.VisitorPoder;

public class SistemaImpl implements Sistema {
	private List<Carta> cartas;
	private static SistemaImpl instancia;
	


	private SistemaImpl() {
		this.cartas = new LinkedList<Carta>();
	}
	


	public static SistemaImpl getInstancia() {
		if(instancia == null) {
			instancia = new SistemaImpl();
		}
		return instancia;
	}



	@Override
	public void crearObjetos(String[] partes) {
		if(partes.length == 0) {
			return;
		}
		FactoryCarta factory = new FactoryCarta();
		Carta c = factory.crear(partes);
		if(c!= null) {
			VisitorPoder visitor = new VisitorCalcularPoder();
			c.accept(visitor);
			cartas.add(c);
		}
		
	}

}
