package Sensores;

public class SensorDeAmarre extends Sensor {
    
    public SensorDeAmarre(String id) {
        super(id, "mecanica");
    }

    @Override
    public String getUnidad() {
        return "m"; // Metros (Distancia entre el barco y la boya)
    }

    @Override
    public double obtenerMedicion() {
        // Implementación de ejemplo, devolver un valor simulado
        return 0.0;
    }
}