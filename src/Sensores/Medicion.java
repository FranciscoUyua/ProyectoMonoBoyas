package Sensores;

import java.time.LocalDateTime;

public class Medicion {
    private String idSensor;
    private double valor;
    private String unidad;
    private LocalDateTime timestamp;

    public Medicion(String idSensor, double valor, String unidad) {
        this.idSensor = idSensor;
        this.valor = valor;
        this.unidad = unidad;
        this.timestamp = LocalDateTime.now(); // Marca temporal exacta
    }

    // Getters
    public String getIdSensor() { return idSensor; }
    public double getValor() { return valor; }
    public String getUnidad() { return unidad; }
    public LocalDateTime getTimestamp() { return timestamp; }
}