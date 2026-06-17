package Sensores;

import java.time.LocalDateTime;

public class Medicion {
    private int idSensor; 
    private double valor;
    private String unidad;
    private LocalDateTime timestamp;

    public Medicion(int idSensor, double valor, String unidad) {
        this.idSensor = idSensor;
        this.valor = valor;
        this.unidad = unidad;
        this.timestamp = LocalDateTime.now(); 
    }

    // Getters
    public int getIdSensor() { return idSensor; } // Devuelve int
    public double getValor() { return valor; }
    public String getUnidad() { return unidad; }
    public LocalDateTime getTimestamp() { return timestamp; }
}