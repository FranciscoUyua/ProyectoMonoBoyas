import java.util.List;
import java.util.ArrayList;
import Sensores.Sensor;



public class Monoboya {
    protected int id;
    protected Planta planta; // Asociación con Planta
    protected List<Sensor> sensores; // Lista de sensores asociados a la Monoboya
    protected Manguera manguera; // Asociación con Manguera

    public Monoboya(int id, Planta planta) {
        this.id = id;
        this.planta = planta;
        this.sensores = new ArrayList<>();
    }

    // Método para agregar un sensor a la Monoboya
    public void agregarSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public Planta getPlanta() {
        return planta;
    }

    public List<Sensor> getSensores() {
        return sensores;
    }
}