package Equipamiento;

import Usuarios.*;
import Operaciones.*;
import Central.CentralDatos;

public class Planta {
    protected String nombre;
    protected int idPlanta;
    protected int capacidadMaximaMonoboyas;
    protected int cantidadActualMonoboyas;
    protected OperadorPlanta[] operadorPlanta; 
    protected CentralDatos centralDatos; 
    private Monoboya[] monoboyas;

    public Planta(String nombre, int idPlanta) {
        this.nombre = nombre;
        this.idPlanta = idPlanta;
        cantidadActualMonoboyas = 0;
        this.monoboyas = new Monoboya[capacidadMaximaMonoboyas];
    }

    /**
     * Registra la monoboya usando su ID entero como clave.
     */
    public void agregarMonoboya(Monoboya m) {
        if (m != null && cantidadActualMonoboyas < capacidadMaximaMonoboyas) {
            monoboyas[cantidadActualMonoboyas] = m;
            cantidadActualMonoboyas++;
        }
    }

    public void iniciarOperacion(Monoboya m , Operacion operacion) {
            if(m.getEstadoEnum() == Monoboya.EstadoMonoboya.DISPONIBLE && operacion != null) {
                m.estado = Monoboya.EstadoMonoboya.DESHABILITADA; 
                m.asignarOperacion(operacion);
            //terminar
            }
   
    }

    public void recibirSolicitudTransferencia(Operacion operacion) {
        System.out.println("\n[PLANTA " + nombre + "] >>> RECIBIENDO SOLICITUD DE TRANSFERENCIA DE CARGA DEL BUQUE ");
        Monoboya m=obtenerMonoboyaDisponible();
        //Aca habria una logica para que espere en caso de que no haya monoboyas disponibles
        //Pero por cuestiones de tiempo y complejidad no lo hicimos
        operacion.asignarMonoboya(m);
        //Operador opPlanta = obtenerOperadorPlanta()
        //operacion.asignarOperadorPlanta(opPlanta)
        //Operador opLancha = obtenerOperadorLancha()
        //opLancha.asignarOperacion(operacion)
        //operacion.asignarOperadorLancha(opLancha)
        //opLancha.iniciarOperacion()
//reober
    }

    public Monoboya obtenerMonoboyaDisponible() {
        for (int i = 0; i < cantidadActualMonoboyas; i++) {
            if (monoboyas[i].getEstadoEnum() == Monoboya.EstadoMonoboya.DISPONIBLE) {
                return monoboyas[i];
            }
        }
        return null; // No hay monoboyas disponibles
    public CentralDatos getCentralDatos() {
        return centralDatos;
    }

}