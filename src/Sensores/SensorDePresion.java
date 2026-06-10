package Sensores;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class SensorDePresion extends Sensor {
    
    private final String RUTA_ARCHIVO = "presion.txt";

    public SensorDePresion(String id) {
        super(id, "operativo");
    }

    public double obtenerMedicion() {
        // Intenta leer el archivo presion.txt que creaste en la raíz
        try {
            if (Files.exists(Paths.get(RUTA_ARCHIVO))) {
                String contenido = new String(Files.readAllBytes(Paths.get(RUTA_ARCHIVO)));
                return Double.parseDouble(contenido.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("[Aviso] No se pudo leer presion.txt. Pasando a simulación...");
        }

        // Si falla, devuelve un valor aleatorio
        return 100000 + Math.random() * 50000; 
    }

    @Override
    public String getUnidad() {
        return "Pa";
    }
}