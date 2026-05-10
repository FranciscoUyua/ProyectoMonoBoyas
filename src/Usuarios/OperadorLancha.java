package Usuarios;
import Operaciones.*;

public class OperadorLancha extends Usuario {
    protected Operacion operacion;
    
    public OperadorLancha(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        this.operacion = null;
    }

    // Métodos específicos para OperadorLancha
    public void reconocerAlerta() {
        // Implementación específica para reconocer alertas en el contexto de una lancha
        System.out.println("OperadorLancha " + nombre + " está reconociendo una alerta.");
    }

}
