package Sensores;

import java.nio.file.Files;
import java.nio.file.Paths;

public class SensorDeTension extends Sensor {
    
    private final String RUTA_ARCHIVO = "tension.txt";

    public SensorDeTension(String id) {
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
        return 50 + Math.random() * 20; // Tensión simulada entre 50 y 70 toneladas
    }

    @Override
    public String getUnidad() { return "t"; }
}