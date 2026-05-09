package Usuarios;

public abstract class Usuario {
    protected int id;
    protected String nombre;
    protected String contrasena;
    protected int dni;
    
    public Usuario(int id, String nombre, String contrasena, int dni){
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.dni = dni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }


    //Metodos que dependen del tipo de usuario (abstractos)
    

    






}
