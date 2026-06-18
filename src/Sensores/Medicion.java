package Sensores;

import java.time.LocalDateTime;

public class Medicion {
    private int idSensor;
    private double valor;
    private String unidad;
    private LocalDateTime timestamp;
    private Sensor.TipoSensor tipoSensor;
    public enum OrigenMedicion {
    MONOBOYA,
    BUQUE
}
    private OrigenMedicion origen;


    public Medicion(int idSensor, double valor, String unidad, Sensor.TipoSensor tipoSensor,OrigenMedicion origen) {
        this.idSensor = idSensor;
        this.valor = valor;
        this.unidad = unidad;
        this.tipoSensor = tipoSensor;
        this.origen = origen;
        this.timestamp = LocalDateTime.now();
    }

    public int getIdSensor() { return idSensor; }
    public double getValor() { return valor; }
    public String getUnidad() { return unidad; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Sensor.TipoSensor getTipo() { return tipoSensor; }
    public OrigenMedicion getOrigen() { return origen; }
}