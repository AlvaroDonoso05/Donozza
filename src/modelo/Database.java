package modelo;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import controlador.Logger;

public class Database {
	private File fileDb;
	private ObjectMapper mapper = new ObjectMapper();
	
	public Database() {
		this.fileDb = new File("resources/db.json");
		if(!fileDb.exists()) {
			generarEstructura();
		}
	}
	
	private void generarEstructura() {
		Logger logger = new Logger();
		ObjectNode rootNode = mapper.createObjectNode();
		ArrayNode mesas = mapper.createArrayNode();
		
		logger.log("Generando Bases de Datos");
		
		for(int i = 0; i < 8; i++) {
			ObjectNode mesaDefault = mapper.createObjectNode();
			mesaDefault.put("id", i + 1);
			mesaDefault.put("ocupado", false);
			
			ArrayNode pedido = mapper.createArrayNode();
			
			/*
			 * Estructura de Pedido
			 * {
			 * 		"pizza": "Carbonara",
			 * 		"ingredientesExtra": [
			 * 			{
			 * 				"nombre": "tomate",
			 * 				"cantidad": 3
			 * 			},
			 * 			{
			 * 				"nombre": "salami",
			 * 				"cantidad": 1
			 * 			}
			 * 		]
			 * 		"precio": (Pizza + Extras)
			 * }
			 * 
			 */
			
			mesaDefault.set("pedido", pedido);
			mesaDefault.put("total", 0);
			
			mesas.add(mesaDefault);
		}
		
		rootNode.set("mesas", mesas);
		
		
		logger.success("Base de datos de Comandas Generada");
	}
}
