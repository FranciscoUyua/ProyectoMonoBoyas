package Usuarios;

import Alertas.Alerta;
import java.util.ArrayList;

public class OperadorPlanta extends UsuarioOperador {

    public OperadorPlanta(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        this.alertasRecibidas = new ArrayList<>();
        rol = "OPERADOR_PLANTA";
    }

    public String getRol() {
    return rol;
    }

    public void reconocerAlerta(Alerta alerta) {
        // Acción desde la sala de control principal en tierra
        System.out.println("  [✔] OperadorPlanta (" + this.nombre + ") ha RECONOCIDO la alerta ID: " + alerta.getId() + " en la base de datos central.");
    }
}