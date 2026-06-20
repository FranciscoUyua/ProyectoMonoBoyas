package Usuarios;
import Usuarios.*;
import Operaciones.*;
import Persistencia.UsuarioAlertaDAO;
import Equipamiento.*;


public class Administrador extends Usuario {

    protected OperadorBuque operadorBuque; // Referencia al operador de buque asignado
    protected Usuario usuario; // Referencia al usuario que se está gestionando (para agregar, eliminar o modificar)
    protected Planta planta; // Referencia a la planta que opera

    public Administrador(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        rol = "ADMIN";
    }

    public String getRol() {
        return rol;
    }

    public void agregarUsuario(Usuario nuevoUsuario) {
    }

    public void eliminarUsuario(int idUsuario) {
    }

    public void modificarUmbrales() {
    }

    public void PlanificarOperacion(Buque barco,OperadorBuque operadorBuque, Planta planta) {
        Operacion operacion = new Operacion((int)(Math.random() * 100),  barco, operadorBuque, planta); 
        //operacion ya se crea en preparada
    
    }


}
