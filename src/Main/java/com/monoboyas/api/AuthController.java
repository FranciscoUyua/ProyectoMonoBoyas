package com.monoboyas.api;

import Persistencia.UsuarioDAO;
import Usuarios.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UsuarioDAO usuarioDAO;

    public AuthController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> body) {
        Object dniObj = body.get("dni");
        Object password = body.get("password");

        if (dniObj == null || password == null) {
            return ResponseEntity.status(400).body(Map.of(
                "error", Map.of(
                    "code", "DATOS_INVALIDOS",
                    "message", "DNI y contraseña son requeridos"
                )
            ));
        }

        int dni;
        try {
            dni = Integer.parseInt(dniObj.toString());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body(Map.of(
                "error", Map.of(
                    "code", "DATOS_INVALIDOS",
                    "message", "DNI debe ser un número"
                )
            ));
        }

        try {
            Usuario usuario = usuarioDAO.buscarPorDni(dni);

            if (!usuario.getContrasena().equals(password.toString())) {
                return ResponseEntity.status(401).body(Map.of(
                    "error", Map.of(
                        "code", "CREDENCIALES_INVALIDAS",
                        "message", "DNI o contraseña incorrectos"
                    )
                ));
            }

            return ResponseEntity.ok(Map.of(
                "token", "fake-token-de-prueba-12345",
                "usuario", Map.of(
                    "id", usuario.getId(),
                    "dni", usuario.getDni(),
                    "nombre", usuario.getNombre(),
                    "rol", usuario.getClass().getSimpleName()
                )
            ));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                "error", Map.of(
                    "code", "CREDENCIALES_INVALIDAS",
                    "message", "DNI o contraseña incorrectos"
                )
            ));
        }
    }
}