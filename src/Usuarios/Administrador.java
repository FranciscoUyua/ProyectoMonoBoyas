package Usuarios;
import java.util.List;

import Equipamiento.Buque;
import Equipamiento.Planta;
import Operaciones.Operacion;
import Persistencia.UsuarioDAO;


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

    public void PlanificarOperacion(Buque barco, Planta planta, UsuarioDAO usuarioDAO) {
        operadorBuque = obtenerOperadorBuqueDisponible(usuarioDAO);
        Operacion operacion = new Operacion((int)(Math.random() * 100),  barco, operadorBuque, planta); 
        //operacion ya se crea en preparada
    
    }

    public Usuario primerOperadorBuqueDisponible(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            if (u.getOperacion() == null) {
                return u;
            }
        }
        return null;
    }

    public OperadorBuque obtenerOperadorBuqueDisponible(UsuarioDAO usuarioDAO) {
        List<Usuario> usuarios = usuarioDAO.listarPorRol("OPERADOR_BUQUE");
        Usuario disponible = primerOperadorBuqueDisponible(usuarios);
        return (OperadorBuque) disponible;
    }

}
