package Equipamiento;

import Central.*;
import Operaciones.Operacion;
import Sensores.Medicion.OrigenMedicion;
import Sensores.*;

public class Buque {

    protected int nroIMO;
    protected int capacidad; 
    protected int capacidadRestante;
    protected String nombre;
    protected Sensor transmisorPresion;
    private Publisher publisher;
    protected CentralDatos centralDatos;
    protected Operacion operacion;

    public Buque(int nroIMO, int capacidad, String nombre,Publisher publisher,Operacion operacion) {
        this.nroIMO = nroIMO;
        this.capacidad = capacidad;
        capacidadRestante=capacidad;
        this.nombre = nombre;
        this.publisher = publisher;
        this.operacion = operacion;
        this.centralDatos = centralDatos;
    }

    public Buque(int nroIMO, int capacidad, String nombre, Publisher publisher) {
        this(nroIMO, capacidad, nombre, publisher, null);
    }

    public void recolectarDatos() {
        Medicion nuevaMedicion = new Medicion(transmisorPresion.getId(),transmisorPresion.getValor(),transmisorPresion.getUnidad(),transmisorPresion.getTipo(),OrigenMedicion.BUQUE,operacion.getId());
        publisher.publicar(nuevaMedicion);
    }
            
    public void solitudTransferencia() {
        operacion.getPlanta().recibirSolicitudTransferencia(operacion);
        System.out.println("\n[BUQUE " + nombre + "] >>> SOLICITANDO TRANSFERENCIA DE CARGA...");
    }

    public int getNroIMO() {
        return nroIMO;
    }

    public void descontarCapacidad(double litrosTransferidos) {
        int litrosEnteros = (int) Math.round( litrosTransferidos) ; // trunca el decimal, ej: 142.7 -> 142
        capacidadRestante -= litrosEnteros;
        if (capacidadRestante < 0) {
            capacidadRestante = 0;
        }
    }

    public int getCapacidadRestante(){

        return capacidadRestante;
    }
    public void setNroIMO(int nroIMO) {
        this.nroIMO = nroIMO;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Sensor getTransmisorPresion() {
        return transmisorPresion;
    }

    public void setTransmisorPresion(Sensor transmisorPresion) {
        this.transmisorPresion = transmisorPresion;
    }

    public void IniciarTransferencia(){

    }

    public void DetenerTransferencia(){

    }

    public boolean finalizoDescarga(){
        if(capacidad!=)
        return false;
    }

    public CentralDatos getCentralDatos() {
        return centralDatos;
    }

}
