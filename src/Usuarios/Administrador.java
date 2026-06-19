package Usuarios;

import Operaciones.*;
import Equipamiento.*;
import java.util.ArrayList;
import java.util.List;

import Alertas.Alerta;


public class Administrador extends Usuario {

    protected int id;
    protected String nombre;
    protected String contrasena;
    protected int dni;
    protected List<Alerta> alertasRecibidas; // Lista para almacenar las alertas recibidas por el usuario
    protected String rol;
    protected Planta planta; // Referencia a la planta que opera

    public Administrador(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        this.alertasRecibidas = new ArrayList<>();
        rol = "ADMIN";
    }

    public String getRol() {
    return rol;
    }

    // Métodos específicos para Administrador
    public void PlanificarOperacion(Buque barco,OperadorBuque operadorBuque) {
        Operacion operacion = new Operacion((int)(Math.random() * 100),  barco, operadorBuque); 
    }


}
