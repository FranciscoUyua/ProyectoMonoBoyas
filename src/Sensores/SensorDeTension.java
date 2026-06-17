package Sensores;

import java.time.LocalDateTime;

public class SensorDeTension extends Sensor {
    public SensorDeTension(int id, ISensorDataProvider provider) {
        super(id, "mecanica", "t", provider);
    }

    // GETTERS
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public String getUnidad() {
        return unidad;
    }

    @Override
    public boolean isActivo() {
        return activo;
    }

    @Override
    public double getValor() {
        return valor;
    }

    @Override
    public LocalDateTime getUltimaMedicion() {
        return ultimaMedicion;
    }
}