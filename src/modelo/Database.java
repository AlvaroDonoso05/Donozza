package modelo;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import controlador.Logger;

public class Database {
	private File fileDb;
	private ObjectMapper mapper = new ObjectMapper();
	private ArrayNode comandas;
	private Logger logger = new Logger();
	
	public Database() {
		this.fileDb = new File("resources/db.json");
		if(!fileDb.exists()) {
			generarEstructura();
		}
		
		 JsonNode rootNode;
		try {
			rootNode = mapper.readTree(fileDb);
			this.comandas = (ArrayNode) rootNode.get("mesas");
		} catch (Exception e) {
			logger.error(e);
		}
        
	}
		
	
	
	private void generarEstructura() {
		ObjectNode rootNode = mapper.createObjectNode();
		ArrayNode mesas = mapper.createArrayNode();
		
		logger.log("Generando Bases de Datos");
		
		for(int i = 0; i < 8; i++) {
			ObjectNode mesaDefault = mapper.createObjectNode();
			mesaDefault.put("ocupado", false);
			
			ArrayNode pedido = mapper.createArrayNode();
			
			/*
			 * Estructura de Pedido
			 * {
			 * 		"producto": "Carbonara",
			 * 		"ingredientesExtra": [1,3,4],
			 * 		"precio": (Pizza + Extra)
			 * }
			 *  {
			 * 		"producto": "Fanta",
			 * 		"Cantidad": 2,
			 * 		"precio": Precio* cantidad

			 * }
			 * 
			 */
			
			mesaDefault.set("pedido", pedido);
			mesaDefault.put("total", 0);
			
			mesas.add(mesaDefault);
		}
		
		ArrayNode usuarios = mapper.createArrayNode();
		ObjectNode userDefault = mapper.createObjectNode();
		userDefault.put("name", "admin");
		userDefault.put("password", "admin");
		userDefault.put("isAdmin", true);
		
		usuarios.add(userDefault);
		
		rootNode.set("mesas", mesas);
		rootNode.set("usuarios", usuarios);
		
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(fileDb, rootNode);
		} catch (Exception e) {
			logger.error(e);
		}
		
		logger.success("Base de datos de Comandas Generada");
	}


	public ArrayNode getComandas() {
		return comandas;
	}

}
