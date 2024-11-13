package modelo;

import java.io.File;

import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import controlador.Logger;

public class Ingredientes {
	
	private static Object object = new Object();
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Logger logger = new Logger();
	
	private JsonNode rootNode;
	private ArrayNode listaIngredientes;
	private String url;

	public Ingredientes(String url) {
		this.url = url;
		actualizarIngredientes(false);
	}
	
	public void actualizarIngredientes(boolean generarArchivo) {
		synchronized (object) {
			try {
				if(generarArchivo) {
					objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
					objectMapper.writeValue(new File("resources/json/ingredientes.json"), rootNode);
				} else {
					rootNode = objectMapper.readTree(new File(this.url));
					listaIngredientes = (ArrayNode) rootNode.get("ingredientes");
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public ArrayNode getListaIngredientes() {
		return listaIngredientes;
	}
	
	public String getUrl() {
		return url;
	}

	public Boolean deleteIngrediente(int id) {
		Boolean delete = false;
		synchronized (object) {
			try {
				Iterator<JsonNode> iterator = listaIngredientes.iterator();
				
				while(iterator.hasNext()) {
					JsonNode ingrediente = iterator.next();
					if(ingrediente.get("id").asInt() == id) {
						iterator.remove();
						delete = true;
						logger.success("Ingrediente " + id + " eliminado.");
					}
				}
				
				for(int i = 0; i < listaIngredientes.size(); i++) {
					((ObjectNode)listaIngredientes.get(i)).put("id", i + 1);
				}
				
				actualizarIngredientes(true);
				
			} catch(Exception e) {
				logger.error(e);
			}
			
			
			return delete;
		}
	}
	
	public void setStock(int id, int cantidad) {
		for(JsonNode ingrediente: listaIngredientes) {
			if(ingrediente.get("id").asInt() == id) {
				((ObjectNode) ingrediente).put("stock", cantidad);
				logger.success("Stock Cambiado al ingrediente (" + ingrediente.get("nombre") + ") : " + ingrediente.get("stock").asInt());
				actualizarIngredientes(true);
			}
		}
	}
	
	public void addStock(int id, int cantidad) {
		for(JsonNode ingrediente: listaIngredientes) {
			if(ingrediente.get("id").asInt() == id) {
				((ObjectNode) ingrediente).put("stock", ingrediente.get("stock").asInt() + cantidad);
				logger.success("Stock Cambiado al ingrediente (" + ingrediente.get("nombre") + ") : " + ingrediente.get("stock").asInt());
				actualizarIngredientes(true);
			}
		}
	}
	
	
}
