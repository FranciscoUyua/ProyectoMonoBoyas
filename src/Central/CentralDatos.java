package Central;

import Sensores.Medicion;

public class CentralDatos {
    
    // Método que recibe el dato desde la Monoboya
    public void procesarTelemetria(Medicion medicion, String idOperacion) {
        System.out.println("=== CENTRAL DE DATOS (PLANTA) ===");
        System.out.println(" > Operación activa: " + idOperacion);
        System.out.println(" > Sensor origen: " + medicion.getIdSensor());
        System.out.println(" > Valor registrado: " + String.format("%.2f", medicion.getValor()) + " " + medicion.getUnidad());
        System.out.println(" > Hora de captura: " + medicion.getTimestamp());
        System.out.println("=================================\n");
    }
}