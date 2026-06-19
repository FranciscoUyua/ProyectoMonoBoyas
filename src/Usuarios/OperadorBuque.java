package Usuarios;

import Alertas.Alerta;
import java.util.ArrayList;

public class OperadorBuque extends UsuarioOperador {

    public OperadorBuque(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        this.alertasRecibidas = new ArrayList<>();
        rol = "OPERADOR_BUQUE";
    }

    public String getRol() {
    return rol;
    }

    @Override
    public void reconocerAlerta(Alerta alerta) {
        // Esta lógica se ejecutará cuando el capitán haga clic en el botón "Aceptar/Reconocer" en su pantalla
        System.out.println("  [✔] OperadorBuque (" + this.nombre + ") ha RECONOCIDO la alerta ID: " + alerta.getId());
        // Aquí a futuro se podría cambiar el estado de la alerta: alerta.setEstado("RECONOCIDA");
    }
}