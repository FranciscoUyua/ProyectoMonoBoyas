package Equipamiento;

import Central.CentralDatos;
import Operaciones.*;
import Sensores.*;

public class Monoboya {
    protected int id;
    protected Sensor[] sensores; // Arreglo de sensores con tamanio fijo
    protected int contadorSensores; // Para llevar el control de cuantos se han agregado
    protected Operacion operacion; 
    protected CentralDatos centralDatos; //La Monoboya conoce y se asocia directamente a la CentralDatos 
    
    public Monoboya(int id, int capacidadMaxima, Operacion operacion, CentralDatos centralDatos) {
        this.id = id;
        this.sensores = new Sensor[capacidadMaxima]; 
        this.contadorSensores = 0;
        this.operacion = operacion; 
        this.centralDatos = centralDatos; // Asignación del nuevo atributo q
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
        // Recolectar datos de cada sensor
        for (int i = 0; i < contadorSensores; i++) {
            Sensor sensor = sensores[i];
            if (sensor != null && sensor.isActivo()) {
                Medicion medicion = new Medicion(sensor.getId(), sensor.getValor(), sensor.getUnidad());
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