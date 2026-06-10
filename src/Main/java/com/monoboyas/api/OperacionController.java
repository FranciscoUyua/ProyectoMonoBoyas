package com.monoboyas.api;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/operaciones")
public class OperacionController {

    @GetMapping
    public Map<String, Object> listar(
            @RequestParam(required = false) String estado) {

        List<Map<String, Object>> operaciones = List.of(
            Map.of(
                "id", 1,
                "estado", "ACTIVA",
                "tipo", "DESCARGA",
                "monoboyaId", 1,
                "buqueNroIMO", 9876543,
                "operadorLanchaDni", 11111111,
                "operadorBuqueDni", 22222222,
                "operadorPlantaDni", 99999999,
                "iniciadaEn", "2026-06-09T10:00:00Z",
                "finalizadaEn", ""
            ),
            Map.of(
                "id", 2,
                "estado", "ACTIVA",
                "tipo", "CARGA",
                "monoboyaId", 2,
                "buqueNroIMO", 1234567,
                "operadorLanchaDni", 33333333,
                "operadorBuqueDni", 44444444,
                "operadorPlantaDni", 99999999,
                "iniciadaEn", "2026-06-09T08:00:00Z",
                "finalizadaEn", ""
            )
        );

        return Map.of(
            "data", operaciones,
            "pagination", Map.of(
                "page", 1,
                "limit", 20,
                "total", operaciones.size(),
                "totalPages", 1
            )
        );
    }
}