package Usuarios;

public abstract class UsuarioPlanta extends Usuario{

  //Aca habria que ver que tienen en comun el operadorplanta y el administrador

  public UsuarioPlanta(int id, String nombre, String contrasena, int dni) {
    super(id, nombre, contrasena, dni);
  }
  
}
