package Sensores;

import java.time.LocalDateTime;

public abstract class Sensor {

    protected String id;
    protected String tipo;       // ambiental, mecanica, operativa
    protected boolean activo;
    protected LocalDateTime ultimaMedicion;

    public Sensor(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.activo = true;
    }

    // Cada sensor sabe cómo capturar su valor
    public abstract double obtenerMedicion();

    // Produce una Medicion con timestamp y origen — esto es lo que viaja
    public Medicion generarMedicion() {
        double valor = obtenerMedicion();
        this.ultimaMedicion = LocalDateTime.now();
        return new Medicion(this.id, valor, getUnidad());
    }

    // Cada sensor declara su unidad (Pa, m/s, m, etc.)
    public abstract String getUnidad();

    // Getters
    public String getId()       { return id; }
    public String getTipo()     { return tipo; }
    public boolean isActivo()   { return activo; }
    public LocalDateTime getUltimaMedicion() { return ultimaMedicion; }
}