package Usuarios;


public class OperadorBuque extends UsuarioOperador {

    public OperadorBuque(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        this.operacion = null;
    }

    // Métodos específicos para UsuarioBarco
    public void reconocerAlerta() {
        // Implementación específica para reconocer alertas en el contexto de un buque
        System.out.println("OperadorBuque " + nombre + " está reconociendo una alerta.");
    }

}
