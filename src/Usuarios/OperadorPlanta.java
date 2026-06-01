package Usuarios;

import Alertas.Alerta;

public class OperadorPlanta extends UsuarioOperador {

    public OperadorPlanta(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
    }

    public void reconocerAlerta(Alerta alerta) {
        // Acción desde la sala de control principal en tierra
        System.out.println("  [✔] OperadorPlanta (" + this.nombre + ") ha RECONOCIDO la alerta ID: " + alerta.getId() + " en la base de datos central.");
    }
}