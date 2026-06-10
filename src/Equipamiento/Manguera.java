package Equipamiento;

import Sensores.Caudalimetro;
import Sensores.SensorDePresion;

public class Manguera {

    protected Caudalimetro caudalimetro; // Sensor de caudal
    protected SensorDePresion  sensorPresion; // Sensor de presión

    protected Monoboya monoboya; // Asociación con Monoboya
    protected Buque buque; // Asociación con Barco
}

