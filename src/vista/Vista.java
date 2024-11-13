package vista;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

import controlador.Controlador;
import modelo.Comanda;
import modelo.Ingredientes;
import controlador.FileWatcher;
import controlador.Logger;
import modelo.Pizzas;
import modelo.Mesa;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JTable;

public class Vista extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JPanel panel;
	public JButton btnMesa1;
	public JButton btnMesa2;
	public JButton btnMesa3;
	public JButton btnMesa4;
	public JButton btnMesa5;
	public JButton btnMesa6;
	public JButton btnMesa7;
	public JButton btnMesa8;
	public JPanel panelMesa;
	public JButton btnPMVolver;
	public JLabel lblMesa;
	public JTable tablaMesa;
	public Controlador controlador;
	public String [] nombreColumnas = {"Nombre","Cantidad","Precio"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		Logger logger = new Logger();
		Vista vista = new Vista();
		
		try {
			Pizzas listaPizzas = new Pizzas("resources/json/pizzas.json");
			Ingredientes listaIngredientes = new Ingredientes("resources/json/ingredientes.json");
			
			FileWatcher watcherPizzas = new FileWatcher(listaPizzas);
			FileWatcher watcherIngredientes = new FileWatcher(listaIngredientes);
			
			watcherPizzas.start();
			watcherIngredientes.start();
			
		} catch(Exception e) {
			logger.error(e);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					vista.controlador = new Controlador(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					logger.error(e);
				}
			}
		});
	}

	public Vista() throws Exception {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 934, 840);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setToolTipText("");
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 92, 373, 436);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 20, 10));
		
		btnMesa1 = new JButton("MESA 1");
		panel.add(btnMesa1);
		
		btnMesa2 = new JButton("MESA 2");
		panel.add(btnMesa2);
		
		btnMesa3 = new JButton("MESA 3");
		panel.add(btnMesa3);
		
		btnMesa4 = new JButton("MESA 4");
		panel.add(btnMesa4);
		
		btnMesa5 = new JButton("MESA 5");
		panel.add(btnMesa5);
		
		btnMesa6 = new JButton("MESA 6");
		panel.add(btnMesa6);
		
		btnMesa7 = new JButton("MESA 7");
		panel.add(btnMesa7);
		
		btnMesa8 = new JButton("MESA 8");
		panel.add(btnMesa8);
		
		
		
		panelMesa = new JPanel();
		panelMesa.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMesa.setBounds(10, 92, 373, 436);
		contentPane.add(panelMesa);
		panelMesa.setLayout(null);
		panelMesa.setVisible(false);
		
		
		btnPMVolver = new JButton("Volver");
		btnPMVolver.setBounds(10, 10, 85, 21);
		panelMesa.add(btnPMVolver);
		
		lblMesa = new JLabel("Mesa X");
		lblMesa.setBounds(159, 36, 45, 13);
		panelMesa.add(lblMesa);
		
		Mesa mesa = new Mesa(1);
		Comanda comanda = new Comanda("Pepe", 1, 3);
		mesa.getComandasMesa().add(comanda);
		

		tablaMesa = new JTable(mesa.llenarMatrizComandas(), nombreColumnas);
		tablaMesa.setBounds(22, 77, 320, 251);
		panelMesa.add(tablaMesa);
		
		JScrollPane scrollPane = new JScrollPane(tablaMesa);
		scrollPane.setBounds(22, 77, 320, 251);
		panelMesa.add(scrollPane);
		
	}
}
