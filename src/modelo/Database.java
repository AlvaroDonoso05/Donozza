package modelo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;

import controlador.Logger;

public class Database {

    private static final Object object = new Object();

    private final File fileDb;
    private final ObjectMapper mapper = new ObjectMapper();

    private ArrayNode comandas;
    private ArrayNode accounts;

    private JsonNode rootNode;

    private final Logger logger = new Logger();
    private String url;

    public Database(String url) {
        this.url = url;
        this.fileDb = new File(this.url);

        if (!fileDb.exists()) {
            generarEstructura();
        }

        try {
            actualizarDatabase(false);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    public void actualizarDatabase(Boolean generarArchivo) throws Exception {
        try {
            if (generarArchivo) {
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(fileDb, rootNode);
            } else {
                this.rootNode = mapper.readTree(fileDb);
                this.comandas = (ArrayNode) this.rootNode.get("mesas");
                this.accounts = (ArrayNode) this.rootNode.get("usuarios");
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private void generarEstructura() {
        ObjectNode rootNode = mapper.createObjectNode();
        ArrayNode mesas = mapper.createArrayNode();

        logger.log("Generando Bases de Datos");

        for (int i = 0; i < 8; i++) {
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
    }

    public void añadirProducto(int idMesa, JsonNode producto, double precio) {
        synchronized (object) {
            try {
                // Cargamos pedidos desde rootNode para garantizar que la estructura esté sincronizada
                ArrayNode mesas = (ArrayNode) rootNode.get("mesas");
                ObjectNode mesa = (ObjectNode) mesas.get(idMesa);
                ArrayNode pedidos = (ArrayNode) mesa.get("pedido");

                boolean encontrado = false;

                for (int i = 0; i < pedidos.size(); i++) {
                    ObjectNode existingProduct = (ObjectNode) pedidos.get(i);
                    if (producto.get("nombre").asText().equalsIgnoreCase(existingProduct.get("nombre").asText())) {

                        int nuevaCantidad = existingProduct.get("cantidad").asInt() + 1;
                        double nuevoPrecio = existingProduct.get("precio").asDouble() + precio;

                        existingProduct.put("cantidad", nuevaCantidad);
                        existingProduct.put("precio", nuevoPrecio);

                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    ObjectNode productoON = (ObjectNode) producto.deepCopy();
                    productoON.put("cantidad", 1);
                    productoON.remove("url");
                    pedidos.add(productoON);
                }

                double total = 0;
                for (JsonNode item : pedidos) {
                    total += item.get("precio").asDouble();
                }
                mesa.put("total", total);

                actualizarDatabase(true);

            } catch (Exception e) {
                logger.error(e);
            }
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
