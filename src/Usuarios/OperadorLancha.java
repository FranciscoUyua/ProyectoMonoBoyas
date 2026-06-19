package Usuarios;

import Alertas.Alerta;

public class OperadorLancha extends UsuarioOperador {

    public OperadorLancha(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
        this.alertasRecibidas = new ArrayList<>();
        rol = "OPERADOR_LANCHA";
    }

    public String getRol() {
    return rol;
    }

    // Específico de la lancha: dispara el inicio. La lógica real vive en Operacion (compa).
    public void iniciarOperacion() {
        if (operacion == null) {
            System.out.println("  OperadorLancha (" + nombre + "): sin operación asignada.");
            return;
        }
        operacion.iniciarOperacion();
        System.out.println(" OperadorLancha (" + nombre + ") inició la operación " + operacion.getId() + ".");
    }

    @Override
    public void reconocerAlerta(Alerta alerta) {
        alerta.cambiarEstado();
        System.out.println("  OperadorLancha (" + nombre + ") RECONOCIÓ la alerta ID " + alerta.getId() + " desde el mar.");
    }
}