
import Sensores.Sensor;

public class Buque {

    protected int nroIMO;
    protected int capacidad; 
    protected String nombre;
    protected Sensor transmisorPresion;

    public Buque(int nroIMO, int capacidad, String nombre) {
        this.nroIMO = nroIMO;
        this.capacidad = capacidad;
        this.nombre = nombre;
    }

    public int getNroIMO() {
        return nroIMO;
    }

    public void setNroIMO(int nroIMO) {
        this.nroIMO = nroIMO;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Sensor getTransmisorPresion() {
        return transmisorPresion;
    }

    public void setTransmisorPresion(Sensor transmisorPresion) {
        this.transmisorPresion = transmisorPresion;
    }

    public void IniciarTransferencia(){

    }

    public void DetenerTransferencia(){

    }

    public int transmitirMedicion(){
        if (transmisorPresion != null) {
            //Manejar nulo
        }

        return -1;
    }

    






}
