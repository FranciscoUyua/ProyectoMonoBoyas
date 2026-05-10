package Sensores;

public class SensorDePresion extends Sensor {

    public SensorDePresion(String id) {
        super(id, "operativo");
    }

    public double obtenerMedicion() {
        // Simulación de medición de presión (en Pascales)
        return 100000 + Math.random() * 50000; // Presión entre 100,000 y 150,000 Pascales
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isActivo() {
        return activo;
    }

}
