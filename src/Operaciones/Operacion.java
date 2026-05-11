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

    public Operacion(int id, Monoboya monoboya, int pasajeMonoboyaBarco) {
        this.id = id;
        this.monoboya = monoboya;
        this.pasajeMonoboyaBarco = pasajeMonoboyaBarco;
    }

    public int getId() {
        return id;
    }
}