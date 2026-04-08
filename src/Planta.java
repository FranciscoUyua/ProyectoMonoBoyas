import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

public class Planta {
    protected String nombre;
    protected int idPlanta;
    
   
    private Map<Integer, Monoboya> monoboyas;

    public Planta(String nombre, int idPlanta) {
        this.nombre = nombre;
        this.idPlanta = idPlanta;
        this.monoboyas = new HashMap<>();
    }

    /**
     * Registra la monoboya usando su ID entero como clave.
     */
    public void agregarMonoboya(Monoboya m) {
        if (m != null) {
            // Suponiendo que m.getId() devuelve un int
            monoboyas.put(m.getId(), m);
        }
    }

    /**
     * Búsqueda directa por ID entero.
     */
    public Monoboya obtenerMonoboya(int id) {
        return monoboyas.get(id);
    }

    /**
     * Para el monitoreo general de la planta.
     */
    public ArrayList<Monoboya> obtenerTodasLasMonoboyas() {
        return new ArrayList<>(monoboyas.values());
    }
}