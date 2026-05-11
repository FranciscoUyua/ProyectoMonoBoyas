package Sensores;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class SensorDePresion extends Sensor {

    // Ruta del archivo para simular o inyectar valores fijos de presión
    private final String RUTA_ARCHIVO = "presion.txt";

    public SensorDePresion(String id) {
        super(id, "operativo");
    }

    @Override
    public double obtenerMedicion() {
        // Intento de lectura desde archivo .txt para forzar valores rápidamente
        try {
            if (Files.exists(Paths.get(RUTA_ARCHIVO))) {
                String contenido = new String(Files.readAllBytes(Paths.get(RUTA_ARCHIVO)));
                return Double.parseDouble(contenido.trim());
            }
        } catch (IOException | NumberFormatException e) {
            // Si hay un error al leer o el formato es incorrecto, lo informamos silenciosamente
            System.err.println("[Aviso] No se pudo leer presion.txt, pasando a simulación...");
        }

        // Fallback: Si el archivo no existe o falló, usamos tu simulación matemática original
        return 100000 + Math.random() * 50000; // Presión entre 100,000 y 150,000 Pascales
    }

    @Override
    public String getUnidad() {
        return "Pa"; // Pascales
    }
}