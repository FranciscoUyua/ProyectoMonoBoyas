import java.util.List;
import java.util.ArrayList;
import Sensores.Sensor;



public class Monoboya {
    private String id;
    private String ubicacion;
    private Planta planta; // Asociación con Planta
    private List<Sensor> sensores; // Lista de sensores asociados a la Monoboya

    public Monoboya(String id, String ubicacion, Planta planta) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.planta = planta;
        this.sensores = new ArrayList<>();
    }

    // Método para agregar un sensor a la Monoboya
    public void agregarSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Planta getPlanta() {
        return planta;
    }

    public List<Sensor> getSensores() {
        return sensores;
    }
}