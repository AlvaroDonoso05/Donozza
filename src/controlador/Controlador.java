package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	
	
	public String[][] llenarMatrizComandas(int idMesa) {
		
		ArrayNode pedidos = (ArrayNode) database.getComandas().get(idMesa).get("pedido");
	
		String[][]comandas = new String[pedidos.size()][3];
		
		 for(int i=0;i<pedidos.size();i++) {
			 comandas[i][0]= pedidos.get(i).get("producto").asText();
			 comandas[i][2]= pedidos.get(i).get("precio").asText();
			 
			 
			 if(pedidos.get("ingredientesExtra")!=null) {
				 ArrayNode ingredientesExtra = (ArrayNode) pedidos.get(i).get("ingredientesExtra");
				 String nombreIngredientes = "";
				 for(int j = 0; j<ingredientesExtra.size(); j++) {
					 nombreIngredientes =  nombreIngredientes + listaIngredientes.getListaIngredientes().get(ingredientesExtra.get(j).asInt()).get("nombre") + ", "; 
				 }
				 comandas[i][1] = nombreIngredientes;
			 }else {
	             comandas[i][1]= pedidos.get(i).get("cantidad").asText();
				 
			 }
         }
		
		return comandas;
		
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
		if(e.getSource()==vista.btnMesa1) {
			vista.lblMesa.setText("MESA 1");
		}
		if(e.getSource()==vista.btnMesa2) {
			vista.lblMesa.setText("MESA 2");
		}
		if(e.getSource()==vista.btnMesa3) {
			vista.lblMesa.setText("MESA 3");
		}
		if(e.getSource()==vista.btnMesa4) {
			vista.lblMesa.setText("MESA 4");
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

	
}
