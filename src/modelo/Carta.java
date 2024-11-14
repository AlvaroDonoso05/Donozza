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
	
	private ArrayNode pizzas;
	private ArrayNode bebidas;
	private ArrayNode entrantes;
	private ArrayNode postres;
	
	private String url;

	public Carta(String url) throws Exception {
		this.url = url;
		actualizarCarta(false);
	}
	
	public void actualizarCarta(Boolean generarArchivo) throws Exception {
		synchronized (object) {
			try {
				if(generarArchivo) {
					objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
					objectMapper.writeValue(new File("resources/json/pizzas.json"), rootNode);
				} else {
					rootNode = objectMapper.readTree(new File(this.url));
					pizzas = (ArrayNode) rootNode.get("pizzas");
					bebidas = (ArrayNode) rootNode.get("bebidas");
					entrantes = (ArrayNode) rootNode.get("entrantes");
					postres = (ArrayNode) rootNode.get("postres");
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public ArrayNode getPizzas() {
		return pizzas;
	}

	public ArrayNode getBebidas() {
		return bebidas;
	}

	public ArrayNode getEntrantes() {
		return entrantes;
	}

	public ArrayNode getPostres() {
		return postres;
	}

	public String getUrl() {
		return url;
	}

	public Boolean deleteProducto(int id, String producto) { // producto (pizzas, bebidas, entrantes, postres)
		Boolean eliminado = false;
		synchronized (object) {
			try {
				ArrayNode categoria = null;
				switch (producto) {
					case "pizzas":
						categoria = (ArrayNode) pizzas.get(id);
						break;
					case "bebidas":
						categoria = (ArrayNode) bebidas.get(id);
						break;
					case "entrantes":
						categoria = (ArrayNode) entrantes.get(id);
						break;
					case "postres":
						categoria = (ArrayNode) postres.get(id);
						break;
					default:
						break;
				}
				
				if(categoria != null) {
					switch (producto) {
						case "pizzas":
							pizzas.remove(id);
							break;
						case "bebidas":
							bebidas.remove(id);
							break;
						case "entrantes":
							entrantes.remove(id);
							break;
						case "postres":
							postres.remove(id);
							break;
					}
					logger.success(producto + " " + id + " eliminado.");
					actualizarCarta(true);
					eliminado = true;
				}
			} catch(Exception e) {
				logger.error(e);
			}
			return eliminado;
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
