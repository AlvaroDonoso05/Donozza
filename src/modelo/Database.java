package modelo;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
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

	public void añadirProducto (int idMesa, JsonNode producto, double precio) {
		JsonNode rootNode;
		boolean encontrado = false;
		try {
			ObjectNode productoON = (ObjectNode) producto;
			productoON.remove("url");
			rootNode = mapper.readTree(fileDb);
			ArrayNode mesas = mapper.createArrayNode();
			mesas = (ArrayNode) rootNode.get("mesas");
			ArrayNode pedidos = (ArrayNode) mesas.get(idMesa).get("pedido");
		
			for(int i = 0; i<pedidos.size();i++) {
				if(producto.get("nombre").asText().equalsIgnoreCase(pedidos.get(i).get("nombre").asText())) {
					productoON.put("cantidad", producto.get("cantidad").asInt() + 1) ;
					productoON.put("precio", precio * productoON.get("cantidad").asDouble());
					encontrado = true;
					pedidos.remove(i);
					i--;
				}					
			}
			if(!encontrado) {
				productoON.put("cantidad", 1);
			}
			pedidos.add(productoON);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.writeValue(fileDb, rootNode);
			encontrado = false;
			
		} catch (JsonProcessingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public ArrayNode getComandas() {
		return comandas;
	}

}
