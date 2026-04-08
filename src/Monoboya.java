import java.util.List;
import java.util.ArrayList;
import Sensores.Sensor;



public class Monoboya {
    private int id;
    private Planta planta; // Asociación con Planta
    private List<Sensor> sensores; // Lista de sensores asociados a la Monoboya

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