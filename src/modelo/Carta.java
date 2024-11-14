package modelo;

import java.io.File;

import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import controlador.Logger;

public class Carta {
	
	private static Object object = new Object();
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Logger logger = new Logger();
	
	private JsonNode rootNode;
	private ArrayNode carta;
	private String url;

	public Carta(String url) throws Exception {
		this.url = url;
		actualizarPizzas(false);
	}
	
	public void actualizarPizzas(Boolean generarArchivo) throws Exception {
		synchronized (object) {
			try {
				if(generarArchivo) {
					objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
					objectMapper.writeValue(new File("resources/json/pizzas.json"), rootNode);
				} else {
					rootNode = objectMapper.readTree(new File(this.url));
					carta = (ArrayNode) rootNode.get("pizzas");
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public ArrayNode getListaPizzas() {
		return carta;
	}

	public String getUrl() {
		return url;
	}

	public void deletePizza(int id) {
		Boolean delete = false;
		synchronized (object) {
			try {
				Iterator<JsonNode> iterator = carta.iterator();
				
				while(iterator.hasNext()) {
					JsonNode pizza = iterator.next();
					if(pizza.get("id").asInt() == id) {
						iterator.remove();
						delete = true;
						logger.success("Pizza " + id + " eliminado.");
					}
				}
				
				for(int i = 0; i < carta.size(); i++) {
					((ObjectNode)carta.get(i)).put("id", i + 1);
				}
				
				if(delete) {
					actualizarPizzas(true);
				}
				
			} catch(Exception e) {
				logger.error(e);
			}
		}
	}
	
	/*
	public Boolean addPizza(String nombre, Double precio, Ingredientes ingredientes) {
		Boolean added = false;
		synchronized (object) {
			try {
				for(JsonNode pizza: listaPizzas) {
					if(pizza.get("nombre").asText().equalsIgnoreCase(nombre)) {
						return added;
					}
				}
				
				
				
				ObjectNode pizza = objectMapper.createObjectNode();
				
				pizza.put("id", listaPizzas.size());
				pizza.put("nombre", nombre);
				pizza.put("precio", precio);
				
				ArrayNode ingredientesLista = objectMapper.createArrayNode();
				
				for(ingre)
				
				ObjectNode ingrediente = objectMapper.createObjectNode();
				
				listaPizzas.add(pizza);
				
			} catch(Exception e) {
				logger.error(e.toString());
			}
		}
		
		return added;
	}*/
}
