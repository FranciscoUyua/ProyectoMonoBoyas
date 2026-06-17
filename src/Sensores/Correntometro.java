package Sensores;

public class Correntometro extends Sensor {
    public Correntometro(int id, ISensorDataProvider provider) {
        super(id, "ambiental", provider);
    }

    @Override
    public String getUnidad() { 
        return "m/s"; 
    }
}