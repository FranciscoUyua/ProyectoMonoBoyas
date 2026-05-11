package Sensores;

public class Anemometro extends Sensor {
    
    public Anemometro(String id) {
        super(id, "ambiental");
    }

    @Override
    public double obtenerMedicion() {
        // Futura conexión HTTP a Open-Meteo
        return Math.random() * 80; // Viento entre 0 y 80 km/h
    }

    @Override
    public String getUnidad() { return "km/h"; }
}