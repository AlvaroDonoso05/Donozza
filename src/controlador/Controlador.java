package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

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
		
        //String[][] comandas = new String[this.comandasMesa.size()][3];
	
        
        /*try {
            for(int i=0;i<this.comandasMesa.size();i++) {
                comandas[i][0]=this.comandasMesa.get(i).getNombre();
                comandas[i][1]=String.valueOf(this.comandasMesa.get(i).getCantidad());
                comandas[i][2]=String.valueOf(this.comandasMesa.get(i).getPrecio());
            }
        }catch(Exception e) {
            throw e;
        }
        return comandas;
     */   
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
		this.vista.tablaMesa = new JTable(llenarMatrizComandas(idMesa), this.vista.nombreColumnas);
		vista.lblMesa.setText("MESA " + (idMesa+1));
		this.vista.tablaMesa.updateUI();
		vista.panel.setVisible(false);
		vista.panelMesa.setVisible(true);
		this.vista.tablaMesa.setBounds(22, 77, 320, 251);
		this.vista.panelMesa.add(this.vista.tablaMesa);
		
		this.vista.scrollPane = new JScrollPane(this.vista.tablaMesa);
		this.vista.scrollPane.setBounds(22, 77, 320, 251);
		this.vista.panelMesa.add(this.vista.scrollPane);
	}
	
}
