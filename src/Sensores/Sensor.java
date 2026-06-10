package Sensores;

import java.time.LocalDateTime;

public abstract class Sensor {
    protected String id;
    protected String tipo;
    protected boolean activo;
    protected LocalDateTime ultimaMedicion;
    protected ISensorDataProvider dataProvider; // La nueva dependencia

    // Constructor que permite inyectar el proveedor (para pruebas/mocking)
    public Sensor(String id, String tipo, ISensorDataProvider dataProvider) {
        this.id = id;
        this.tipo = tipo;
        this.dataProvider = dataProvider;
        this.activo = true;
    }

    // Constructor para compatibilidad con las clases hijas actuales (usa Mock por defecto)
    public Sensor(String id, String tipo) {
        this(id, tipo, new MockSensorDataProvider());
    }

    public Medicion generarMedicion() {
        try {
            double valor = dataProvider.obtenerDato();
            this.ultimaMedicion = LocalDateTime.now();
            return new Medicion(this.id, valor, getUnidad());
        } catch (Exception e) {
            // El error ocurre aquí, capturamos y retornamos null para que la Central decida
            System.err.println("Sensor " + id + " error: " + e.getMessage());
            return null; 
        }
    }

    public abstract String getUnidad();

    // Getters
    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public boolean isActivo() { return activo; }
}