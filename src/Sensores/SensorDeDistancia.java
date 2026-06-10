package Sensores;

import java.nio.file.Files;
import java.nio.file.Paths;

public class SensorDeDistancia extends Sensor {
    
    private final String RUTA_ARCHIVO = "distancia.txt";

    public SensorDeDistancia(String id) {
        super(id, "mecanica");
    }

    public double obtenerMedicion() {
        try {
            if (Files.exists(Paths.get(RUTA_ARCHIVO))) {
                String contenido = new String(Files.readAllBytes(Paths.get(RUTA_ARCHIVO)));
                return Double.parseDouble(contenido.trim());
                
            }
        } catch (Exception e) {
            System.err.println("[Aviso] No se pudo leer " + RUTA_ARCHIVO + ". Pasando a simulación...");
        }
        return 15 + Math.random() * 5; // Distancia simulada entre 15 y 20 metros
    }

    @Override
    public String getUnidad() { return "m"; }
}