package Central;
import Sensores.*;


public class CentralDatosSubscriber implements Subscriber {

    private CentralDatos centralDatos;

    public CentralDatosSubscriber(CentralDatos centralDatos) {
        this.centralDatos = centralDatos;
    }

    @Override
    public void recibirMensaje(Medicion medicion) {
        System.out.println("[CENTRAL] Recibida medición -> Sensor:" + medicion.getIdSensor() + " Tipo:" + medicion.getTipo() + " Valor:" + medicion.getValor() + " " + medicion.getUnidad() + " Origen:" + medicion.getOrigen());
        centralDatos.procesarTelemetria(medicion);
    }
}