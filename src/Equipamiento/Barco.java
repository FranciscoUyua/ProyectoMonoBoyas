public class Barco {
    protected int id;
    protected int litros; // Capacidad de litros del barco

    // Constructor
    public Barco(int id, int litros) {
        this.id = id;
        this.litros = litros;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getLitros() {
        return litros;
    }
}
