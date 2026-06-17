package Sensores;

public class SensorDeTension extends Sensor {
    public SensorDeTension(int id, int monoboya_id, ISensorDataProvider provider) {
        super(id, monoboya_id, "mecanica", "t", provider);
    }
}