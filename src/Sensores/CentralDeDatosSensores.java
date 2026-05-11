package Sensores;

import java.util.ArrayList;
import java.util.List;

public class CentralDeDatosSensores {
    
    // Simula la Base de Datos Histórica (Time-Series)
    private List<Medicion> baseDeDatosTelemetria;

    public CentralDeDatosSensores() {
        this.baseDeDatosTelemetria = new ArrayList<>();
    }

    // Punto de entrada: La monoboya envía los datos aquí
    public void procesarTelemetria(Medicion medicion, String idOperacionActiva) {
        
        // 1. Guardar en Base de Datos (Persistencia)
        guardarMedicion(medicion);

        // 2. Analizar medición contra umbrales (Motor de Reglas)
        evaluarRiesgo(medicion, idOperacionActiva);
    }

    private void guardarMedicion(Medicion medicion) {
        baseDeDatosTelemetria.add(medicion);
        System.out.println("[BD] Guardado -> Sensor: " + medicion.getIdSensor() + 
                           " | Valor: " + String.format("%.2f", medicion.getValor()) + " " + medicion.getUnidad());
    }

    private void evaluarRiesgo(Medicion medicion, String idOperacion) {
        boolean hayAnomalia = false;
        String nivelAlerta = "";

        // Regla dura de ejemplo: Presión superior a 140,000 Pascales
        if (medicion.getUnidad().equals("Pa") && medicion.getValor() > 140000) {
            hayAnomalia = true;
            nivelAlerta = "CRITICA (Roja)";
        }
        // Regla dura de ejemplo: Oleaje superior a 4 metros
        else if (medicion.getUnidad().equals("m") && medicion.getValor() > 4.0) {
            hayAnomalia = true;
            nivelAlerta = "ADVERTENCIA (Amarilla)";
        }

        if (hayAnomalia) {
            generarAlerta(medicion, nivelAlerta, idOperacion);
        }
    }

    private void generarAlerta(Medicion medicion, String nivel, String idOperacion) {
        // En una implementación real, esto dispararía un evento por RabbitMQ o WebSockets
        System.err.println(">> ¡ALERTA " + nivel + "! <<");
        System.err.println("Operación: " + idOperacion + " comprometida.");
        System.err.println("Motivo: El sensor " + medicion.getIdSensor() + " registró " + 
                           String.format("%.2f", medicion.getValor()) + " " + medicion.getUnidad());
    }
}