package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

import Dominio.Carta;
import Dominio.Energy;
import Dominio.Item;
import Dominio.Pokemon;
import Dominio.Supporter;
import Logica.SistemaImpl;
import Strategy.OrdenarNombre;
import Strategy.OrdenarPoder;
import Strategy.OrdenarRareza;

public class MainGUI extends JFrame {
	private SistemaImpl sistema = SistemaImpl.getInstancia();
	private JPanel panelColeccion;

	public MainGUI() {
		setTitle("Colección de Cartas");
		setSize(850, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		createGUI();
		setVisible(true);

	}

	private void createGUI() {
		JTabbedPane pestanas = new JTabbedPane();
		pestanas.add("Administracion", crearPanelAdmin());
		pestanas.add("Ver Coleccion", crearPanelColeccion());
		add(pestanas);

	}

	private JPanel crearPanelColeccion() {
		JPanel panel = new JPanel(new BorderLayout());

		// Botones de orden
		JButton btnNombre = new JButton("Por Nombre");
		JButton btnRareza = new JButton("Por Rareza");
		JButton btnPoder = new JButton("Por Poder");

		JPanel botones = new JPanel(new FlowLayout());
		botones.add(new JLabel("Ordenar: "));
		botones.add(btnNombre);
		botones.add(btnRareza);
		botones.add(btnPoder);
		panel.add(botones, BorderLayout.NORTH);

		panelColeccion = new JPanel();
		panelColeccion.setLayout(new BoxLayout(panelColeccion, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(panelColeccion);
		panel.add(scroll, BorderLayout.CENTER);

		btnNombre.addActionListener(e -> refrescarColeccion(sistema.estrategia(new OrdenarNombre())));
		btnRareza.addActionListener(e -> refrescarColeccion(sistema.estrategia(new OrdenarRareza())));
		btnPoder.addActionListener(e -> refrescarColeccion(sistema.estrategia(new OrdenarPoder())));

		refrescarColeccion(sistema.getCartas());

		return panel;
	}

	private void mostrarDialogoModificar() {
		List<Carta> cartas = sistema.getCartas();
		if (cartas.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay cartas.");
			return;
		}

		String[] opciones = new String[cartas.size()];
		for (int i = 0; i < cartas.size(); i++) {
			opciones[i] = cartas.get(i).getNombre() + " | Rareza: " + cartas.get(i).getRareza();
		}
		String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona la carta a modificar:",
				"Modificar Carta", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
		if (seleccion == null)
			return;

		int idx = 0;
		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i].equals(seleccion)) {
				idx = i;
				break;
			}
		}

		Carta c = cartas.get(idx);

		try {
			if (c.getClass().getSimpleName().equalsIgnoreCase("pokemon")) {
				Pokemon p = (Pokemon) c;
				String daño = JOptionPane.showInputDialog(this, "Nuevo daño:", p.getDaño());
				String energia = JOptionPane.showInputDialog(this, "Nueva cant. energías:", p.getCantEnergia());
				if (daño == null || energia == null) {
					return;
				}
				String[] partes = { c.getNombre(), String.valueOf(c.getRareza()), "Pokemon", daño, energia };
				sistema.eliminarCarta(idx);
				sistema.crearObjetos(partes);

			} else if (c.getClass().getSimpleName().equalsIgnoreCase("item")) {
				Item item = (Item) c;
				String bono = JOptionPane.showInputDialog(this, "Nueva bonificación:", item.getBonificacion());
				if (bono == null) {
					return;
				}
				String[] partes = { c.getNombre(), String.valueOf(c.getRareza()), "Item", bono };
				sistema.eliminarCarta(idx);
				sistema.crearObjetos(partes);

			} else if (c.getClass().getSimpleName().equalsIgnoreCase("supporter")) {
				Supporter s = (Supporter) c;
				String efectos = JOptionPane.showInputDialog(this, "Nuevos efectos por turno:", s.getEfectosTurno());
				if (efectos == null) {
					return;
				}
				String[] partes = { c.getNombre(), String.valueOf(c.getRareza()), "Supporter", efectos };
				sistema.eliminarCarta(idx);
				sistema.crearObjetos(partes);

			} else if (c.getClass().getSimpleName().equalsIgnoreCase("energy")) {
				Energy en = (Energy) c;
				String elem = JOptionPane.showInputDialog(this, "Nuevo elemento:", en.getElemento());
				if (elem == null) {
					return;
				}
				String[] partes = { c.getNombre(), String.valueOf(c.getRareza()), "Energy", elem };

				sistema.eliminarCarta(idx);
				sistema.crearObjetos(partes);
			}
			JOptionPane.showMessageDialog(this, "Carta modificada.");
			refrescarColeccion(sistema.getCartas());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void refrescarColeccion(List<Carta> cartas) {
		panelColeccion.removeAll();
		for (Carta c : cartas) {
			JButton btn = new JButton(c.getNombre() + "  |  Rareza: " + c.getRareza() + "  |  Poder: " + c.getPoder());
			btn.addActionListener(e -> mostrarDetalleCarta(c));
			panelColeccion.add(btn);
		}
		panelColeccion.revalidate();
		panelColeccion.repaint();
	}

	private void mostrarDetalleCarta(Carta c) {
		JDialog dialogo = new JDialog(this, c.getNombre(), true);
		dialogo.setSize(320, 400);
		dialogo.setLocationRelativeTo(this);
		dialogo.setLayout(new BorderLayout(10, 10));

		ImageIcon img = cargarImagen(c.getNombre());
		JLabel lblImg = new JLabel(img, SwingConstants.CENTER);
		dialogo.add(lblImg, BorderLayout.NORTH);

		String info = "<html><div style='padding:10px'>" + "<b>Nombre:</b> " + c.getNombre() + "<br>"
				+ "<b>Rareza:</b> " + c.getRareza() + "<br>" + "<b>Poder:</b> " + c.getPoder() + "<br>"
				+ "<b>Tipo:</b> " + c.getClass().getSimpleName() + "</div></html>";
		dialogo.add(new JLabel(info), BorderLayout.CENTER);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(e -> dialogo.dispose());
		dialogo.add(btnCerrar, BorderLayout.SOUTH);

		dialogo.setVisible(true);
	}

	private ImageIcon cargarImagen(String nombre) {
		String[] extensiones = { ".png", ".jpg", ".jpeg" };
		for (String ext : extensiones) {
			File f = new File("imagenes/" + nombre + ext);
			if (f.exists()) {
				Image scaled = new ImageIcon(f.getPath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
				return new ImageIcon(scaled);
			}
		}

		File defecto = new File("imagenes/default.png");
		if (defecto.exists()) {
			Image scaled = new ImageIcon(defecto.getPath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			return new ImageIcon(scaled);
		}
		return new ImageIcon();
	}

	private void mostrarDialogoEliminar() throws Exception {
		List<Carta> cartas = sistema.getCartas();
		if (cartas.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay cartas en la colección.");
			return;
		}
		String[] opciones = new String[cartas.size()];
		for (int i = 0; i < cartas.size(); i++) {
			opciones[i] = cartas.get(i).getNombre() + " | Rareza: " + cartas.get(i).getRareza();
		}
		String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona la carta a eliminar:",
				"Eliminar Carta", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
		if (seleccion == null)
			return;

		int idx = 0;
		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i].equals(seleccion)) {
				idx = i;
				break;
			}
		}
		sistema.eliminarCarta(idx);
		JOptionPane.showMessageDialog(this, "Carta eliminada.");
		refrescarColeccion(sistema.getCartas());
	}

	private void mostrarDialogoAgregar() {
		String[] tipos = { "Pokemon", "Item", "Supporter", "Energy" };
		String tipo = (String) JOptionPane.showInputDialog(this, "Selecciona el tipo:", "Agregar Carta",
				JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
		if (tipo == null)
			return;

		String nombre = JOptionPane.showInputDialog(this, "Nombre de la carta:");
		if (nombre == null || nombre.trim().isEmpty())
			return;

		String rarezaStr = JOptionPane.showInputDialog(this, "Rareza (número):");
		if (rarezaStr == null)
			return;

		try {
			String[] partes;
			if (tipo.equals("Pokemon")) {
				String daño = JOptionPane.showInputDialog(this, "Daño:");
				String energia = JOptionPane.showInputDialog(this, "Cantidad de Energías:");
				partes = new String[] { nombre, rarezaStr, "Pokemon", daño, energia };
			} else if (tipo.equals("Item")) {
				String bono = JOptionPane.showInputDialog(this, "Bonificación:");
				partes = new String[] { nombre, rarezaStr, "Item", bono };
			} else if (tipo.equals("Supporter")) {
				String efectos = JOptionPane.showInputDialog(this, "Efectos por turno:");
				partes = new String[] { nombre, rarezaStr, "Supporter", efectos };
			} else {
				String elemento = JOptionPane.showInputDialog(this, "Elemento:");
				partes = new String[] { nombre, rarezaStr, "Energy", elemento };
			}
			sistema.crearObjetos(partes);
			JOptionPane.showMessageDialog(this, "¡Carta agregada!");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private JPanel crearPanelAdmin() {
		JPanel panel = new JPanel(new FlowLayout());

		JButton btnAgregar = new JButton("Agregar Carta");
		JButton btnEliminar = new JButton("Eliminar Carta");
		JButton btnModificar = new JButton("Modificar Carta");

		panel.add(btnAgregar);
		panel.add(btnEliminar);
		panel.add(btnModificar);

		btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
		btnEliminar.addActionListener(e -> {
			try {
				mostrarDialogoEliminar();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		btnModificar.addActionListener(e -> mostrarDialogoModificar());

		return panel;
	}

}
