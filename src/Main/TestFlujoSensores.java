package Main;

import Equipamiento.Buque;
import Equipamiento.Monoboya;
import Operaciones.Operacion;
import Sensores.SensorDeOleaje;
import Sensores.SensorDePresion;
import Sensores.MockSensorDataProvider;
import Central.BrokerMQTT;
import Central.CentralDatos;
import Central.CentralDatosSubscriber;
import Usuarios.OperadorLancha;
import Usuarios.OperadorBuque;
import Usuarios.OperadorPlanta;

public class TestFlujoSensores {

    public static void main(String[] args) throws InterruptedException {
        
        CentralDatos centralEnPlanta = new CentralDatos();
        BrokerMQTT broker = new BrokerMQTT();
        broker.suscribir(new CentralDatosSubscriber(centralEnPlanta));
        Monoboya monoboya = new Monoboya(101, 8, null, broker);

        // Crear operadores ANTES de la operación (el constructor los necesita)
        OperadorLancha juanLancha = new OperadorLancha(1, "Juan (Lancha)", "123", 1111);
        OperadorBuque capitanBuque = new OperadorBuque(2, "Capitán Smith (Buque)", "456", 2222);
        OperadorPlanta pedroPlanta = new OperadorPlanta(3, "Pedro (Sala Control)", "789", 3333);

        // Crear buque
        Buque buque = new Buque(9876543, 50000, "MT Concordia", null);

        // Crear la operación con el nuevo constructor
        Operacion opTransferencia = new Operacion(5001, buque, capitanBuque);
        opTransferencia.asignarMonoboya(monoboya);
        monoboya.asignarOperacion(opTransferencia);
        opTransferencia.asignarOperadorLancha(juanLancha);
        opTransferencia.asignarOperadorPlanta(pedroPlanta);

        SensorDePresion presion = new SensorDePresion(1, new MockSensorDataProvider());
        SensorDeOleaje oleaje = new SensorDeOleaje(2, new MockSensorDataProvider());
        monoboya.agregarSensor(presion);
        monoboya.agregarSensor(oleaje);

        System.out.println("--- INICIANDO DESCARGA DE PETRÓLEO ---");

        int segundosTranscurridos = 0;
        int tiempoSimuladoDescarga = 10; 

        while (opTransferencia.isActiva() && segundosTranscurridos < tiempoSimuladoDescarga) {
            System.out.println("\n--- Segundo " + (segundosTranscurridos + 1) + " ---");
            monoboya.recolectarYTransmitirDatos();
            Thread.sleep(1000); 
            segundosTranscurridos++;
        }
        
        if (opTransferencia.isActiva()) {
            System.out.println("\n[OPERADOR] Tiempo de descarga completado sin incidentes críticos. Finalizando manualmente.");
            opTransferencia.finalizarOperacion();
        } else {
            System.out.println("\n[SISTEMA] El ciclo de muestreo se detuvo de forma anticipada debido a protocolos de seguridad.");
        }
        
        System.out.println("--- EL BARCO SE HA DESCONECTADO ---");
    }
}