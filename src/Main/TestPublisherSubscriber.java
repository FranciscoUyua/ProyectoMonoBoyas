package Main;

import Central.BrokerMQTT;
import Central.CentralDatos;
import Central.CentralDatosSubscriber;
import Equipamiento.Monoboya;
import Sensores.SensorDeOleaje;
import Sensores.SensorDePresion;
import Sensores.MockSensorDataProvider;

public class TestPublisherSubscriber {
    public static void main(String[] args) {
        CentralDatos central = new CentralDatos();
        BrokerMQTT broker = new BrokerMQTT();
        broker.suscribir(new CentralDatosSubscriber(central));

        Monoboya monoboya = new Monoboya(101, 2, null, broker);
        monoboya.agregarSensor(new SensorDePresion(1, new MockSensorDataProvider()));
        monoboya.agregarSensor(new SensorDeOleaje(2, new MockSensorDataProvider()));

        monoboya.recolectarYTransmitirDatos();
    }
}