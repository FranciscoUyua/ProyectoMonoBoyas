package Sensores;

public class Giroscopio extends Sensor {
    public Giroscopio(int id, int monoboya_id, ISensorDataProvider provider) {
        super(id, monoboya_id, "mecanica", "grados", provider);
    }
}