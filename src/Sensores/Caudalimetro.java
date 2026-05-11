package Sensores;

public class Caudalimetro extends Sensor {
    public Caudalimetro(String id) {
        super(id, "operativo");
    }

    @Override
    public double obtenerMedicion() {
        return Math.random() * 100; // Simulación: 0 a 100 l/s
    }

    @Override
    public String getUnidad() {
        return "l/s"; // Litros por segundo
    }
}

class SensorDeOleaje extends Sensor {
    public SensorDeOleaje(String id) {
        super(id, "ambiental");
    }

    @Override
    public double obtenerMedicion() {
        return Math.random() * 5; // Simulación: 0 a 5 metros
    }

    @Override
    public String getUnidad() {
        return "m"; // Metros
    }
}

class SensorDePresion extends Sensor {
    public SensorDePresion(String id) {
        super(id, "operativo");
    }

    @Override
    public double obtenerMedicion() {
        // En lugar de Random, aquí podríamos aplicar la API de lectura de TXT (explicado más abajo)
        return 100000 + Math.random() * 50000; 
    }

    @Override
    public String getUnidad() {
        return "Pa"; // Pascales
    }
}