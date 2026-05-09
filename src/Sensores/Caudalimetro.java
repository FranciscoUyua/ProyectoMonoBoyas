package Sensores;

public class Caudalimetro extends Sensor {

    public Caudalimetro(String id) {
        super(id, "operativo");
    }

    @Override
    public double obtenerMedicion() {
        // Simulación de medición de caudal (en litros por segundo)
        return Math.random() * 100; // Caudal entre 0 y 100 l/s
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
