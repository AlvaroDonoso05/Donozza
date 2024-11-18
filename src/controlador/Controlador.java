package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import controlador.Logger;
import modelo.Database;
import modelo.Ingredientes;
import modelo.Pizzas;
import vista.Vista;

public class Controlador implements ActionListener{
	
	private Vista vista;
	private Logger logger = new Logger();
	private Database database;
	private Pizzas listaPizzas;
	private Ingredientes listaIngredientes;
	private String [] nombreColumnas = {"Nombre","Cantidad","Precio"};
	private String [][] prueba = new String[][]{{"hola", "adios", "hola"},
			{"hola", "adios", "hola"}};
			
	private boolean firstTime = true;


	public Controlador(Vista frame) {
		this.vista=frame;
		this.vista.btnMesa1.addActionListener(this);
		this.vista.btnMesa2.addActionListener(this);
		this.vista.btnMesa3.addActionListener(this);
		this.vista.btnMesa4.addActionListener(this);
		this.vista.btnMesa5.addActionListener(this);
		this.vista.btnMesa6.addActionListener(this);
		this.vista.btnMesa7.addActionListener(this);
		this.vista.btnMesa8.addActionListener(this);
		this.vista.btnPMVolver.addActionListener(this);
		

		try {
			this.database = new Database();
			this.listaPizzas = new Pizzas("resources/json/pizzas.json");
			this.listaIngredientes = new Ingredientes("resources/json/ingredientes.json");
			
			FileWatcher watcherPizzas = new FileWatcher(listaPizzas);
			FileWatcher watcherIngredientes = new FileWatcher(listaIngredientes);
			
			watcherPizzas.start();
			watcherIngredientes.start();
			
		} catch(Exception e) {
			logger.error(e);
		}

		
	}
	
	/*Lee el fichero db.json y guarda todos los pedidos en el ArrayNode "pedidos"
	 * Con un bucle se obtiene cada pedido (pedidos.get(i)), que corresponde por orden a su mesa
	 * Si el pedido es una pizza tendrá ingredientesExtra, por lo que se procesará de una forma
	 * El resto de productos se procesarán de la misma forma
	 * 
	 * */
	public String[][] llenarMatrizComandas(int idMesa) {
		
		ArrayNode pedidos = (ArrayNode) database.getComandas().get(idMesa).get("pedido");
	
		this.vista.comandas = new String[pedidos.size()][3];
		
		 for(int i=0;i<pedidos.size();i++) {
			 
			this.vista.comandas[i][2]= pedidos.get(i).get("precio").asText();
			
			 if(pedidos.get(i).get("ingredientesExtra")!=null) {
		
				 ArrayNode ingredientesExtra = (ArrayNode) pedidos.get(i).get("ingredientesExtra");
				 String nombreIngredientes =   pedidos.get(i).get("producto").asText() + " (";
				 
				 for(int j = 0; j<ingredientesExtra.size(); j++) {
					 if(j == ingredientesExtra.size()-1) {
						 nombreIngredientes =  nombreIngredientes + listaIngredientes.getListaIngredientes().get(ingredientesExtra.get(j).asInt()).get("nombre") + ")"; 
					 }else {
						 nombreIngredientes =  nombreIngredientes + listaIngredientes.getListaIngredientes().get(ingredientesExtra.get(j).asInt()).get("nombre") + ", "; 
					 }
				 }
				 this.vista.comandas[i][0] = nombreIngredientes;
				 this.vista.comandas[i][1] = "1";
						 
			 }else {
				 this.vista.comandas[i][0] = pedidos.get(i).get("producto").asText();
				 this.vista.comandas[i][1]= pedidos.get(i).get("cantidad").asText();
			 }
			 
         }
		
		return this.vista.comandas;
		  
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//BOTONES MESAS
		//Recupera el JTable y JScrollPane, llama al método generar pedido y lo añade a la tabla
		if(e.getSource()==vista.btnMesa1) {
			mostrarTabla(0);
		}
		if(e.getSource()==vista.btnMesa2) {
			mostrarTabla(1);
		}
		if(e.getSource()==vista.btnMesa3) {
			mostrarTabla(2);
		}
		if(e.getSource()==vista.btnMesa4) {
			mostrarTabla(3);
		}
		if(e.getSource()==vista.btnMesa5) {
			vista.lblMesa.setText("MESA 5");
		}
		if(e.getSource()==vista.btnMesa6) {
			vista.lblMesa.setText("MESA 6");
		}
		if(e.getSource()==vista.btnMesa7) {
			vista.lblMesa.setText("MESA 7");
		}
		if(e.getSource()==vista.btnMesa8) {
			vista.panel.setVisible(false);
			vista.panelMesa.setVisible(true);
			vista.lblMesa.setText("MESA 8");
		}
		
		//BOTONES SECCIÓN COMANDAS
		if(e.getSource()==vista.btnPMVolver) {
			vista.panel.setVisible(true);
			vista.panelMesa.setVisible(false);
		}
		
		
	}

	public void mostrarTabla (int idMesa) {
		//Creación estructuras a usar
		String [][] pedidos = llenarMatrizComandas(idMesa);
		String [] producto = new String[3];
		DefaultTableModel dtm = (DefaultTableModel) this.vista.tablaMesa.getModel();
		
		//Resetear Tablas
		dtm.setRowCount(0);
		
		//La primera vez se añaden las columnas
		if(firstTime) {
			dtm.addColumn("Producto");
			dtm.addColumn("Informacion");
			dtm.addColumn("Precio");
			firstTime = false;
		}
		
		//Rellenar tabla con los nuevos datos
		for(int i = 0; i < pedidos.length; i++) {
			for(int j = 0; j < pedidos[i].length; j++) {
			 producto[j] = pedidos[i][j];
			 }
			 dtm.addRow(producto);
			 }
		dtm.fireTableDataChanged();

	
		vista.lblMesa.setText("MESA " + (idMesa+1));
		vista.panel.setVisible(false);
		vista.panelMesa.setVisible(true);
		
	}
	
}
