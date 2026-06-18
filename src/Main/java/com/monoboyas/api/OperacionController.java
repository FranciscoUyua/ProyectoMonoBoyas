package com.monoboyas.api;

import Persistencia.OperacionDAO;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/operaciones")
public class OperacionController {

    private final OperacionDAO operacionDAO;

    public OperacionController(OperacionDAO operacionDAO) {
        this.operacionDAO = operacionDAO;
    }

    @GetMapping
    public Map<String, Object> listar(@RequestParam(required = false) String estado) {
        List<OperacionDAO.OperacionInfo> operaciones = (estado != null)
            ? operacionDAO.listarPorEstado(estado)
            : operacionDAO.listarTodas();

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