package Sensores;

import java.time.LocalDateTime;

public abstract class Sensor {
    protected int id;
    protected int monoboya_id; // NUEVO: ID de la monoboya a la que pertenece
    protected String tipo;
    protected String unidad;   // NUEVO: La unidad ahora es un atributo
    protected boolean activo;
    protected LocalDateTime ultimaMedicion;
    protected ISensorDataProvider dataProvider; 

    // Constructor actualizado
    public Sensor(int id, int monoboya_id, String tipo, String unidad, ISensorDataProvider dataProvider) {
        this.id = id;
        this.monoboya_id = monoboya_id;
        this.tipo = tipo;
        this.unidad = unidad;
        this.dataProvider = dataProvider;
        this.activo = true;
    }

    public Medicion generarMedicion() {
        try {
            double valor = dataProvider.obtenerDato();
            this.ultimaMedicion = LocalDateTime.now();
            return new Medicion(this.id, valor, this.unidad); // Usa el atributo unidad
        } catch (Exception e) {
            System.err.println("Sensor ID " + id + " error: " + e.getMessage());
            // Si el archivo falla, podemos retornar null o disparar una alerta de "Sensor Desconectado"
            return null; 
        }
    }

    // Getters
    public int getId() { return id; }
    public int getMonoboyaId() { return monoboya_id; }
    public String getTipo() { return tipo; }
    public String getUnidad() { return unidad; }
    public boolean isActivo() { return activo; }
}