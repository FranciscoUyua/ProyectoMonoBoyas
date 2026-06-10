package com.monoboyas.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> body) {

        // Para testear: acepta cualquier DNI y contraseña
        // En el backend real esto verifica contra la BD

        Object dni = body.get("dni");
        Object password = body.get("password");

        if (dni == null || password == null) {
            return ResponseEntity.status(400).body(Map.of(
                "error", Map.of(
                    "code", "DATOS_INVALIDOS",
                    "message", "DNI y contraseña son requeridos"
                )
            ));
        }

        // Devuelve un token falso y un usuario de prueba
        return ResponseEntity.ok(Map.of(
            "token", "fake-token-de-prueba-12345",
            "usuario", Map.of(
                "dni", dni,
                "nombre", "Admin Sistema",
                "rol", "ADMIN",
                "plantaId", 1,
                "buqueNroIMO", null,
                "operacionId", null,
                "creadoEn", "2026-01-01T00:00:00Z"
            )
        ));
    }
}