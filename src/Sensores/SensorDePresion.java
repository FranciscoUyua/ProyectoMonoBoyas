package Sensores;

public class SensorDePresion extends Sensor {
    
    public SensorDePresion(String id) {
        super(id, "operativo");
    }

    @Override
    public String getUnidad() {
        return "Pa"; // Pascales
    }

    @Override
    public double obtenerMedicion() {
        // Implementación de ejemplo, devolver un valor simulado
        return 0.0;
    }
}