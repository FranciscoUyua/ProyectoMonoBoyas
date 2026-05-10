package Operaciones;

import Usuarios.*;
import Equipamiento.*;

public class Operacion {
    protected int id;
    protected Monoboya monoboya; // Asociacion con Monoboya
    protected int pasajeMonoboyaBarco;
    protected Buque barco; // Asociacion con Barco
    protected int pasajeBarcoMonoboya;
    protected OperadorLancha operadorLancha; // Asociacion con OperadorLancha
    protected EstadoOperacion estado; 

    public Operacion( int id,Monoboya monoboya, int pasajeMonoboyaBarco ){

    }

}