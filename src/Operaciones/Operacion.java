package Operaciones;

import Usuarios.*;
import Alertas.Alerta;
import Equipamiento.*;

public class Operacion {
    protected int id;
    protected Monoboya monoboya; 
    protected int pasajeMonoboyaBarco;
    protected Buque barco; 
    protected int pasajeBarcoMonoboya;
    
    // Agregamos los 3 operadores que monitorean activamente esta operación
    protected OperadorLancha operadorLancha; 
    protected OperadorBuque operadorBuque;   // Añadido para el flujo
    protected OperadorPlanta operadorPlanta; // Añadido para el flujo
    protected boolean estaActiva;

    public Operacion(int id, Monoboya monoboya, int pasajeMonoboyaBarco) {
        this.id = id;
        this.monoboya = monoboya;
        this.pasajeMonoboyaBarco = pasajeMonoboyaBarco;
        this.estaActiva = true; 
    }

    // NUEVO MÉTODO SETTER: Para vincular al equipo humano de la operación
    public void asignarEquipoHumano(OperadorLancha lancha, OperadorBuque buque, OperadorPlanta planta) {
        this.operadorLancha = lancha;
        this.operadorBuque = buque;
        this.operadorPlanta = planta;
    }

    public void recibirAlerta(Alerta alerta) {
        System.out.println("\n[OPERACIÓN " + this.id + "] -> !!! Alerta Recibida en Servidor Central !!!");
        System.out.println(" > ID Alerta: " + alerta.getId());
        System.out.println(" > Tipo: " + alerta.getTipoAlerta());
        System.out.println(" > Mensaje: " + alerta.getMensaje());
        System.out.println(" > Contexto Medición: " + alerta.getString_medicion());
        
        System.out.println(" [SISTEMA] Distribuyendo alerta a los paneles de los operadores asignados...");
        
        // ENVÍO DE LA ALERTA A CADA UNO DE LOS OPERADORES (Destino Final)
        if (this.operadorLancha != null) {
            this.operadorLancha.mostrarAlertaEnPantalla(alerta);
        }
        if (this.operadorBuque != null) {
            this.operadorBuque.mostrarAlertaEnPantalla(alerta);
        }
        if (this.operadorPlanta != null) {
            this.operadorPlanta.mostrarAlertaEnPantalla(alerta);
        }
        
        // Comportamiento reactivo lógico de seguridad:
        if (alerta.getTipoAlerta().equals("CRITICA")) {
            System.out.println(" [SISTEMA DE SEGURIDAD] Tipo de Alerta CRÍTICA detectada.");
            finalizarOperacion();
        }
    }

    public int getId() { return id; }
    public boolean isActiva() { return estaActiva; }

    public void finalizarOperacion() {
        this.estaActiva = false;
        System.out.println("\n[SISTEMA] >>> OPERACIÓN " + this.id + " FINALIZADA. Deteniendo recolección de datos.");
    }
}