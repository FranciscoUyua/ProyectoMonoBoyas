package Operaciones;

import Usuarios.*;
import Alertas.Alerta;
import Equipamiento.*;

public class Operacion {
    protected int id;
    protected Monoboya monoboya; 
    protected Buque barco; 
    protected OperadorLancha operadorLancha; 
    protected OperadorBuque operadorBuque;   
    protected OperadorPlanta operadorPlanta;
    protected boolean estaActiva;
    protected enum TipoOperacion {
        PREPARADA,
        ENCURSO,
        DETENIDA,
        FINALIZADA
    }
    protected TipoOperacion tipoOperacion;

    public Operacion(int id, Buque b, OperadorBuque operadorBuque) {
        this.id = id;
        barco = b;
        this.estaActiva = false;
        this.tipoOperacion = TipoOperacion.PREPARADA;
        this.operadorBuque = operadorBuque;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void asignarMonoboya(Monoboya monoboya) {
        this.monoboya = monoboya;
    }

    public void asignarOperadorLancha(OperadorLancha operador) {
        this.operadorLancha = operador;
    }

    public void asignarOperadorPlanta(OperadorPlanta operador) {
        this.operadorPlanta = operador;
    }

    public void asignarOperadorBuque(OperadorBuque operador) {
        this.operadorBuque = operador;
    }
    public void asignarBuque(Buque barco) {
        this.barco = barco;
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