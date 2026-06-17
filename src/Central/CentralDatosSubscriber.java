package Central;
import Sensores.*;


public class CentralDatosSubscriber implements Subscriber {

    private CentralDatos centralDatos;

    public CentralDatosSubscriber(CentralDatos centralDatos) {
        this.centralDatos = centralDatos;
    }

    @Override
    public void recibirMensaje(Medicion medicion) {
        centralDatos.procesarTelemetria(medicion);
    }
}