package Operaciones;

import Usuarios.*;
import Equipamiento.*;

public class Operacion {
    protected int id;
    protected Monoboya monoboya; 
    protected int pasajeMonoboyaBarco;
    protected Buque barco; 
    protected int pasajeBarcoMonoboya;
    protected OperadorLancha operadorLancha; 
    // protected EstadoOperacion estado; // Comentado si aún no tienes el Enum creado
    protected boolean estaActiva;

    public Operacion(int id, Monoboya monoboya, int pasajeMonoboyaBarco) {
        this.id = id;
        this.monoboya = monoboya;
        this.pasajeMonoboyaBarco = pasajeMonoboyaBarco;
        this.estaActiva = true; // Por defecto, la operación se crea como activa
    }

    public int getId() {
        return id;
    }

    public boolean isActiva() {
        return estaActiva;
    }

    public void finalizarOperacion() {
        this.estaActiva = false;
        System.out.println("\n[SISTEMA] >>> OPERACIÓN " + this.id + " FINALIZADA. Deteniendo recolección de datos.");
    }
}