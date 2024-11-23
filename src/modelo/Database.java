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
	
	private static Object object = new Object();
	
	private File fileDb;
	private ObjectMapper mapper = new ObjectMapper();
	
	private ArrayNode comandas;
	private ArrayNode accounts;
	
	private JsonNode rootNode;
	
	private Logger logger = new Logger();
	private String usuarioLogged;
	private String url;
	
	public Database(String url) {
		this.url = url;
		this.fileDb = new File(this.url);
		
		if(!fileDb.exists()) {
			generarEstructura();
		}
		
		try {
			actualizarDatabase(false);
		} catch (Exception e) {
			logger.error(e);
		}
        
	}
	
	public void actualizarDatabase(Boolean generarArchivo) throws Exception {
		synchronized (object) {
			try {
				if(generarArchivo) {
					mapper.enable(SerializationFeature.INDENT_OUTPUT);
					mapper.writeValue(new File("resources/json/pizzas.json"), rootNode);
				} else {
					rootNode = mapper.readTree(new File(this.url));
					this.comandas = (ArrayNode) rootNode.get("mesas");
					this.accounts = (ArrayNode) rootNode.get("usuarios");
				}
			} catch (Exception e) {
				logger.error(e);
			}
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
	
	public boolean comprobarUsuario(String username, String password) {
		for (JsonNode account : accounts) {
            if (account.get("name").asText().equalsIgnoreCase(username) && account.get("password").asText().equalsIgnoreCase(password)) {
                return true;
            }
        }
		
		return false;

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



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
