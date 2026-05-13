package Equipamiento;

import Central.CentralDatos;
import Operaciones.*;
import Sensores.*;

public class Monoboya {
    protected int id;
    protected Planta planta; // Asociacion con Planta
    protected Sensor[] sensores; // Arreglo de sensores con tamanio fijo
    protected int contadorSensores; // Para llevar el control de cuantos se han agregado
    protected Operacion operacion; 
    protected CentralDatos centralDatos; //La Monoboya conoce y se asocia directamente a la CentralDatos 
    
    public Monoboya(int id, Planta planta, int capacidadMaxima, Operacion operacion, CentralDatos centralDatos) {
        this.id = id;
        this.planta = planta;
        this.sensores = new Sensor[capacidadMaxima]; 
        this.contadorSensores = 0;
        this.operacion = operacion; 
        this.centralDatos = centralDatos; // Asignación del nuevo atributo
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
        if (operacion == null) {
            System.out.println("[Monoboya " + id + "] Sin operación activa. Telemetría en espera.");
            return;
        }

        // Validación de seguridad para evitar NullPointerException
        if (this.centralDatos == null) {
            System.err.println("[Error] La Monoboya " + id + " no tiene una CentralDatos asociada.");
            return;
        }

        System.out.println("[Monoboya " + id + "] Iniciando barrido de sensores...");

        // Iteramos solo hasta la cantidad de sensores reales instalados
        for (int i = 0; i < contadorSensores; i++) {
            Sensor sensorActual = sensores[i];
            
            if (sensorActual.isActivo()) {
                // 1. El sensor actúa como driver y va a buscar su dato (API/Archivo)
                Medicion nuevaMedicion = sensorActual.generarMedicion();
                
                // 2. La Monoboya empaqueta y envía el dato a su CentralDatos asociada
                this.centralDatos.procesarTelemetria(nuevaMedicion, String.valueOf(operacion.getId()));
            }
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public Planta getPlanta() {
        return planta;
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