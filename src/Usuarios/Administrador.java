package Usuarios;

import Operaciones.*;
import Equipamiento.*;


public class Administrador extends Usuario {

    public Administrador(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        this.alertasRecibidas = new ArrayList<>();
    }

    // Métodos específicos para Administrador
    public void PlanificarOperacion(Buque barco,OperadorBuque operadorBuque) {
        Operacion operacion = new Operacion((int)(Math.random() * 100),  barco, operadorBuque); 
    }


}
