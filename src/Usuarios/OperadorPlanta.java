package Usuarios;

import java.util.ArrayList;
import java.util.List;

import Alertas.Alerta;
import Equipamiento.Planta;
import Operaciones.Operacion;

public class OperadorPlanta extends UsuarioOperador {

    protected int id;
    protected String nombre;
    protected String contrasena;
    protected int dni;
    protected List<Alerta> alertasRecibidas; // Lista para almacenar las alertas recibidas por el usuario
    protected String rol;
    protected Planta planta; // Referencia a la planta que opera
    protected Operacion operacion; // Referencia a la operación que el operador está manejando

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