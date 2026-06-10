package Central;

import Alertas.Alerta;
import Operaciones.Operacion;
import Sensores.Medicion;

public class CentralDatos {
    
    private int contadorAlertas = 1; // Para generar IDs correlativos de alertas

    // Simulamos una referencia a la operación activa para poder enviarle la alerta
    // En una arquitectura completa, la central buscaría la operación por su ID.
    private Operacion operacionInstanciada;

    // Método setter para que en el test podamos asociar la operación a la Central
    public void setOperacionActiva(Operacion operacion) {
        this.operacionInstanciada = operacion;
    }

    public void procesarTelemetria(Medicion medicion, String idOperacion) {
        int idOpInt = Integer.parseInt(idOperacion);

        // ESCENARIO A: Inconsistencia/Fallo Aleatorio (Dato nulo)
        if (medicion == null) {
            System.out.println("\n=== CENTRAL DE DATOS: INCONSISTENCIA DETECTADA ===");
            
            // Instanciamos Alerta usando estrictamente tu constructor:
            // Alerta(id, tipoAlerta, mensaje, id_operacion, int_medicion, string_medicion)
            Alerta alertaFallo = new Alerta(
                contadorAlertas++, 
                "CRITICA", 
                "Error crítico: El sensor remoto dejó de transmitir datos a la API.", 
                idOpInt, 
                -1, // Código de error entero para int_medicion
                "Sensor Origen Desconocido (Fallo de Transmisión)"
            );

            System.out.println(" > Instanciando Alerta ID: " + alertaFallo.getId());
            System.out.println("=================================================");

            // La alerta viaja directamente a la operación afectada
            if (operacionInstanciada != null && operacionInstanciada.getId() == idOpInt) {
                operacionInstanciada.recibirAlerta(alertaFallo);
            }
            return;
        }

        // ESCENARIO B: Telemetría normal transmitida por la Monoboya
        System.out.println("=== CENTRAL DE DATOS (PLANTA) ===");
        System.out.println(" > Operación activa: " + idOpInt);
        System.out.println(" > Sensor origen: " + medicion.getIdSensor());
        System.out.println(" > Valor registrado: " + String.format("%.2f", medicion.getValor()) + " " + medicion.getUnidad());
        System.out.println("=================================\n");
        
        // Simulación lógica de control adicional: si el valor supera un umbral crítico (ej: 85.0)
        if (medicion.getValor() > 85.0) {
            System.out.println("\n=== CENTRAL DE DATOS: VALOR FUERA DE RANGO ===");
            Alerta alertaUmbral = new Alerta(
                contadorAlertas++,
                "ADVERTENCIA",
                "Valor de telemetría inusualmente alto.",
                idOpInt,
                (int) medicion.getValor(), // Convertimos el double a int para cumplir tu firma
                "Sensor: " + medicion.getIdSensor() + " reportando exceso."
            );
            
            if (operacionInstanciada != null && operacionInstanciada.getId() == idOpInt) {
                operacionInstanciada.recibirAlerta(alertaUmbral);
            }
        }
    }
}