package Central;
import Sensores.*;
import java.util.ArrayList;
import java.util.List;

public class BrokerMQTT implements Publisher {

    private List<Subscriber> subscribers = new ArrayList<>();

    public void suscribir(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void publicar(Medicion medicion) {
        System.out.println("📡 [Broker MQTT] Publicando medición del sensor " + medicion.getIdSensor());
        for (Subscriber subscriber : subscribers) {
            subscriber.recibirMensaje(medicion);
        }
    }
}