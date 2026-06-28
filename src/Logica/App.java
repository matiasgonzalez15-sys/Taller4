package Logica;

import java.io.*;
import java.util.*;

public class App {
	// Matias Ignacio Gonzalez Gomez 22.350.340-3
	static SistemaImpl sistema = SistemaImpl.getInstancia();
	public static void main(String[] args) throws FileNotFoundException {
		cargarArchivo();

	}
	private static void cargarArchivo() throws FileNotFoundException {
		File arch = new File("Sobres.txt");
		Scanner lector = new Scanner(arch);
		while(lector.hasNext()) {
			String linea = lector.nextLine();
			String[] partes = linea.split(";");
			sistema.crearObjetos(partes);
		}
		
	}

}
