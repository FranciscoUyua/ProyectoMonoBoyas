package Central;
import Sensores.*;
public interface Subscriber {
    void recibirMensaje(Medicion medicion);
}