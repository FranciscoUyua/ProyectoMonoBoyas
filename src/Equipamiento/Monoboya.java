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

    // Constructor
    public Monoboya(int id, Planta planta, int capacidadMaxima, Operacion operacion) {
        this.id = id;
        this.planta = planta;
        this.sensores = new Sensor[capacidadMaxima]; // Inicializacion del arreglo
        this.contadorSensores = 0;
        this.operacion = operacion; 
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
    // RECOLECCIÓN Y TRANSMISIÓN DE TELEMETRÍA (Actualizado a CentralDatos)
    // ----------------------------------------------------------------------
    
    // Este método recibe la nueva CentralDatos alojada en la Planta
    public void recolectarYTransmitirDatos(CentralDatos central) {
        if (operacion == null) {
            System.out.println("[Monoboya " + id + "] Sin operación activa. Telemetría en espera.");
            return;
        }

        System.out.println("[Monoboya " + id + "] Iniciando barrido de sensores...");

        // Iteramos solo hasta la cantidad de sensores reales instalados
        for (int i = 0; i < contadorSensores; i++) {
            Sensor sensorActual = sensores[i];
            
            if (sensorActual.isActivo()) {
                // 1. El sensor actúa como driver y va a buscar su dato (API/Archivo)
                Medicion nuevaMedicion = sensorActual.generarMedicion();
                
                // 2. La Monoboya empaqueta y envía el dato a la CentralDatos en la Planta
                central.procesarTelemetria(nuevaMedicion, String.valueOf(operacion.getId()));
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
}