package Usuarios;
import Operaciones.*;

public class OperadorPlanta extends UsuarioOperador {
    protected Operacion operacion;

    public OperadorPlanta(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        this.operacion = null;
    }

    // Métodos específicos para OperadorPlanta

    public void reconocerAlerta() {
        // Implementación específica para reconocer alertas en el contexto de una planta
        System.out.println("OperadorPlanta " + nombre + " está reconociendo una alerta.");
    }
}
