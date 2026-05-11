package Sensores;

public class SensorDeTension extends Sensor {
    
    public SensorDeTension(String id) {
        super(id, "mecanica");
    }

    @Override
    public String getUnidad() {
        return "t"; // Toneladas (Fuerza ejercida por el buque sobre la boya)
    }

    @Override
    public double obtenerMedicion() {
        // Implementación de ejemplo, devolver un valor simulado
        return 0.0;
    }
}