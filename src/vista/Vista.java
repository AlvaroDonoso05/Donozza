package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controlador.Controlador;
import controlador.ImagePanel;
import controlador.Logger;
import modelo.TablaIngredientes;
import modelo.TablaPedidos;
import modelo.TablaProductos;
import modelo.TablaUsuarios;
import javax.swing.JToggleButton;


public class Vista extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = new Logger();
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
    public JLabel lblFondo;
    public JLabel lblFondoMesa;
    public JLabel lblLogo;
    public JLabel lblLetrero;

    public JLabel lblUsuario;
    public JLabel lblTitulo;
    public JLabel lblContrasena;
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JLabel lblwrongPassword;
    public JButton btnIniciarSesion;
    
    public JPanel adminPanel;
    public JButton btnAddProducto;
    public JButton btnAddIngrediente;
    public JButton btnAddUser;

    public Vista() throws Exception {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 934, 840);

        contentPane = new JLayeredPane();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Generar Admin Panel
        generateAdminPanel();
        adminPanel.setVisible(false);

        
        generateLoadingPanel();
        loading.setVisible(true);

        generateLoginPanel();
        logIn.setVisible(false);

        generateMainPanel();
        mainPanel.setVisible(false);

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.success("Configuracíon inicial de base de datos terminada.");
                loading.setVisible(false);
                logIn.setVisible(true);
            }
        });
        timer.setRepeats(false);
        timer.start();


    }

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
    
    private void generateAdminPanel() {
        adminPanel = new JPanel();
        adminPanel.setLayout(new BorderLayout());
        adminPanel.setBounds(0, 0, 918, 801);
        adminPanel.setBackground(new Color(240, 240, 240));
        contentPane.add(adminPanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        adminPanel.add(tabbedPane, BorderLayout.CENTER);

        // Panel de Productos
        JPanel productosPanel = new JPanel();
        productosPanel.setLayout(null);
        tabbedPane.addTab("Gestión de Productos/Ingredientes", productosPanel);

        JLabel lblProductos = new JLabel("Productos");
        lblProductos.setFont(new Font("Segoe Print", Font.BOLD, 18));
        lblProductos.setBounds(20, 20, 200, 30);
        productosPanel.add(lblProductos);

        TablaProductos tableProductosModel = new TablaProductos();
        JTable tableProductos = new JTable(tableProductosModel);
        tableProductos.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollProductos = new JScrollPane(tableProductos);
        scrollProductos.setBounds(20, 60, 400, 200);
        productosPanel.add(scrollProductos);

        // Formulario para agregar productos
        addProductForm(productosPanel, tableProductosModel);

        JLabel lblIngredientes_1 = new JLabel("Ingredientes");
        lblIngredientes_1.setFont(new Font("Segoe Print", Font.BOLD, 18));
        lblIngredientes_1.setBounds(20, 288, 200, 30);
        productosPanel.add(lblIngredientes_1);

        // Tabla de ingredientes
        TablaIngredientes tableIngredientesModel = new TablaIngredientes();
        JTable tableIngredientes = new JTable(tableIngredientesModel);
        tableIngredientes.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollIngredientes = new JScrollPane(tableIngredientes);
        scrollIngredientes.setBounds(20, 328, 400, 200);
        productosPanel.add(scrollIngredientes);

        // Formulario para agregar ingredientes
        addIngredientForm(productosPanel, tableIngredientesModel);

        // Panel de Usuarios
        JPanel usuariosPanel = new JPanel();
        usuariosPanel.setLayout(null);
        tabbedPane.addTab("Gestión de Usuarios", usuariosPanel);

        JLabel lblUsuarios = new JLabel("Usuarios");
        lblUsuarios.setFont(new Font("Segoe Print", Font.BOLD, 18));
        lblUsuarios.setBounds(20, 20, 200, 30);
        usuariosPanel.add(lblUsuarios);

        // Tabla de usuarios
        TablaUsuarios tableUsuariosModel = new TablaUsuarios();
        JTable tableUsuarios = new JTable(tableUsuariosModel);
        tableUsuarios.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollUsuarios = new JScrollPane(tableUsuarios);
        scrollUsuarios.setBounds(20, 60, 400, 200);
        usuariosPanel.add(scrollUsuarios);

        addUserForm(usuariosPanel, tableUsuariosModel);
    }

    // Formulario para productos
    private void addProductForm(JPanel panel, TablaProductos tableModel) {
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(450, 60, 80, 25);
        panel.add(lblTipo);

        JTextField txtTipo = new JTextField();
        txtTipo.setBounds(530, 60, 150, 25);
        panel.add(txtTipo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(450, 100, 80, 25);
        panel.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(530, 100, 150, 25);
        panel.add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(450, 140, 80, 25);
        panel.add(lblPrecio);

        JTextField txtPrecio = new JTextField();
        txtPrecio.setBounds(530, 140, 150, 25);
        panel.add(txtPrecio);

        JLabel lblExtras = new JLabel("Ingredientes/URL:");
        lblExtras.setBounds(450, 180, 120, 25);
        panel.add(lblExtras);

        JTextField txtExtras = new JTextField();
        txtExtras.setBounds(580, 180, 150, 25);
        panel.add(txtExtras);

        btnAddProducto = new JButton("Agregar Producto");
        btnAddProducto.setBounds(450, 220, 150, 30);
        panel.add(btnAddProducto);

        btnAddProducto.addActionListener(e -> {
            tableModel.addProducto(
                txtTipo.getText(),
                txtNombre.getText(),
                Double.parseDouble(txtPrecio.getText()),
                txtExtras.getText()
            );

            txtTipo.setText("");
            txtNombre.setText("");
            txtPrecio.setText("");
            txtExtras.setText("");
        });
    }

    // Formulario para ingredientes
    private void addIngredientForm(JPanel panel, TablaIngredientes tableIngredientesModel) {
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(450, 328, 80, 25);
        panel.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(530, 328, 150, 25);
        panel.add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(450, 368, 80, 25);
        panel.add(lblPrecio);

        JTextField txtPrecio = new JTextField();
        txtPrecio.setBounds(530, 368, 150, 25);
        panel.add(txtPrecio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(450, 408, 80, 25);
        panel.add(lblStock);

        JTextField txtStock = new JTextField();
        txtStock.setBounds(530, 408, 150, 25);
        panel.add(txtStock);

        JLabel lblUrl = new JLabel("URL:");
        lblUrl.setBounds(450, 448, 80, 25);
        panel.add(lblUrl);

        JTextField txtUrl = new JTextField();
        txtUrl.setBounds(530, 448, 150, 25);
        panel.add(txtUrl);

        btnAddIngrediente = new JButton("Agregar Ingrediente");
        btnAddIngrediente.setBounds(450, 488, 150, 30);
        panel.add(btnAddIngrediente);

        btnAddIngrediente.addActionListener(e -> {
            tableIngredientesModel.addIngrediente(
                txtNombre.getText(),
                Double.parseDouble(txtPrecio.getText()),
                Integer.parseInt(txtStock.getText()),
                txtUrl.getText()
            );

            txtNombre.setText("");
            txtPrecio.setText("");
            txtStock.setText("");
            txtUrl.setText("");
        });
    }

    // Formulario para usuarios
    private void addUserForm(JPanel panel, TablaUsuarios tableUsuariosModel) {
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(450, 60, 80, 25);
        panel.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(530, 60, 150, 25);
        panel.add(txtNombre);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(450, 100, 80, 25);
        panel.add(lblPassword);

        JTextField txtPassword = new JTextField();
        txtPassword.setBounds(530, 100, 150, 25);
        panel.add(txtPassword);
        
        JLabel lblisAdmin = new JLabel("Admin:");
        lblisAdmin.setBounds(450, 140, 80, 25);
        panel.add(lblisAdmin);
        
        JToggleButton tglbtnAdmin = new JToggleButton("Admin");
        tglbtnAdmin.setBounds(530, 140, 150, 25);
        panel.add(tglbtnAdmin);

        btnAddUser = new JButton("Agregar Usuario");
        btnAddUser.setBounds(450, 200, 150, 30);
        panel.add(btnAddUser);

        btnAddUser.addActionListener(e -> {
        	tableUsuariosModel.addUsuario(
        		txtNombre.getText(),
        		txtPassword.getText(),
        		tglbtnAdmin.isSelected()
        		
        	);
            txtNombre.setText("");
            txtPassword.setText("");
            tglbtnAdmin.setSelected(false);
        });
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
        btnPMVolver.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        btnPMVolver.setBounds(10, 10, 85, 21);
        panelMesa.add(btnPMVolver);

        lblMesa = new JLabel("Mesa X");
        lblMesa.setFont(new Font("Segoe Print", Font.PLAIN, 10));
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
        btnCobrar.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        btnCobrar.setBounds(257, 338, 85, 57);
        panelMesa.add(btnCobrar);

        btnQuitarProducto = new JButton("Quitar");
        btnQuitarProducto.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        btnQuitarProducto.setBounds(32, 338, 85, 57);
        panelMesa.add(btnQuitarProducto);

        lblFondoMesa = new JLabel();
        lblFondoMesa.setBounds(0, 0, 373, 405);
        ImageIcon originalMaderaLight = new ImageIcon("resources/img/fondos/maderalight.jpg");
        Image MLEscalada = originalMaderaLight.getImage().getScaledInstance(lblFondoMesa.getWidth(), lblFondoMesa.getHeight(), Image.SCALE_SMOOTH);
        lblFondoMesa.setIcon(new ImageIcon(MLEscalada));
        panelMesa.add(lblFondoMesa);

        panelCarta = new JPanel();
        panelCarta.setOpaque(false);
        panelCarta.setBounds(433, 78, 450, 436);
        mainPanel.add(panelCarta);
        panelCarta.setLayout(null);

        btnCartaEntrantes = new JButton("Entrantes");
        btnCartaEntrantes.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        btnCartaEntrantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnCartaEntrantes.setBackground(new Color(255, 255, 128));
        btnCartaEntrantes.setBounds(0, 0, 105, 82);
        panelCarta.add(btnCartaEntrantes);

        btnCartaPizzas = new JButton("Pizza");
        btnCartaPizzas.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        btnCartaPizzas.setBackground(new Color(255, 255, 128));
        btnCartaPizzas.setBounds(115, 0, 105, 82);
        panelCarta.add(btnCartaPizzas);

        btnCartaPostres = new JButton("Postres");
        btnCartaPostres.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        btnCartaPostres.setBackground(new Color(255, 255, 128));
        btnCartaPostres.setBounds(230, 0, 105, 82);
        panelCarta.add(btnCartaPostres);

        btnCartaBebidas = new JButton("Bebidas");
        btnCartaBebidas.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        btnCartaBebidas.setBackground(new Color(255, 255, 128));
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
        panelIngredientes.setOpaque(false);
        panelIngredientes.setBounds(10, 493, 373, 253);
        mainPanel.add(panelIngredientes);
        panelIngredientes.setLayout(null);

        btnEliminarIngrediente = new JButton("Eliminar");
        btnEliminarIngrediente.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        btnEliminarIngrediente.setForeground(Color.BLACK);
        btnEliminarIngrediente.setBackground(new Color(255, 0, 0));
        btnEliminarIngrediente.setEnabled(false);
        btnEliminarIngrediente.setBounds(10, 175, 96, 49);
        panelIngredientes.add(btnEliminarIngrediente);

        btnAgregarIngrediente = new JButton("Agregar");
        btnAgregarIngrediente.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        btnAgregarIngrediente.setBackground(new Color(128, 255, 128));
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
        lblCantidad.setForeground(Color.WHITE);
        lblCantidad.setFont(new Font("Segoe Print", Font.PLAIN, 10));
        lblCantidad.setBounds(157, 175, 57, 13);
        panelIngredientes.add(lblCantidad);

        lblNumCantidad = new JLabel("0");
        lblNumCantidad.setForeground(Color.WHITE);
        lblNumCantidad.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumCantidad.setBounds(157, 193, 45, 13);
        panelIngredientes.add(lblNumCantidad);

        lblLogo = new JLabel();
        lblLogo.setBounds(433, 531, 450, 239);
        mainPanel.add(lblLogo);
        ImageIcon logoOriginal = new ImageIcon("resources/img/logos/logo.png");
        Image original = logoOriginal.getImage();
        Image logoEscalado = original.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(logoEscalado);
        lblLogo.setIcon(logo);

        lblLetrero = new JLabel();
        lblLetrero.setBounds(96, -34, 161, 145);
        mainPanel.add(lblLetrero);
        ImageIcon originalLetrero = new ImageIcon("resources/img/logos/letrero.png");
        Image letreroOriginal = originalLetrero.getImage();
        Image letreroEscalado = letreroOriginal.getScaledInstance(lblLetrero.getWidth(), lblLetrero.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon letrero = new ImageIcon(letreroEscalado);
        lblLetrero.setIcon(letrero);
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
