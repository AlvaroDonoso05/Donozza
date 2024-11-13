package controlador;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import modelo.Ingredientes;
import modelo.Pizzas;

public class FileWatcher extends Thread {
    private Path filePath;
    private Path directoryPath;
    private Object clase;

    public FileWatcher(Object clase) {
        if (clase instanceof Pizzas) {
            Pizzas listaPizzas = (Pizzas) clase;
            this.filePath = Paths.get(listaPizzas.getUrl());
            this.directoryPath = filePath.getParent();
        } else if (clase instanceof Ingredientes) {
            Ingredientes ingredientes = (Ingredientes) clase;
            this.filePath = Paths.get(ingredientes.getUrl());
            this.directoryPath = filePath.getParent();
        }
        this.clase = clase;
    }

    public void run() {
    	Logger logger = new Logger();
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            
            directoryPath.register(watchService, 
                    StandardWatchEventKinds.ENTRY_CREATE, 
                    StandardWatchEventKinds.ENTRY_MODIFY, 
                    StandardWatchEventKinds.ENTRY_DELETE);

            logger.log("Observando cambios en el directorio: " + directoryPath);

            while (true) {
                WatchKey key = watchService.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> tipo = event.kind();
                    Path modifiedFile = (Path) event.context();

                    if (modifiedFile.getFileName().toString().equals(filePath.getFileName().toString())) {
                        if (tipo == StandardWatchEventKinds.ENTRY_MODIFY) {
                            logger.success("El archivo " + filePath.getFileName() + " ha sido modificado.");

                            if (clase instanceof Pizzas) {
                                Pizzas listaPizzas = (Pizzas) clase;
                                listaPizzas.actualizarPizzas(false);
                            } else if (clase instanceof Ingredientes) {
                                Ingredientes ingredientes = (Ingredientes) clase;
                                ingredientes.actualizarIngredientes(false);
                            }
                        }
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    logger.warning("La clave de monitoreo ya no es válida.");
                    break;
                }

                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
