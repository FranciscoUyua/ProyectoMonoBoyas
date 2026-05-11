package Sensores;

public class Anemometro extends Sensor {
    
    public Anemometro(String id) {
        super(id, "ambiental");
    }

    @Override
    public String getUnidad() {
        return "km/h"; // Kilómetros por hora (también podría ser m/s o nudos)
    }

    @Override
    public double obtenerMedicion() {
        // Implementación básica, devolver un valor simulado
        return 10.0; // Ejemplo: 10 km/h
    }
}