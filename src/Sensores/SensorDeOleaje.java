package Sensores;

public class SensorDeOleaje extends Sensor {

    public SensorDeOleaje(String id) {
        super(id, "ambiental");
    }

    @Override
    public double obtenerMedicion() {
        // Simulación de medición de oleaje (en metros)
        // Nota: Aquí es donde se conectaría la lectura de la API de Open-Meteo a futuro
        return Math.random() * 5; // Oleaje entre 0 y 5 metros
    }

    @Override
    public String getUnidad() {
        return "m"; // Metros
    }
}