import java.util.List;
import java.util.ArrayList;
// import com.example.Monoboya; // Uncomment if Monoboya is in another package
// Or create the Monoboya class in the same directory


public class Planta {
    protected String nombre;
    protected int idPlanta;
    private List<Monoboya> monoboyas;

    public Planta(String nombre, int idPlanta) {
        this.nombre = nombre;
        this.idPlanta = idPlanta;
        this.monoboyas = new ArrayList<>();
    }
}
