package Sensores;

public class Caudalimetro extends Sensor {
    
    public Caudalimetro(String id) {
        super(id, "operativo");
    }

    @Override
    public String getUnidad() {
        return "l/s"; // Litros por segundo
    }

    @Override
    public double obtenerMedicion() {
        // Implementación de ejemplo para un caudalímetro.
        // Ajusta la lógica según los requisitos reales del sensor.
        return 0.0;
    }
}