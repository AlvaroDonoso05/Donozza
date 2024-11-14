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
import modelo.Database;
import modelo.Ingredientes;
import controlador.FileWatcher;
import controlador.Logger;
import modelo.Pizzas;

import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	
	public JPanel panelCarta;
	public JButton btnCartaEntrantes;
	public JButton btnCartaPizzas;
	public JButton btnCartaPostres;
	public JButton btnCartaBebidas;
	public JPanel panelBotonesEntrantes;
	public JPanel panelIngredientes;
	public JPanel panelBotonesPizzas;
	public JPanel panelBotonesPostres;
	public JPanel panelBotonesBebidas;
	
	

	public static void main(String[] args) throws Exception {
		Logger logger = new Logger();
		Vista vista = new Vista();
		
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
		

		tablaMesa = new JTable();
		tablaMesa.setBounds(22, 77, 320, 251);
		panelMesa.add(tablaMesa);
		
		JScrollPane scrollPane = new JScrollPane(tablaMesa);
		scrollPane.setBounds(22, 77, 320, 251);
		panelMesa.add(scrollPane);
		
		panelCarta = new JPanel();
		panelCarta.setBounds(434, 92, 450, 436);
		contentPane.add(panelCarta);
		panelCarta.setLayout(null);
		
		btnCartaEntrantes = new JButton("Entrantes");
		btnCartaEntrantes.setBackground(new Color(128, 128, 255));
		btnCartaEntrantes.setBounds(0, 0, 105, 82);
		panelCarta.add(btnCartaEntrantes);
		
		btnCartaPizzas = new JButton("Pizza");
		btnCartaPizzas.setBackground(new Color(128, 128, 255));
		btnCartaPizzas.setBounds(115, 0, 105, 82);
		panelCarta.add(btnCartaPizzas);
		
		btnCartaPostres = new JButton("Postres");
		btnCartaPostres.setBackground(new Color(128, 128, 255));
		btnCartaPostres.setBounds(230, 0, 105, 82);
		panelCarta.add(btnCartaPostres);
		
		btnCartaBebidas = new JButton("Bebidas");
		btnCartaBebidas.setBackground(new Color(128, 128, 255));
		btnCartaBebidas.setBounds(345, 0, 105, 82);
		panelCarta.add(btnCartaBebidas);
		
		panelBotonesEntrantes = new JPanel();
		panelBotonesEntrantes.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelBotonesEntrantes.setBounds(0, 102, 450, 334);
		panelCarta.add(panelBotonesEntrantes);
		
		panelBotonesPizzas = new JPanel();
		panelBotonesPizzas.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelBotonesPizzas.setBounds(0, 102, 450, 334);
		panelCarta.add(panelBotonesPizzas);
		
		panelBotonesPostres = new JPanel();
		panelBotonesPostres.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelBotonesPostres.setBounds(0, 102, 450, 334);
		panelCarta.add(panelBotonesPostres);
		
		panelBotonesBebidas = new JPanel();
		panelBotonesBebidas.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelBotonesBebidas.setBounds(0, 102, 450, 334);
		panelCarta.add(panelBotonesBebidas);
		
		panelIngredientes = new JPanel();
		panelIngredientes.setBounds(0, 431, 450, 334);
		contentPane.add(panelIngredientes);
		
	}
}
