package Usuarios;

import Alertas.Alerta;

// Corregido: Ahora hereda de UsuarioOperador para aprovechar el atributo 'operacion' heredado
public class OperadorLancha extends UsuarioOperador {
    
    public OperadorLancha(int id, String nombre, String contrasena, int dni) {
        super(id, nombre, contrasena, dni);
    }

    @Override
    public void reconocerAlerta(Alerta alerta) {
        // Acción desde la tablet o dispositivo móvil en la lancha
        System.out.println("  [✔] OperadorLancha (" + this.nombre + ") ha RECONOCIDO la alerta ID: " + alerta.getId() + " desde el mar.");
    }
}