package Sensores;

public class SensorDeOleaje extends Sensor {
    
    public SensorDeOleaje(String id) {
        super(id, "ambiental");
    }

    @Override
    public double obtenerMedicion() {
        // Simulación: Devuelve un oleaje aleatorio entre 0 y 5 metros
        return Math.random() * 5; 
    }

    @Override
    public String getUnidad() {
        return "m";
    }
}