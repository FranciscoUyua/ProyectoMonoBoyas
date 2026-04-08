package Usuarios;

import java.time.LocalDateTime;

public abstract class Usuario {
    protected String id;
    protected String nombre;
    protected boolean activo;
    protected LocalDateTime ultimoAcceso;
    
    public Usuario(String id, String nombre){
        this.id = id;
        this.nombre = nombre;
        this.activo = true;
        this.ultimoAcceso = LocalDateTime.now();
    }

    public String getId() {
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
