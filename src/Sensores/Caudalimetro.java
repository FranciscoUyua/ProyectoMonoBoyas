package Sensores;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Caudalimetro extends Sensor {
    
    private final String RUTA_ARCHIVO = "caudal.txt";

    public Caudalimetro(String id) {
        super(id, "operativo");
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
        return Math.random() * 100; // Caudal simulado entre 0 y 100 l/s
    }

    @Override
    public String getUnidad() { return "l/s"; }
}