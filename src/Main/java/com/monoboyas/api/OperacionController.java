package com.monoboyas.api;

import Persistencia.OperacionDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/operaciones")
public class OperacionController {

    private final OperacionDAO operacionDAO;
    private final OperacionService operacionService;

    public OperacionController(OperacionDAO operacionDAO, OperacionService operacionService) {
        this.operacionDAO = operacionDAO;
        this.operacionService = operacionService;
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

    @PostMapping
    public ResponseEntity<?> planificar(@RequestBody Map<String, Object> body) {
        try {
            int buqueNroIMO = (int) body.get("buqueNroIMO");
            int plantaId    = (int) body.get("plantaId");
            String tipo     = (String) body.get("tipo");
            return ResponseEntity.status(201).body(
                operacionService.planificar(buqueNroIMO, plantaId, tipo)
            );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/preparar")
    public ResponseEntity<?> preparar(@PathVariable int id, @RequestBody Map<String, Object> body) {
        try {
            int monoboyaId       = (int) body.get("monoboyaId");
            int operadorPlantaId = (int) body.get("operadorPlantaId");
            int operadorLanchaId = (int) body.get("operadorLanchaId");
            return ResponseEntity.ok(
                operacionService.preparar(id, monoboyaId, operadorPlantaId, operadorLanchaId)
            );
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<?> iniciar(@PathVariable int id, @RequestBody Map<String, Object> body) {
        try {
            int operadorBuqueId = (int) body.get("operadorBuqueId");
            return ResponseEntity.ok(operacionService.iniciar(id, operadorBuqueId));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/detener")
    public ResponseEntity<?> detener(@PathVariable int id) {
        try {
            return ResponseEntity.ok(operacionService.detener(id));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/reanudar")
    public ResponseEntity<?> reanudar(@PathVariable int id) {
        try {
            return ResponseEntity.ok(operacionService.reanudar(id));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizar(@PathVariable int id) {
        try {
            return ResponseEntity.ok(operacionService.finalizar(id));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }
}