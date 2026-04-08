package Usuarios;

import java.time.LocalDateTime;

public abstract class Usuario {
    protected int id;
    protected String nombre;
    protected boolean activo;
    protected LocalDateTime ultimoAcceso;
    
    public Usuario(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
        this.activo = true;
        this.ultimoAcceso = LocalDateTime.now();
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

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    //Metodos que dependen del tipo de usuario (abstractos)
    

    






}
