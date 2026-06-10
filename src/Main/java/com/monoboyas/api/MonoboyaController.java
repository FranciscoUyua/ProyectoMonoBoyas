package com.monoboyas.api;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/monoboyas")
public class MonoboyaController {

    @GetMapping
    public List<Map<String, Object>> listar() {
        return List.of(
            Map.of("id", 1, "estado", "EN_OPERACION",
                   "plantaId", 1, "capacidadMaximaSensores", 8,
                   "cantidadSensoresInstalados", 8),
            Map.of("id", 2, "estado", "EN_OPERACION",
                   "plantaId", 1, "capacidadMaximaSensores", 8,
                   "cantidadSensoresInstalados", 5),
            Map.of("id", 3, "estado", "DISPONIBLE",
                   "plantaId", 1, "capacidadMaximaSensores", 8,
                   "cantidadSensoresInstalados", 3)
        );
    }

    @GetMapping("/{id}")
    public Map<String, Object> obtener(@PathVariable int id) {
        return Map.of("id", id, "estado", "EN_OPERACION",
                      "plantaId", 1, "capacidadMaximaSensores", 8,
                      "cantidadSensoresInstalados", 8);
    }
}