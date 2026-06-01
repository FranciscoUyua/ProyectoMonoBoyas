package Sensores;

public class Correntometro extends Sensor {
    
    public Correntometro(String id) {
        super(id, "ambiental");
    }

    public double obtenerMedicion() {
        // Futura conexión HTTP a API marina
        return Math.random() * 3; // Velocidad del agua entre 0 y 3 m/s
    }

    @Override
    public String getUnidad() { return "m/s"; }
}