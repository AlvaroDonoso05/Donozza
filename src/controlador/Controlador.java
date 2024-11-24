package controlador;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.fasterxml.jackson.databind.node.ArrayNode;

import modelo.Database;
import modelo.Ingredientes;
import modelo.TablaPedidos;
import modelo.Carta;
import vista.Vista;

public class Controlador implements ActionListener{
	
	private Vista vista;
	private Logger logger = new Logger();
	private Database database;
	private Carta carta;
	private Ingredientes listaIngredientes;
	private String categoriaActual = "";

	public Controlador(Vista frame) {
		this.vista=frame;
		this.vista.btnPMVolver.addActionListener(this);
		this.vista.btnCartaPizzas.addActionListener(this);
		this.vista.btnCartaEntrantes.addActionListener(this);
		this.vista.btnCartaBebidas.addActionListener(this);
		this.vista.btnCartaPostres.addActionListener(this);
		this.vista.btnIniciarSesion.addActionListener(this);
		this.vista.btnQuitarProducto.addActionListener(this);


		try {
			this.database = new Database("resources/json/db.json");
			this.carta = new Carta("resources/json/carta.json");
			this.listaIngredientes = new Ingredientes("resources/json/ingredientes.json");

			
			FileWatcher watcherPizzas = new FileWatcher(carta);
			FileWatcher watcherIngredientes = new FileWatcher(listaIngredientes);
			
			watcherPizzas.start();
			watcherIngredientes.start();
			
		} catch(Exception e) {
			logger.error(e);
		}
		
		generarBotonesMesas();

		
	}
	
	public void generarBotonesMesas() {
		ImageIcon mesaIcon = new ImageIcon("resources/img/mesa.png");
		
		for(int i = 0; i < 8; i++) {
			int mesaId = i;
			
			JButton btnMesa = new JButton("MESA " + (mesaId + 1), new ImageIcon(mesaIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
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
	            	database.añadirProducto(idMesa, carta.obtenerProducto(j, categoria), precioProducto);
	            	mostrarTabla(idMesa);
	            	
	            	//database.añadirProducto(, null);
	                
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
		
		if(e.getSource()==vista.btnCartaEntrantes) {
			 gestionarCarta("entrantes");
		}
		if(e.getSource()==vista.btnCartaPizzas) {
			 gestionarCarta("pizzas");
		}
		if(e.getSource()==vista.btnCartaPostres) {
			 gestionarCarta("postres");
		}
		if(e.getSource()==vista.btnCartaBebidas) {
			 gestionarCarta("bebidas");
		}
		
		if(e.getSource() == vista.btnIniciarSesion) {
			if(database.comprobarUsuario(vista.usernameField.getText(), vista.passwordField.getText())) {
				vista.loading.setVisible(false);
				vista.logIn.setVisible(false);
				vista.lblwrongPassword.setVisible(false);
				vista.mainPanel.setVisible(true);
			}else {
				vista.lblwrongPassword.setVisible(true);
			}

		}
		
		if(e.getSource() == this.vista.btnQuitarProducto) {
			int idMesa = Integer.parseInt(vista.lblMesa.getText().substring(vista.lblMesa.getText().indexOf(" ") + 1)) - 1;
			eliminarProducto(idMesa);
			mostrarTabla(idMesa);
		}
		
		//BOTONES SECCIÓN COMANDAS
		if(e.getSource()==vista.btnPMVolver) {
			vista.panelMesas.setVisible(true);
			vista.panelMesa.setVisible(false);
			deseleccionarMesa();
		}
		
	}
	
	public void mostrarTabla (int idMesa) {
		//Recuperación del modelo de tabla
			
		TablaPedidos tP= (TablaPedidos) this.vista.tablaMesa.getModel();
		tP.clearData();
			
		ArrayNode pedidos = (ArrayNode) database.getComandas().get(idMesa).get("pedido");
			
		List pedidosLista = tP.getPedidos();
			
		 for(int i=0;i<pedidos.size();i++) {
			String [] pedido = new String[3];
			pedido [2] = pedidos.get(i).get("precio").asText();
				
			if(pedidos.get(i).get("ingredientesExtra")!=null) {
				ArrayNode ingredientesExtra = (ArrayNode) pedidos.get(i).get("ingredientesExtra");
				pedido[0] =   pedidos.get(i).get("nombre").asText();
				String nombreIngredientes = "(";
					 
				for(int j = 0; j<ingredientesExtra.size(); j++) {
					if(j == ingredientesExtra.size()-1) {
						nombreIngredientes =  nombreIngredientes + listaIngredientes.getListaIngredientes().get(ingredientesExtra.get(j).asInt()).get("nombre") + ")"; 
					}else {
						nombreIngredientes =  nombreIngredientes + listaIngredientes.getListaIngredientes().get(ingredientesExtra.get(j).asInt()).get("nombre") + ", "; 
					}
				}
				
				pedido[1] = nombreIngredientes;			 
				 } else {
					 pedido[0] = pedidos.get(i).get("nombre").asText();
					 pedido[1]= pedidos.get(i).get("cantidad").asText();
				 }
				 pedidosLista.add(pedido);
	         }
			
			tP.fireTableDataChanged();
			this.vista.lblMesa.setText("MESA " + (idMesa+1));
			this.vista.panelMesas.setVisible(false);
			this.vista.panelMesa.setVisible(true);
		}
	
	
	public void eliminarProducto(int idMesa){
		String nProducto;
		
		TablaPedidos tP= (TablaPedidos) this.vista.tablaMesa.getModel();
		
		//Obtenemos la información completa del pedido seleccionado
		nProducto = tP.getPedidos().get(this.vista.tablaMesa.getSelectedRow())[0];
		database.eliminarProducto(idMesa, nProducto);
		
	}

	
		//SECCIÓN CARTA
	


}
