package modelo;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
	
	private JsonNode obtenerProducto(int id, String categoria) {
		JsonNode producto = null;
		
		try {
			switch (categoria) {
			case "pizzas":
				producto = (JsonNode) pizzas.get(id);
				break;
			case "bebidas":
				producto = (JsonNode) bebidas.get(id);
				break;
			case "entrantes":
				producto = (JsonNode) entrantes.get(id);
				break;
			case "postres":
				producto = (JsonNode) postres.get(id);
				break;
			default:
				break;
		}
		} catch(Exception e) {
			logger.error(e);
		}
		
		
		return producto;
	}

	public Boolean deleteProducto(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
		Boolean eliminado = false;
		synchronized (object) {
			try {
				JsonNode producto = obtenerProducto(id, categoria);
				
				if(producto != null) {
					switch (categoria) {
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
					logger.success(categoria + " " + id + " eliminado.");
					actualizarCarta(true);
					eliminado = true;
				}
			} catch(Exception e) {
				logger.error(e);
			}
			return eliminado;
		}
	}
	
	public Double getPrecio(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
		JsonNode producto = null;
		
		try {
			producto = obtenerProducto(id, categoria);
		} catch(Exception e) {
			logger.error(e);
		}
		return producto.get("precio").asDouble();
	}
	
	public ArrayList<Integer> getIngredientes(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
		JsonNode producto = null;
		ArrayList<Integer> listaIngredientes = new ArrayList<>();
		
		try {
			producto = obtenerProducto(id, categoria);
			
			for(int i = 0; i < producto.get("ingredientes").size(); i++) {
				listaIngredientes.add(producto.get("ingredientes").get(i).asInt());
			}
		} catch(Exception e) {
			logger.error(e);
		}
		
		return listaIngredientes;
	}
	
	public String getNombre(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
		JsonNode producto = null;
		
		try {
			producto = obtenerProducto(id, categoria);
		} catch(Exception e) {
			logger.error(e);
		}
		return producto.get("nombre").asText();
	}
	
	public Boolean exist(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
		JsonNode producto = null;
		
		try {
			producto = obtenerProducto(id, categoria);
		} catch(Exception e) {
			logger.error(e);
		}
		
		if(producto == null) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public File getImg(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
		JsonNode producto = null;
		File file = null;
		
		try {
			producto = obtenerProducto(id, categoria);
			if(producto != null) {
				file = new File(producto.get("url").asText());
			}
		} catch(Exception e) {
			logger.error(e);
		}
		
		return file;
	}
	
	/*
	public Boolean addProducto(String nombre, Double precio, Ingredientes ingredientes) {
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
