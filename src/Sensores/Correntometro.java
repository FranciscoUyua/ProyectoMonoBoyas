package Sensores;

public class Correntometro extends Sensor {
    
    public Correntometro(String id) {
        super(id, "ambiental");
    }

    @Override
    public String getUnidad() {
        return "m/s"; // Metros por segundo
    }

    @Override
    public double obtenerMedicion() {
        // Implementación para obtener la medición de corriente
        return Math.random() * 5.0; // Ejemplo: velocidad aleatoria entre 0 y 5 m/s
    }
}