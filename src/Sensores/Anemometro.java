package Sensores;

public class Anemometro extends Sensor {
    // El constructor recibe el ID numérico y el proveedor de datos
    public Anemometro(int id, ISensorDataProvider provider) {
        super(id, "ambiental", provider);
    }

    @Override
    public String getUnidad() { 
        return "km/h"; 
    }
}