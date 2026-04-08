package Sensores;

public class SensorDeOleaje extends Sensor {

    public SensorDeOleaje(String id) {
        super(id, "ambiental");
    }

    @Override
    public double obtenerMedicion() {
        // Simulación de medición de oleaje (en metros)
        return Math.random() * 5; // Oleaje entre 0 y 5 metros
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