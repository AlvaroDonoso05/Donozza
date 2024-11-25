package modelo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controlador.Logger;

public class Carta {

    private static final Object object = new Object();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = new Logger();

    private JsonNode rootNode;

    private ArrayNode pizzas;
    private ArrayNode bebidas;
    private ArrayNode entrantes;
    private ArrayNode postres;

    private final String url;

    public Carta(String url) throws Exception {
        this.url = url;
        actualizarCarta(false);
    }

    public void actualizarCarta(Boolean generarArchivo) throws Exception {
        synchronized (object) {
            try {
                if (generarArchivo) {
                    mapper.enable(SerializationFeature.INDENT_OUTPUT);
                    mapper.writeValue(new File("resources/json/carta.json"), rootNode);
                } else {
                    rootNode = mapper.readTree(new File(this.url));
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

    public JsonNode obtenerProducto(int id, String categoria) {
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
        } catch (Exception e) {
            logger.error(e);
        }


        return producto;
    }

    public Boolean deleteProducto(int id, String categoria) {
        Boolean eliminado = false;
        synchronized (object) {
            try {
                ArrayNode categoriaArray = obtenerCategoriaArray(categoria);
                if (categoriaArray != null && id >= 0 && id < categoriaArray.size()) {
                    categoriaArray.remove(id);
                    logger.success("Producto eliminado de " + categoria + ": ID " + id);
                    actualizarCarta(true);
                    eliminado = true;
                } else {
                    logger.warning("El índice " + id + " no es válido para la categoría " + categoria);
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return eliminado;
    }

    public Double getPrecio(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
        JsonNode producto = null;

        try {
            producto = obtenerProducto(id, categoria);
        } catch (Exception e) {
            logger.error(e);
        }
        return producto.get("precio").asDouble();
    }

    public ArrayList<Integer> getIngredientes(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
        JsonNode producto = null;
        ArrayList<Integer> listaIngredientes = new ArrayList<>();

        try {
            producto = obtenerProducto(id, categoria);

            for (int i = 0; i < producto.get("ingredientes").size(); i++) {
                listaIngredientes.add(producto.get("ingredientes").get(i).asInt());
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return listaIngredientes;
    }

    public String getNombre(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
        JsonNode producto = null;

        try {
            producto = obtenerProducto(id, categoria);
        } catch (Exception e) {
            logger.error(e);
        }
        return producto.get("nombre").asText();
    }

    public Boolean exist(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
        JsonNode producto = null;

        try {
            producto = obtenerProducto(id, categoria);
        } catch (Exception e) {
            logger.error(e);
        }

        return producto != null;

    }

    public File getImg(int id, String categoria) { // producto (pizzas, bebidas, entrantes, postres)
        JsonNode producto = null;
        File file = null;

        try {
            producto = obtenerProducto(id, categoria);
            if (producto != null) {
                file = new File(producto.get("url").asText());
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return file;
    }

    public void addProducto(String categoria, String nombre, Double precio, List<Integer> ingredientes, String url) {
        synchronized (object) {
            try {
                ArrayNode categoriaArray = obtenerCategoriaArray(categoria);
                if (categoriaArray == null) {
                    logger.warning("Categoría '" + categoria + "' no encontrada.");
                    return;
                }

                for (JsonNode producto : categoriaArray) {
                    if (producto.get("nombre").asText().equalsIgnoreCase(nombre)) {
                        logger.warning("El producto con nombre '" + nombre + "' ya existe en la categoría '" + categoria + "'.");
                        return;
                    }
                }

                ObjectNode producto = mapper.createObjectNode();

                producto.put("nombre", nombre);
                producto.put("precio", precio);
                producto.put("url", url);

                // Si la categoría es "pizzas", agregar la lista de ingredientes
                if (categoria.equalsIgnoreCase("pizzas") && ingredientes != null) {
                    ArrayNode ingredientesLista = mapper.createArrayNode();
                    for (Integer ingredienteId : ingredientes) {
                        ingredientesLista.add(ingredienteId);
                    }
                    producto.set("ingredientes", ingredientesLista);
                }

                categoriaArray.add(producto);
                actualizarCarta(true);


                logger.success("Producto '" + nombre + "' añadido correctamente en la categoría '" + categoria + "'.");
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    private ArrayNode obtenerCategoriaArray(String categoria) {
        switch (categoria.toLowerCase()) {
            case "pizzas":
                return pizzas;
            case "bebidas":
                return bebidas;
            case "entrantes":
                return entrantes;
            case "postres":
                return postres;
            default:
                return null;
        }
    }

}
