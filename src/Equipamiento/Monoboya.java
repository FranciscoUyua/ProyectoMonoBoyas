package Equipamiento;
import Operaciones.*;
import Sensores.*;

public class Monoboya {
    protected int id;
    protected Planta planta; // Asociacion con Planta
    protected Sensor[] sensores; // Arreglo de sensores con tamanio fijo [cite: 729]
    protected int contadorSensores; // Para llevar el control de cuantos se han agregado
    protected Operacion operacion ; 
    // Constructor: ahora necesitas definir el tamanio maximo del arreglo
    public Monoboya(int id, Planta planta, int capacidadMaxima, Operacion operacion ) {
        this.id = id;
        this.planta = planta;
        this.sensores = new Sensor[capacidadMaxima]; // Inicializacion del arreglo
        this.contadorSensores = 0;
        operacion = null; 
    }

    public void asignarOperacion(Operacion operacion){
        this.operacion = operacion; 
    }
    // Metodo para agregar un sensor al arreglo
    public void agregarSensor(Sensor sensor) {
        if (contadorSensores < sensores.length) {
            sensores[contadorSensores] = sensor;
            contadorSensores++;
        } else {
            System.out.println("Error: No hay mas espacio para sensores en esta monoboya.");
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public Planta getPlanta() {
        return planta;
    }

    // Retorna el arreglo completo
    public Sensor[] getSensores() {
        return sensores;
    }
    
    // Util para saber cuantos sensores reales hay instalados
    public int getCantidadSensores() {
        return contadorSensores;
    }
}