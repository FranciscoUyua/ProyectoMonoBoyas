package Sensores;

public class Giroscopio extends Sensor {
    
    public Giroscopio(String id) {
        super(id, "mecanica");
    }

    @Override
    public String getUnidad() {
        return "grados"; // Ángulo de inclinación o rotación
    }

    @Override
    public double obtenerMedicion() {
        // Implementación de ejemplo, devolver un valor simulado
        return 0.0;
    }
}