package Usuarios;

import Alertas.Alerta;

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

    // NUEVO MÉTODO: Simula la recepción de la alerta en la interfaz gráfica del usuario
    public void mostrarAlertaEnPantalla(Alerta alerta) {
        System.out.println("  [INTERFAZ DE " + this.getClass().getSimpleName().toUpperCase() + ": " + this.nombre + "]");
        System.out.println("  ┌──────────────────────────────────────────────────────────┐");
        System.out.println("  │ ¡¡¡ ALERTA EN TIEMPO REAL DETECTADA EN TU PANEL !!!      │");
        System.out.println("  │ > GRAVEDAD: " + alerta.getTipoAlerta());
        System.out.println("  │ > MENSAJE : " + alerta.getMensaje());
        System.out.println("  └──────────────────────────────────────────────────────────┘");
    }

    // Getters y Setters existentes...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public int getDni() { return dni; }
    public void setDni(int dni) { this.dni = dni; }
}