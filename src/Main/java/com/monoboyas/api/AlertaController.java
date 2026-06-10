package com.monoboyas.api;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/alertas")
public class AlertaController {

    @GetMapping
    public Map<String, Object> listar(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String estado) {

        List<Map<String, Object>> alertas = List.of(
            Map.of(
                "id", 1,
                "tipo", "CRITICA",
                "mensaje", "Presión fuera de rango en sensor PRES-01",
                "operacionId", 1,
                "sensorId", "PRES-01",
                "valorMedicion", 150.5,
                "estado", "PENDIENTE",
                "generadaEn", "2026-06-09T14:00:00Z",
                "reconocidaPorDni", 0,
                "reconocidaEn", ""
            ),
            Map.of(
                "id", 2,
                "tipo", "ADVERTENCIA",
                "mensaje", "Velocidad de viento elevada",
                "operacionId", 1,
                "sensorId", "ANEM-01",
                "valorMedicion", 65.0,
                "estado", "PENDIENTE",
                "generadaEn", "2026-06-09T13:45:00Z",
                "reconocidaPorDni", 0,
                "reconocidaEn", ""
            )
        );

        return Map.of(
            "data", alertas,
            "pagination", Map.of(
                "page", 1,
                "limit", 50,
                "total", alertas.size(),
                "totalPages", 1
            )
        );
    }
}