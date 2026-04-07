package ProyectoMonoBoyas.Src.Sensores;

public abstract class Sensor {

    protected String id;
    protected String tipo;//Capa ambiental , mecanica , operativa
    protected boolean activo;

    // Constructor
    public Sensor(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.activo = true;
    }

    // Método abstracto (cada sensor lo implementa distinto)
    public abstract double obtenerMedicion();

    // Getters
    public String getId() {
        return id;
    }

    public abstract String getTipo();

    public abstract boolean isActivo();

    
}