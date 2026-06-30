package Logica;

import java.io.*;
import javax.swing.SwingUtilities;
import GUI.MainGUI;
import java.util.*;


public class App {
	// Matias Ignacio Gonzalez Gomez 22.350.340-3
	static SistemaImpl sistema = SistemaImpl.getInstancia();
	public static void main(String[] args) throws Exception {
		cargarArchivo();
		SwingUtilities.invokeLater(() -> new MainGUI());
		
	}
	private static void cargarArchivo() throws Exception {
	    File arch = new File("Sobres.txt");
	    Scanner lector = new Scanner(arch);
	    while (lector.hasNextLine()) {
	        String linea = lector.nextLine().trim();
	        if (linea.isEmpty()) {
	            continue;
	        }
	        String[] partes = linea.split(";");
	        try {
	            sistema.crearObjetos(partes);
	        } catch (Exception e) {
	            System.err.println("Línea inválida, se omite: " + linea + " (" + e.getMessage() + ")");
	        }
	    }
	    lector.close();
	}

}
