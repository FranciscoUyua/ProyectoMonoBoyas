package Sensores.Providers;

import Sensores.ISensorDataProvider;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ArchivoDataProvider implements ISensorDataProvider {
    
    private final String rutaArchivo;

    // Constructor que recibe qué archivo debe leer
    public ArchivoDataProvider(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public double obtenerDato() throws Exception {
        if (Files.exists(Paths.get(rutaArchivo))) {
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            return Double.parseDouble(contenido.trim());
        } else {
            throw new Exception("Archivo no encontrado: " + rutaArchivo);
        }
    }
}