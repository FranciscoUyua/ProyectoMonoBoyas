package Equipamiento;

import Central.*;
import Operaciones.*;
import Sensores.*;

public class Monoboya {
    protected int id;
    protected Sensor[] sensores; // Arreglo de sensores con tamanio fijo
    protected int contadorSensores; // Para llevar el control de cuantos se han agregado
    protected Operacion operacion; 
    protected CentralDatos centralDatos; //La Monoboya conoce y se asocia directamente a la CentralDatos 
    //central de datos se va
    protected Publisher publisher; // La Monoboya tiene un Publisher para enviar datos a la CentralDatos
    
    public Monoboya(int id, int capacidadMaxima, Operacion operacion, Publisher publisher) {
        this.id = id;
        this.sensores = new Sensor[capacidadMaxima]; 
        this.contadorSensores = 0;
        this.operacion = operacion; 
        this.publisher = publisher;
    }

    public void asignarOperacion(Operacion operacion){
        this.operacion = operacion; 
    }

    // Metodo para agregar un sensor al arreglo
    public void agregarSensor(Sensor sensor) {
        if (contadorSensores < sensores.length) {
            sensores[contadorSensores] = sensor;
            contadorSensores++;
        } else {
            System.err.println("Error: No hay más espacio para sensores en la monoboya " + this.id);
        }
    }

    // ----------------------------------------------------------------------
    // RECOLECCIÓN Y TRANSMISIÓN DE TELEMETRÍA (Sin parámetros)
    // ----------------------------------------------------------------------
    

     public void recolectarYTransmitirDatos() {
    // Agregamos "Sensor" (con mayúscula si es tu clase) antes de la variable
        for (Sensor sensor : sensores) { 
            if (sensor != null) {
                Medicion nuevaMedicion = new Medicion(sensor.getId(), sensor.getValor(), sensor.getUnidad());

            publisher.publicar(nuevaMedicion);
        }
    }
}



    // Getters
    public int getId() {
        return id;
    }

    public Sensor[] getSensores() {
        return sensores;
    }
    
    public int getCantidadSensores() {
        return contadorSensores;
    }
    
    public Operacion getOperacion() {
        return operacion;
    }

    public CentralDatos getCentralDatos() {
        return centralDatos;
    }

}