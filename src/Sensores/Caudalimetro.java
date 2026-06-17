package Sensores;

public class Caudalimetro extends Sensor {
    public Caudalimetro(int id, int monoboya_id, ISensorDataProvider provider) {
        super(id, monoboya_id, "operativo", "l/s", provider);
    }
}