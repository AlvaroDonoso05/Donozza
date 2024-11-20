package controlador;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

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
	private boolean firstTime;

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
		this.vista.btnCartaPizzas.addActionListener(this);
		this.vista.btnCartaEntrantes.addActionListener(this);
		this.vista.btnCartaBebidas.addActionListener(this);
		this.vista.btnCartaPostres.addActionListener(this);

		try {
			this.database = new Database();
			this.carta = new Carta("resources/json/carta.json");
			this.listaIngredientes = new Ingredientes("resources/json/ingredientes.json");
			
			FileWatcher watcherPizzas = new FileWatcher(carta);
			FileWatcher watcherIngredientes = new FileWatcher(listaIngredientes);
			
			watcherPizzas.start();
			watcherIngredientes.start();
			
		} catch(Exception e) {
			logger.error(e);
		}

		
	}
	
	
	
	public void cargarEntrantes() {
		int i=0;
		while(carta.exist(i, "entrantes")) {
			i++;
			JButton button = new JButton("Hola");
			button.setPreferredSize(new Dimension(150, 50));
		    button.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            
		        }
		    });
		    vista.btnCartaEntrantes.add(button);
		};
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//BOTONES MESAS
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
			mostrarTabla(4);		}
		if(e.getSource()==vista.btnMesa6) {
			mostrarTabla(5);		}
		if(e.getSource()==vista.btnMesa7) {
			mostrarTabla(6);		}
		if(e.getSource()==vista.btnMesa8) {
			mostrarTabla(7);
	
		}
		if(e.getSource()==vista.btnCartaEntrantes) {
			cargarEntrantes();
		}
		
		//BOTONES SECCIÓN COMANDAS
		if(e.getSource()==vista.btnPMVolver) {
			vista.panel.setVisible(true);
			vista.panelMesa.setVisible(false);
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
					 pedido[0] =   pedidos.get(i).get("producto").asText();
					 String nombreIngredientes = "(";
					 
					 for(int j = 0; j<ingredientesExtra.size(); j++) {
						 if(j == ingredientesExtra.size()-1) {
							 nombreIngredientes =  nombreIngredientes + listaIngredientes.getListaIngredientes().get(ingredientesExtra.get(j).asInt()).get("nombre") + ")"; 
						 }else {
							 nombreIngredientes =  nombreIngredientes + listaIngredientes.getListaIngredientes().get(ingredientesExtra.get(j).asInt()).get("nombre") + ", "; 
						 }
					 }
					 pedido[1] = nombreIngredientes;
							 
				 }else {
					 pedido[0] = pedidos.get(i).get("producto").asText();
					 pedido[1]= pedidos.get(i).get("cantidad").asText();
				 }
				 pedidosLista.add(pedido);
	         }
			

			tP.fireTableDataChanged();
			this.vista.lblMesa.setText("MESA " + (idMesa+1));
			this.vista.panel.setVisible(false);
			this.vista.panelMesa.setVisible(true);
			
		}


	
		//SECCIÓN CARTA
	


}
