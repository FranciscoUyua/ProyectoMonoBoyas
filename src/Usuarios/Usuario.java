package Usuarios;



public abstract class Usuario {
    protected int id;
    protected String nombre;
    protected boolean activo;
   
    public Usuario(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
        this.activo = true;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean estaActivo(){
        return activo;
    }

    //Metodos que dependen del tipo de usuario (abstractos)
    

    






}
