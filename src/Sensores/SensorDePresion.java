package Sensores;

public class SensorDePresion extends Sensor {

    public SensorDePresion(String id) {
        super(id, "operativo");
    }

    @Override
    public double obtenerMedicion() {
        // Simulación de medición de presión (en Pascales)
        return 100000 + Math.random() * 50000; // Presión entre 100,000 y 150,000 Pascales
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public boolean isActivo() {
        return activo;
    }

}
