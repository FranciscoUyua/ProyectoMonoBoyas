package Main;

import Equipamiento.Monoboya;
import Equipamiento.Planta;
import Operaciones.Operacion;
import Sensores.*; // Importamos todos los sensores de golpe
import Central.CentralDatos;

public class TestFlujoSensores {

    public static void main(String[] args) {
        
        Planta miPlanta = null; 
        CentralDatos centralEnPlanta = new CentralDatos();

        // Monoboya inicializada con capacidad para los 8 sensores
        Monoboya monoboya = new Monoboya(101, miPlanta, 8, null);
        Operacion opTransferencia = new Operacion(5001, monoboya, 2000); 
        monoboya.asignarOperacion(opTransferencia);

        // Instanciamos los 8 sensores
        SensorDePresion presion = new SensorDePresion("PRES-01");
        Caudalimetro caudal = new Caudalimetro("CAUD-01");
        SensorDeTension tension = new SensorDeTension("TENS-01");
        SensorDeDistancia distancia = new SensorDeDistancia("DIST-01");
        Giroscopio giroscopio = new Giroscopio("GIRO-01");
        
        SensorDeOleaje oleaje = new SensorDeOleaje("OLEA-01");
        Anemometro anemometro = new Anemometro("ANEM-01");
        Correntometro correntometro = new Correntometro("CORR-01");

        // Los instalamos en la Monoboya
        monoboya.agregarSensor(presion);
        monoboya.agregarSensor(caudal);
        monoboya.agregarSensor(tension);
        monoboya.agregarSensor(distancia);
        monoboya.agregarSensor(giroscopio);
        monoboya.agregarSensor(oleaje);
        monoboya.agregarSensor(anemometro);
        monoboya.agregarSensor(correntometro);

        // EJECUTAR EL TEST
        System.out.println("--- INICIANDO TEST CON 8 SENSORES ---");
        monoboya.recolectarYTransmitirDatos(centralEnPlanta);
    }
}