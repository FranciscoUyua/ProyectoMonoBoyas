package Sensores;

public interface ISensorDataProvider {
    // Definimos que el proveedor lanza una excepción si la API falla
    double obtenerDato() throws Exception;
}