package Main;

import Equipamiento.Monoboya;
import Equipamiento.Planta;
import Operaciones.Operacion;
import Sensores.SensorDeOleaje;
import Sensores.SensorDePresion;
import Central.CentralDatos;

public class TestFlujoSensores {

    public static void main(String[] args) throws InterruptedException {
        
        CentralDatos centralEnPlanta = new CentralDatos();
        Planta miPlanta = new Planta("Planta de Prueba", 1);
        Monoboya monoboya = new Monoboya(101, miPlanta, 8, null, centralEnPlanta);        
        // Creamos la operación (Nace con estado activa = true)
        Operacion opTransferencia = new Operacion(5001, monoboya, 2000); 
        monoboya.asignarOperacion(opTransferencia);

        SensorDePresion presion = new SensorDePresion("PRES-01");
        SensorDeOleaje oleaje = new SensorDeOleaje("OLEA-01");
        monoboya.agregarSensor(presion);
        monoboya.agregarSensor(oleaje);

        System.out.println("--- INICIANDO DESCARGA DE PETRÓLEO ---");

        int segundosTranscurridos = 0;
        int tiempoSimuladoDescarga = 10; // Supongamos que dura 10 segundos

        // BUCLE DE POLLING (Muestreo continuo)
        // Mientras la operación siga activa, la monoboya no dejará de pedir datos
        while (opTransferencia.isActiva()) {
            
            System.out.println("\n--- Segundo " + (segundosTranscurridos + 1) + " ---");
            
            // 1. La monoboya toma la "foto" del momento y la envía a la Central
            monoboya.recolectarYTransmitirDatos();

            // 2. Esperamos 1 segundo exacto en la vida real antes de la siguiente captura
            Thread.sleep(1000); 
            segundosTranscurridos++;

            // 3. Condición de corte: Simulamos que el operador finaliza la descarga a los 10 segundos
            // (En la vida real, el operador apretaría un botón en el sistema para llamar a este método)
            if (segundosTranscurridos == tiempoSimuladoDescarga) {
                opTransferencia.finalizarOperacion();
            }
        }
        
        System.out.println("--- EL BARCO SE HA DESCONECTADO ---");
    }
}