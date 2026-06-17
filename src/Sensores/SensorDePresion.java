package Sensores;

public class SensorDePresion extends Sensor {
    public SensorDePresion(int id, int monoboya_id, ISensorDataProvider provider) {
        super(id, monoboya_id, "operativo", "Pa", provider);
    }
}