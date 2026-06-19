package Usuarios;
import Alertas.Alerta;
import Operaciones.*;
import java.util.ArrayList;

public abstract class UsuarioOperador extends Usuario{
    protected Operacion operacion;

    public UsuarioOperador(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        this.operacion = null; // Inicialmente sin operación asignada
        this.alertasRecibidas = new ArrayList<>();
    }

    public abstract void reconocerAlerta(Alerta alerta); // Método abstracto para que cada operador lo implemente según su tipo

    // Métodos específicos para UsuarioOperador
    
}
