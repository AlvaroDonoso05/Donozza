package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import controlador.Controlador;
import controlador.ImagePanel;
import modelo.TablaPedidos;
import controlador.Logger;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Vista extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Logger logger = new Logger();
	public JLayeredPane contentPane;
	public ImagePanel logIn;
	public ImagePanel mainPanel;
	public JPanel loading = new JPanel();
	public JPanel panelMesas;
	public JPanel panelLogIn;
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
	
	public JLabel lblUsuario;
	public JLabel lblTitulo;
	public JLabel lblContrasena;
	public JTextField usernameField;
	public JPasswordField passwordField;
	public JLabel lblwrongPassword;
	public JButton btnIniciarSesion;
	
	

	public static void main(String[] args) throws Exception {
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
		
		contentPane = new JLayeredPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		generateLoadingPanel();
		loading.setVisible(true);
		
		generateLoginPanel();
		logIn.setVisible(false);
		
		generateMainPanel();
		mainPanel.setVisible(false);
		
		Timer timer = new Timer(10000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.success("Configuracíon inicial de base de datos terminada.");
				loading.setVisible(false);
				logIn.setVisible(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
		
		
	}

	private void generateMainPanel() {
		mainPanel = new ImagePanel("resources/img/background.png");
		mainPanel.setBackground(new Color(255, 204, 204));
		mainPanel.setForeground(new Color(255, 255, 255));
		mainPanel.setBounds(0, 0, 918, 801);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);
		
		panelMesas = new JPanel();
		panelMesas.setOpaque(false);
		panelMesas.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelMesas.setToolTipText("");
		panelMesas.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMesas.setBounds(10, 78, 373, 405);
		mainPanel.add(panelMesas);
		panelMesas.setLayout(new GridLayout(0, 2, 20, 10));

		panelMesa = new JPanel();
		panelMesa.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMesa.setBounds(10, 78, 373, 405);
		mainPanel.add(panelMesa);
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
		panelCarta.setOpaque(false);
		panelCarta.setBounds(433, 78, 450, 436);
		mainPanel.add(panelCarta);
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
		panelBotonesEntrantes.setLayout(new GridLayout(0, 3, 15, 15));
		panelBotonesEntrantes.setBackground(Color.LIGHT_GRAY);

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
		mainPanel.add(panelIngredientes);
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
	
	private void generateLoadingPanel() {
		ImageIcon newIcon = new ImageIcon("resources/img/pizza-load.gif");
		
		loading.setBackground(new Color(85, 52, 117));
		loading.setBounds(0, 0, 918, 801);
		loading.setLayout(null);
		contentPane.add(loading);

		JLabel loadingGIF = new JLabel("");
		loadingGIF.setBounds(206, 191, 678, 407);
		loadingGIF.setIcon(newIcon);
		loading.add(loadingGIF);
	}
	
	private void generateLoginPanel() {
		logIn = new ImagePanel("resources/img/background.png");
		logIn.setBackground(new Color(85, 52, 117));
		logIn.setBounds(0, 0, 918, 801);
		logIn.setLayout(null);
		contentPane.add(logIn);

		panelLogIn = new JPanel();
		panelLogIn.setBackground(new Color(24, 27, 29, 200));
		panelLogIn.setBounds(269, 223, 368, 340);
		logIn.add(panelLogIn);
		panelLogIn.setLayout(null);

		lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Segoe Print", Font.BOLD, 18));
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setBounds(10, 97, 106, 38);
		panelLogIn.add(lblUsuario);

		lblTitulo = new JLabel("Inicie Sesión");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Segoe Print", Font.PLAIN, 47));
		lblTitulo.setBounds(0, 0, 368, 71);
		panelLogIn.add(lblTitulo);

		lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasena.setForeground(Color.WHITE);
		lblContrasena.setFont(new Font("Segoe Print", Font.BOLD, 18));
		lblContrasena.setBounds(20, 148, 121, 41);
		panelLogIn.add(lblContrasena);
		
		usernameField = new JTextField();
		usernameField.setBounds(158, 106, 172, 26);
		panelLogIn.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(158, 158, 172, 26);
		panelLogIn.add(passwordField);

		btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.setBounds(110, 237, 137, 47);
		panelLogIn.add(btnIniciarSesion);

		lblwrongPassword = new JLabel("Contraseña Incorrecta!");
		lblwrongPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblwrongPassword.setForeground(new Color(255, 0, 0));
		lblwrongPassword.setBounds(110, 200, 137, 26);
		lblwrongPassword.setVisible(false);
		panelLogIn.add(lblwrongPassword);
	}
}
