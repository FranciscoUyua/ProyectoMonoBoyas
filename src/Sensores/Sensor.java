package Sensores;

import java.time.LocalDateTime;

public abstract class Sensor {
    protected int id;
    protected String tipo;
    protected String unidad;
    protected boolean activo;
    protected LocalDateTime ultimaMedicion;
    
    protected double valor; 
    protected ISensorDataProvider dataProvider; 

    // Constructor actualizado
    public Sensor(int id, String tipo, String unidad, ISensorDataProvider dataProvider) {
        this.id = id;
        this.tipo = tipo;
        this.unidad = unidad;
        this.dataProvider = dataProvider;
        this.activo = true;
        this.valor = 0.0; // Valor por defecto al inicializar
    }

    //En lugar de generar un objeto, simplemente actualiza su estado interno
    public void actualizarDato() {
        if (this.activo && this.dataProvider != null) {
            try {
                this.valor = dataProvider.obtenerDato();
                this.ultimaMedicion = LocalDateTime.now();
            } catch (Exception e) {
                System.err.println("Sensor ID " + id + " error al leer dato: " + e.getMessage());
                // Mantenemos el último valor conocido o manejamos el error según el negocio
            }
        }
    }

    // --------------------------------------------------------
    // GETTERS IMPLEMENTADOS
    // --------------------------------------------------------
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public String getUnidad() { return unidad; }
    public boolean isActivo() { return activo; }
    public double getValor() { return valor; }
    public LocalDateTime getUltimaMedicion() { return ultimaMedicion; }
}