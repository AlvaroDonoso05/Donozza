package controlador;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import modelo.Carta;
import modelo.Database;
import modelo.Ingredientes;
import modelo.TablaPedidos;
import modelo.TablaProductos;
import modelo.TablaUsuarios;
import vista.Vista;

public class Controlador implements ActionListener {

    private final Vista vista;
    private final Logger logger = new Logger();
    private Database database;
    private Carta carta;
    private Ingredientes listaIngredientes;
    private String categoriaActual = "";

    public Controlador(Vista frame) {
        this.vista = frame;
        this.vista.btnPMVolver.addActionListener(this);
        this.vista.btnCartaPizzas.addActionListener(this);
        this.vista.btnCartaEntrantes.addActionListener(this);
        this.vista.btnCartaBebidas.addActionListener(this);
        this.vista.btnCartaPostres.addActionListener(this);
        this.vista.btnIniciarSesion.addActionListener(this);
        this.vista.btnQuitarProducto.addActionListener(this);
        this.vista.btnCobrar.addActionListener(this);
        this.vista.btnCerrarSesion.addActionListener(this);
        this.vista.btnEliminarProducto.addActionListener(this);
        this.vista.btnCerrarSesionProd.addActionListener(this);
        this.vista.btnCerrarSesionUsuarios.addActionListener(this);
        this.vista.btnEliminarProducto.addActionListener(this);
        this.vista.btnModificarProducto.addActionListener(this);
        this.vista.btnAddProducto.addActionListener(this);

        this.vista.btnEliminarUser.addActionListener(this);
        this.vista.btnModificarUser.addActionListener(this);
        this.vista.btnAddUser.addActionListener(this);


        try {
            this.database = new Database("resources/json/db.json");
            this.carta = new Carta("resources/json/carta.json");
            this.listaIngredientes = new Ingredientes("resources/json/ingredientes.json");


            FileWatcher watcherPizzas = new FileWatcher(carta);
            FileWatcher watcherIngredientes = new FileWatcher(listaIngredientes);

            watcherPizzas.start();
            watcherIngredientes.start();

        } catch (Exception e) {
            logger.error(e);
        }

       generarBotonesMesas();
       
       try {
    	// Cargar Productos
    	   TablaProductos modelo = (TablaProductos) this.vista.tableProductos.getModel();
           
           for (JsonNode pizza : carta.getPizzas()) {
               modelo.addProducto("pizza",
                   pizza.get("nombre").asText(),
                   pizza.get("precio").asDouble(),
                   pizza.get("url").asText()
               );
           }
       } catch(Exception e) {
    	   logger.error(e);
       }
       
       try {
       	// Cargar Usuarios
    	   ArrayNode usuarios = database.getAccounts();
              
    	   this.vista.tableUsuariosModel.clearData();
    	   
    	   for (JsonNode userNode : usuarios) {
    	        String nombre = userNode.get("name").asText();
    	        String password = userNode.get("password").asText();
    	        boolean isAdmin = userNode.get("isAdmin").asBoolean();

    	        // Agregar cada usuario al modelo de la tabla
    	        this.vista.tableUsuariosModel.addUsuarioFirst(nombre, password, isAdmin);
    	    }
          } catch(Exception e) {
       	   logger.error(e);
          }

         this.vista.tableUsuarios.updateUI();
    }

    public void generarBotonesMesas() {

        for (int i = 0; i < 8; i++) {
            int mesaId = i;

            JButton btnMesa = new JButton("MESA " + (mesaId + 1), new ImageIcon(database.ocuparLiberarMesa(mesaId).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
            btnMesa.setPreferredSize(new Dimension(150, 150));
            btnMesa.setBackground(new Color(255, 255, 255));
            btnMesa.setFocusPainted(false);
            btnMesa.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mostrarTabla(mesaId);
                    seleccionarMesa();
                }
            });

            btnMesa.setHorizontalTextPosition(SwingConstants.CENTER);
            btnMesa.setVerticalTextPosition(SwingConstants.BOTTOM);
            vista.panelMesas.add(btnMesa);
        }

    }

    public void deseleccionarMesa() {
        this.vista.btnCartaBebidas.setEnabled(false);
        this.vista.btnCartaEntrantes.setEnabled(false);
        this.vista.btnCartaPizzas.setEnabled(false);
        this.vista.btnCartaPostres.setEnabled(false);
        descargarCarta();
    }

    public void seleccionarMesa() {
        this.vista.btnCartaBebidas.setEnabled(true);
        this.vista.btnCartaEntrantes.setEnabled(true);
        this.vista.btnCartaPizzas.setEnabled(true);
        this.vista.btnCartaPostres.setEnabled(true);
    }
    
    public void recargarMesas() {
    	this.vista.panelMesas.removeAll();
    	this.vista.panelMesas.revalidate();
    	this.vista.panelBotonesEntrantes.repaint();
    }

    public void descargarCarta() {
        this.vista.panelBotonesEntrantes.removeAll();
        this.vista.panelBotonesEntrantes.revalidate();
        this.vista.panelBotonesEntrantes.repaint();
    }

    private void gestionarCarta(String categoria) {
        if (categoriaActual.equals(categoria)) {
            descargarCarta();
            categoriaActual = "";
        } else {
            cargarCarta(categoria);
            categoriaActual = categoria;
        }
    }

    public void cargarCarta(String categoria) {
        int i = 0;
        this.vista.panelBotonesEntrantes.removeAll();

        while (carta.exist(i, categoria)) {
            int j = i;
            String nombreProducto = carta.getNombre(i, categoria);
            String urlImagen = carta.getImg(i, categoria).getPath();
            double precioProducto = carta.getPrecio(i, categoria);

            JPanel panelProducto = new JPanel();
            panelProducto.setLayout(new BoxLayout(panelProducto, BoxLayout.Y_AXIS));
            panelProducto.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            panelProducto.setBackground(Color.WHITE);
            panelProducto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelProducto.setMaximumSize(new Dimension(130, 190));

            // Crear etiqueta de imagen
            JLabel etiquetaImagen = new JLabel();
            etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);
            etiquetaImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

            ImageIcon icono = new ImageIcon(new ImageIcon(urlImagen).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            etiquetaImagen.setIcon(icono);

            // Etiqueta Nombre
            JLabel etiquetaNombre = new JLabel(nombreProducto);
            etiquetaNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
            etiquetaNombre.setFont(new Font("Arial", Font.BOLD, 14));

            // Etiqueta Precio
            JLabel etiquetaPrecio = new JLabel(String.format("€ %.2f", precioProducto));
            etiquetaPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);
            etiquetaPrecio.setFont(new Font("Arial", Font.ITALIC, 12));
            etiquetaPrecio.setForeground(Color.GRAY);

            // Boton seleccion producto
            JButton botonSeleccionar = new JButton("Seleccionar");
            botonSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT);
            botonSeleccionar.setPreferredSize(new Dimension(100, 30));
            botonSeleccionar.setMaximumSize(new Dimension(120, 30));
            botonSeleccionar.setBackground(new Color(66, 135, 245));
            botonSeleccionar.setForeground(Color.WHITE);
            botonSeleccionar.setFocusPainted(false);
            botonSeleccionar.setFont(new Font("Arial", Font.PLAIN, 12));

            botonSeleccionar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int idMesa = Integer.parseInt(vista.lblMesa.getText().substring(vista.lblMesa.getText().indexOf(" ") + 1)) - 1;
                    boolean encontrado = false;
                    ArrayNode pizzas = carta.getPizzas();
                    
                    for(int i = 0;i<pizzas.size();i++) {
                    	if(nombreProducto.equalsIgnoreCase(pizzas.get(i).get("nombre").asText())) {
                    		encontrado = true;
                    	}
                    }
                    if(encontrado) {
                    	cargarIngredientes(nombreProducto);
                    	
                    }else {
                    	 database.añadirProducto(idMesa, carta.obtenerProducto(j, categoria), precioProducto);
                         mostrarTabla(idMesa);
                    }
                    recargarMesas();
                    generarBotonesMesas();
   
                }
            });

            panelProducto.add(etiquetaImagen);
            panelProducto.add(Box.createVerticalStrut(5));
            panelProducto.add(etiquetaNombre);
            panelProducto.add(etiquetaPrecio);
            panelProducto.add(Box.createVerticalStrut(10));
            panelProducto.add(botonSeleccionar);

            this.vista.panelBotonesEntrantes.add(panelProducto);

            i++;
        }

        i = 0;

        this.vista.panelBotonesEntrantes.revalidate();
        this.vista.panelBotonesEntrantes.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnCartaEntrantes) {
            gestionarCarta("entrantes");
        }
        if (e.getSource() == vista.btnCartaPizzas) {
            gestionarCarta("pizzas");
        }
        if (e.getSource() == vista.btnCartaPostres) {
            gestionarCarta("postres");
        }
        if (e.getSource() == vista.btnCartaBebidas) {
            gestionarCarta("bebidas");
        }

        if (e.getSource() == vista.btnIniciarSesion) {
            if (database.comprobarUsuario(vista.usernameField.getText(), vista.passwordField.getText())) {
                if(database.isAdmin(vista.usernameField.getText())) {
                	vista.loading.setVisible(false);
                    vista.logIn.setVisible(false);
                    vista.lblwrongPassword.setVisible(false);
                    vista.adminPanel.setVisible(true);
                } else {
                	vista.loading.setVisible(false);
                    vista.logIn.setVisible(false);
                    vista.lblwrongPassword.setVisible(false);
                    vista.mainPanel.setVisible(true);
                }
            } else {
                vista.lblwrongPassword.setVisible(true);

            }

        }
        if(e.getSource() == vista.btnCerrarSesion) {
            vista.mainPanel.setVisible(false);
            vista.logIn.setVisible(true);
            vista.usernameField.setText("");
            vista.passwordField.setText("");
        }
        
        if(e.getSource() == vista.btnCerrarSesionProd || e.getSource() == vista.btnCerrarSesionUsuarios) {
        	vista.logIn.setVisible(true);
        	vista.adminPanel.setVisible(false);
            vista.usernameField.setText("");
            vista.passwordField.setText("");
        }

        //BOTONES SECCIÓN COMANDAS
        if (e.getSource() == vista.btnPMVolver) {
            vista.panelMesas.setVisible(true);
            vista.panelMesa.setVisible(false);
            deseleccionarMesa();
            categoriaActual = "";
        }

        
        if(e.getSource() == this.vista.btnQuitarProducto) {
			int idMesa = Integer.parseInt(vista.lblMesa.getText().substring(vista.lblMesa.getText().indexOf(" ") + 1)) - 1;
			eliminarProducto(idMesa);
			mostrarTabla(idMesa);
		}
        if(e.getSource() == this.vista.btnCobrar) {
        	double total;
        	int idMesa = Integer.parseInt(vista.lblMesa.getText().substring(vista.lblMesa.getText().indexOf(" ") + 1)) - 1;
        	total = database.cobrar(idMesa);
        	mostrarTabla(idMesa);
        	JOptionPane.showMessageDialog(null, "El total es: " + total + "€");
        	recargarMesas();
        	generarBotonesMesas();
        }
        
        
        // Botones de Aministración de Productos
        if (e.getSource() == this.vista.btnEliminarProducto) {
            int selectedRow = this.vista.tableProductos.getSelectedRow();
            if (selectedRow != -1) {
                this.vista.tableProductosModel.removeProducto(selectedRow);
                String categoria = this.vista.cmbCategoriaProducto.getSelectedItem().toString().toLowerCase();
                boolean eliminado = carta.deleteProducto(selectedRow, categoria);
                
                if (eliminado) {
                    logger.success("Producto eliminado correctamente");
                } else {
                    logger.warning("Error al eliminar el producto.");
                }
            }
            
        } else if (e.getSource() == this.vista.btnModificarProducto) {
            int selectedRow = this.vista.tableProductos.getSelectedRow();
            
            if (selectedRow != -1) {
                try {
                    String categoria = this.vista.cmbCategoriaProducto.getSelectedItem().toString().toLowerCase();
                    String nombre = this.vista.txtNombreProducto.getText();
                    double precio = Double.parseDouble(this.vista.txtPrecioProducto.getText());
                    String ingredientesExtra = this.vista.txtExtrasProducto.getText();
                    List<Integer> ingredientes = null;

                    // Actualiza el modelo de la tabla
                    this.vista.tableProductosModel.updateProducto(
                        selectedRow,
                        categoria,
                        nombre,
                        precio,
                        ingredientesExtra
                    );

                    // Procesa ingredientes si es una pizza
                    if (categoria.equals("pizzas")) {
                        if (ingredientesExtra != null && !ingredientesExtra.isEmpty()) {
                            String[] ingredientesTexto = ingredientesExtra.split(",");
                            ingredientes = new ArrayList<>();

                            for (String id : ingredientesTexto) {
                                try {
                                    ingredientes.add(Integer.parseInt(id.trim()));
                                } catch (Exception ex) {
                                    logger.error(ex);
                                    return; // Salir si hay un error en los ingredientes
                                }
                            }
                        } else {
                            logger.warning("Advertencia: No se especificaron ingredientes para la pizza.");
                        }
                    }

                    // Elimina y vuelve a agregar el producto en la carta
                    carta.deleteProducto(selectedRow, categoria);

                    carta.addProducto(categoria, nombre, precio, ingredientes, carta.getPizzas().get(selectedRow).get("url").asText());

                    // Limpia los campos de entrada
                    limpiarCampos();
                    logger.success("Producto modificado correctamente.");
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } else {
                logger.warning("Seleccione un producto para modificar.");
            }
        } else if (e.getSource() == this.vista.btnAddProducto) {
        	String categoria = this.vista.cmbCategoriaProducto.getSelectedItem().toString().toLowerCase();
            String nombre = this.vista.txtNombreProducto.getText();
            double precio = Double.parseDouble(this.vista.txtPrecioProducto.getText());
            String txtExtrasProducto = this.vista.txtExtrasProducto.getText();
            List<Integer> ingredientes = null;
            
            if (categoria.equals("pizzas")) {
                String[] ingredientesTexto = txtExtrasProducto.split(",");
                ingredientes = new ArrayList<>();
                for (String id : ingredientesTexto) {
                    ingredientes.add(Integer.parseInt(id.trim()));
                }
            }
            
            carta.addProducto(categoria, nombre, precio, ingredientes, "resources/img/default.png");
            
            this.vista.tableProductosModel.addProducto(
                categoria,
                nombre,
                precio,
                txtExtrasProducto
            );
            limpiarCampos();
            
            logger.success("Producto añadido correctamente.");
        }
        
        // Botones de Administracion de Usuarios
        if (e.getSource() == this.vista.btnEliminarUser) {
            int selectedRow = this.vista.tableUsuarios.getSelectedRow();
            if (selectedRow != -1) {
                this.vista.tableUsuariosModel.removeUsuario(selectedRow);
                database.eliminarUsuario(selectedRow);
            }
        } else if (e.getSource() == this.vista.btnModificarUser) {
            int selectedRow = this.vista.tableUsuarios.getSelectedRow();
            if (selectedRow != -1) {
                this.vista.tableUsuariosModel.updateUsuario(selectedRow, this.vista.txtNombreUser.getText(), this.vista.txtPasswordUser.getText(), this.vista.tglbtnAdminUser.isSelected());
                database.modificarUsuario(selectedRow, this.vista.txtNombreUser.getText(), this.vista.txtPasswordUser.getText(), this.vista.tglbtnAdminUser.isSelected());
                limpiarCampos();
            }
        } else if (e.getSource() == this.vista.btnAddUser) {
            this.vista.tableUsuariosModel.addUsuarioFirst(this.vista.txtNombreUser.getText(), this.vista.txtPasswordUser.getText(), this.vista.tglbtnAdminUser.isSelected());
            database.añadirUsuario(this.vista.txtNombreUser.getText(), this.vista.txtPasswordUser.getText(), this.vista.tglbtnAdminUser.isSelected());
            limpiarCampos();
        }
    }
    
    private void limpiarCampos() {
        this.vista.txtPrecioProducto.setText("");
        this.vista.txtExtrasProducto.setText("");
        this.vista.txtNombreUser.setText("");
        this.vista.txtPasswordUser.setText("");
        this.vista.tglbtnAdminUser.setSelected(false);
    }


    public void mostrarTabla(int idMesa) {
        //Recuperación del modelo de tabla

        TablaPedidos tP = (TablaPedidos) this.vista.tablaMesa.getModel();
        tP.clearData();

        ArrayNode pedidos = (ArrayNode) database.getComandas().get(idMesa).get("pedido");

        List<String[]> pedidosLista = tP.getPedidos();

        for (int i = 0; i < pedidos.size(); i++) {
            String[] pedido = new String[3];
            pedido[2] = pedidos.get(i).get("precio").asText();

            if (pedidos.get(i).get("ingredientesExtra") != null) {
                ArrayNode ingredientesExtra = (ArrayNode) pedidos.get(i).get("ingredientesExtra");
                pedido[0] = pedidos.get(i).get("nombre").asText();
                String nombreIngredientes = "(";

                for (int j = 0; j < ingredientesExtra.size(); j++) {
                    if (j == ingredientesExtra.size() - 1) {
                        nombreIngredientes = nombreIngredientes + listaIngredientes.getListaIngredientes().get(ingredientesExtra.get(j).asInt()).get("nombre") + ")";
                    } else {
                        nombreIngredientes = nombreIngredientes + listaIngredientes.getListaIngredientes().get(ingredientesExtra.get(j).asInt()).get("nombre") + ", ";
                    }
                }

                pedido[1] = nombreIngredientes;
            } else {
                pedido[0] = pedidos.get(i).get("nombre").asText();
                pedido[1] = pedidos.get(i).get("cantidad").asText();
            }
            pedidosLista.add(pedido);
        }

        tP.fireTableDataChanged();
        this.vista.lblMesa.setText("MESA " + (idMesa + 1));
        this.vista.panelMesas.setVisible(false);
        this.vista.panelMesa.setVisible(true);
    }
    
    public void eliminarProducto(int idMesa){
    	if(this.vista.tablaMesa.getSelectedRow()!=-1) {
    		String nProducto;
    		
    		TablaPedidos tP= (TablaPedidos) this.vista.tablaMesa.getModel();
    		
    		//Obtenemos el nombre del producto seleccionado
    		nProducto = tP.getPedidos().get(this.vista.tablaMesa.getSelectedRow())[0];
    		database.eliminarProducto(idMesa, nProducto);  		
    	}
	}
    
    public void cargarIngredientes(String pizza) {
        this.vista.panelIngredientes.removeAll();
        ArrayNode lIngredientes = listaIngredientes.getListaIngredientes();
        ArrayNode lPizzas = carta.getPizzas();
        ArrayNode iPizza = null;
        
        
        //Obtener IDs de los ingredientes de la pizza en concreto
        for(int j=0;j<lPizzas.size();j++) {
        	if(lPizzas.get(j).get("nombre").asText().equalsIgnoreCase(pizza)) {
        		iPizza = (ArrayNode) lPizzas.get(j).get("ingredientes");
        	}
        }
        for(int i=0;i<iPizza.size();i++) {
        	System.out.println(iPizza.get(i).asInt());
        	String nombreIngrediente = lIngredientes.get(iPizza.get(i).asInt()).get("nombre").asText();
            String urlImagen = lIngredientes.get(iPizza.get(i).asInt()).get("url").asText();
            double precioProducto = lIngredientes.get(iPizza.get(i).asInt()).get("precio").asDouble();

            JPanel panelIngrediente = new JPanel();
            panelIngrediente.setLayout(new BoxLayout(panelIngrediente, BoxLayout.Y_AXIS));
            panelIngrediente.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            panelIngrediente.setBackground(Color.WHITE);
            panelIngrediente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelIngrediente.setMaximumSize(new Dimension(130, 190));
          

            // Crear etiqueta de imagen
            JLabel etiquetaImagen = new JLabel();
            etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);
            etiquetaImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

            ImageIcon icono = new ImageIcon(new ImageIcon(urlImagen).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            etiquetaImagen.setIcon(icono);

            // Etiqueta Nombre
            JLabel etiquetaNombre = new JLabel(nombreIngrediente);
            etiquetaNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
            etiquetaNombre.setFont(new Font("Arial", Font.BOLD, 14));

            // Etiqueta Precio
            JLabel etiquetaPrecio = new JLabel(String.format("€ %.2f", precioProducto));
            etiquetaPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);
            etiquetaPrecio.setFont(new Font("Arial", Font.ITALIC, 12));
            etiquetaPrecio.setForeground(Color.GRAY);

            // Boton seleccion producto
            JButton botonAnadir = new JButton("+");
            botonAnadir.setAlignmentX(Component.CENTER_ALIGNMENT);
            botonAnadir.setPreferredSize(new Dimension(50, 15));
            botonAnadir.setMaximumSize(new Dimension(60, 15));
            botonAnadir.setBackground(new Color(0, 188, 0));
            botonAnadir.setForeground(Color.WHITE);
            botonAnadir.setFocusPainted(false);
            botonAnadir.setFont(new Font("Arial", Font.PLAIN, 12));
            
            JButton botonQuitar = new JButton("-");
            botonQuitar.setAlignmentX(Component.CENTER_ALIGNMENT);
            botonQuitar.setPreferredSize(new Dimension(50, 15));
            botonQuitar.setMaximumSize(new Dimension(60, 15));
            botonQuitar.setBackground(new Color(255, 0, 0));
            botonQuitar.setForeground(Color.WHITE);
            botonQuitar.setFocusPainted(false);
            botonQuitar.setFont(new Font("Arial", Font.PLAIN, 12));
            
            JLabel cantidad = new JLabel("Cantidad");
            cantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
            cantidad.setFont(new Font("Arial", Font.ITALIC, 12));
            cantidad.setForeground(Color.GRAY);
            
            JLabel tCantidad = new JLabel("");
            tCantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
            tCantidad.setFont(new Font("Arial", Font.ITALIC, 12));
            tCantidad.setForeground(Color.GRAY);
            tCantidad.setText("8");
            

            botonAnadir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int idMesa = Integer.parseInt(vista.lblMesa.getText().substring(vista.lblMesa.getText().indexOf(" ") + 1)) - 1;
                    boolean encontrado = false;
                    ArrayNode pizzas = carta.getPizzas();
                    
                    recargarMesas();
                    generarBotonesMesas();
   
                }
            });

            panelIngrediente.add(etiquetaImagen);
            panelIngrediente.add(Box.createVerticalStrut(5));
            panelIngrediente.add(etiquetaNombre);
            panelIngrediente.add(etiquetaPrecio);
            panelIngrediente.add(Box.createVerticalStrut(10));
            panelIngrediente.add(botonAnadir);
            panelIngrediente.add(botonQuitar);
            panelIngrediente.add(cantidad);
            panelIngrediente.add(tCantidad);

            this.vista.panelIngredientes.add(panelIngrediente);
        }


        this.vista.panelIngredientes.revalidate();
        this.vista.panelIngredientes.repaint();
    }
    
    private void cargarProductosEnTabla() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.vista.tableProductos.getModel();
            modelo.setRowCount(0);

            for (JsonNode pizza : carta.getPizzas()) {
                modelo.addRow(new Object[]{
                    pizza.get("id").asInt(),
                    pizza.get("nombre").asText(),
                    pizza.get("precio").asDouble(),
                    pizza.get("url").asText()
                });
            }

            // Similar para otras categorías (bebidas, entrantes, postres)
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
