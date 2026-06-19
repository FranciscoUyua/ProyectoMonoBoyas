package Usuarios;
import Operaciones.*;
import java.util.ArrayList;

public abstract class UsuarioPlanta extends Usuario{
    protected Operacion operacion;


  //Aca habria que ver que tienen en comun el operadorplanta y el administrador

  public UsuarioPlanta(int id, String nombre, String contrasena, int dni) {
    super(id, nombre, contrasena, dni);
    this.operacion = null; // Inicialmente sin operación asignada
    this.alertasRecibidas = new ArrayList<>();
  }
  
}
