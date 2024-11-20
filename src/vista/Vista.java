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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JButton;

import controlador.Controlador;
import modelo.Database;
import modelo.Ingredientes;
import modelo.TablaPedidos;
import controlador.FileWatcher;
import controlador.Logger;
import modelo.Carta;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

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
	public JScrollPane scrollPane;
	public DefaultTableModel dtm;
	
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
	public JButton btnEliminarIngrediente;
	public JButton btnAgregarIngrediente;
	public JPanel panelImgIngredientes;
	public JButton btnIngrediente1;
	public JLabel lblCantidad;
	public JLabel lblNumCantidad;
	public JButton btnCobrar;
	public JButton btnQuitarProducto;
	
	

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
		panel.setBounds(10, 78, 373, 405);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 20, 10));
		
		btnMesa1 = new JButton("MESA 1");
		btnMesa1.setBackground(new Color(255, 255, 255));
		panel.add(btnMesa1);
		
		btnMesa2 = new JButton("MESA 2");
		btnMesa2.setBackground(new Color(255, 255, 255));
		panel.add(btnMesa2);
		
		btnMesa3 = new JButton("MESA 3");
		btnMesa3.setBackground(new Color(255, 255, 255));
		panel.add(btnMesa3);
		
		btnMesa4 = new JButton("MESA 4");
		btnMesa4.setBackground(new Color(255, 255, 255));
		panel.add(btnMesa4);
		
		btnMesa5 = new JButton("MESA 5");
		btnMesa5.setBackground(new Color(255, 255, 255));
		panel.add(btnMesa5);
		
		btnMesa6 = new JButton("MESA 6");
		panel.add(btnMesa6);
		
		btnMesa7 = new JButton("MESA 7");
		panel.add(btnMesa7);
		
		btnMesa8 = new JButton("MESA 8");
		panel.add(btnMesa8);
		
		
		
		panelMesa = new JPanel();
		panelMesa.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMesa.setBounds(10, 78, 373, 405);
		contentPane.add(panelMesa);
		panelMesa.setLayout(null);
		panelMesa.setVisible(false);
		
		
		btnPMVolver = new JButton("Volver");
		btnPMVolver.setBounds(10, 10, 85, 21);
		panelMesa.add(btnPMVolver);
		
		lblMesa = new JLabel("Mesa X");
		lblMesa.setBounds(159, 36, 45, 13);
		panelMesa.add(lblMesa);
		

		TablaPedidos tP = new TablaPedidos();
		tablaMesa = new JTable(tP);
		TableColumn column = tablaMesa.getColumnModel().getColumn(1);
		column.setPreferredWidth(200);
		tablaMesa.getTableHeader().setReorderingAllowed(false);
		tablaMesa.setBounds(22, 77, 320, 251);
		panelMesa.add(tablaMesa);
		
		scrollPane = new JScrollPane(tablaMesa);
		scrollPane.setBounds(22, 77, 320, 251);
		panelMesa.add(scrollPane);
		
		btnCobrar = new JButton("Cobrar");
		btnCobrar.setBounds(257, 338, 85, 57);
		panelMesa.add(btnCobrar);
		
		btnQuitarProducto = new JButton("Quitar Producto");
		btnQuitarProducto.setBounds(32, 338, 85, 57);
		panelMesa.add(btnQuitarProducto);
		
		panelCarta = new JPanel();
		panelCarta.setBounds(433, 78, 450, 436);
		contentPane.add(panelCarta);
		panelCarta.setLayout(null);
		
		btnCartaEntrantes = new JButton("Entrantes");
		btnCartaEntrantes.setBackground(new Color(128, 128, 255));
		btnCartaEntrantes.setBounds(0, 0, 105, 82);
		panelCarta.add(btnCartaEntrantes);
		
		btnCartaPizzas = new JButton("Pizza");
		btnCartaPizzas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		panelBotonesEntrantes.setLayout(new GridLayout(0, 1));
		JScrollPane cartaBoton = new JScrollPane(panelBotonesEntrantes);
		cartaBoton.setBounds(0, 102, 450, 334);
		cartaBoton.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelCarta.add(cartaBoton);
		
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
		panelIngredientes.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelIngredientes.setBounds(10, 493, 373, 253);
		contentPane.add(panelIngredientes);
		panelIngredientes.setLayout(null);
		
		btnEliminarIngrediente = new JButton("Eliminar");
		btnEliminarIngrediente.setEnabled(false);
		btnEliminarIngrediente.setBounds(10, 175, 96, 49);
		panelIngredientes.add(btnEliminarIngrediente);
		
		btnAgregarIngrediente = new JButton("Agregar");
		btnAgregarIngrediente.setBounds(267, 175, 96, 49);
		panelIngredientes.add(btnAgregarIngrediente);
		
		panelImgIngredientes = new JPanel();
		panelImgIngredientes.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelImgIngredientes.setBounds(10, 10, 353, 155);
		panelIngredientes.add(panelImgIngredientes);
		panelImgIngredientes.setLayout(null);
		
		btnIngrediente1 = new JButton("New button");
		btnIngrediente1.setBounds(10, 10, 48, 45);
		btnIngrediente1.setBackground(getForeground());
		panelImgIngredientes.add(btnIngrediente1);
		
		lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(159, 175, 45, 13);
		panelIngredientes.add(lblCantidad);
		
		lblNumCantidad = new JLabel("0");
		lblNumCantidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumCantidad.setBounds(159, 198, 45, 13);
		panelIngredientes.add(lblNumCantidad);
		
	}
}
