package Equipamiento;

import Usuarios.*;
import Operaciones.*;

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

    public CentralDatos getCentralDatos() {
        return centralDatos;
    }

}