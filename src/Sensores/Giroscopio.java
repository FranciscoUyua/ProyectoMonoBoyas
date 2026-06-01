package Sensores;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Giroscopio extends Sensor {
    
    private final String RUTA_ARCHIVO = "giroscopio.txt";

    public Giroscopio(String id) {
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
        return -5 + Math.random() * 10; // Ángulo simulado entre -5 y +5 grados
    }

    @Override
    public String getUnidad() { return "grados"; }
}