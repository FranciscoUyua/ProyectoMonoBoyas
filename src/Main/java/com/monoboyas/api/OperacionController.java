package com.monoboyas.api;

import Persistencia.OperacionDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import Persistencia.BuqueDAO;
import Persistencia.PlantaDAO;
import Persistencia.MonoboyaDAO;
import java.util.stream.Collectors;
import Persistencia.UsuarioDAO;
import Usuarios.Usuario;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/operaciones")
public class OperacionController {

    private final OperacionDAO operacionDAO;
    private final OperacionService operacionService;
    private final OperacionDAO operacionDAO;
    private final OperacionService operacionService;
    private final BuqueDAO buqueDAO;
    private final PlantaDAO plantaDAO;
    private final UsuarioDAO usuarioDAO;
    private final MonoboyaDAO monoboyaDAO;

    public OperacionController(OperacionDAO operacionDAO, OperacionService operacionService) {
        this.operacionDAO = operacionDAO;
        this.operacionService = operacionService;
        this.operacionDAO = operacionDAO;
        this.buqueDAO = buqueDAO;
        this.plantaDAO = plantaDAO;
        this.usuarioDAO = usuarioDAO;
        this.monoboyaDAO = monoboyaDAO;
    }

    @GetMapping
    public Map<String, Object> listar(@RequestParam(required = false) String estado) {
        List<OperacionDAO.OperacionInfo> operaciones = (estado != null)
            ? operacionDAO.listarPorEstado(estado)
            : operacionDAO.listarTodas();

        return Map.of(
            "data", operaciones,
            "pagination", Map.of(
                "page", 1, "limit", 20, "total", operaciones.size(), "totalPages", 1
            )
        );
    }

    @PostMapping
    public ResponseEntity<?> planificar(@RequestBody Map<String, Object> body) {
        try {
            int buqueNroIMO      = (int) body.get("buqueNroIMO");
            int plantaId         = (int) body.get("plantaId");
            String tipo          = (String) body.get("tipo");
            int operadorBuqueDni = (int) body.get("operadorBuqueDni");
            return ResponseEntity.status(201).body(
                operacionService.planificar(buqueNroIMO, plantaId, tipo, operadorBuqueDni)
            );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/preparar")
    public ResponseEntity<?> preparar(@PathVariable int id, @RequestBody Map<String, Object> body) {
        try {
            int monoboyaId        = (int) body.get("monoboyaId");
            int operadorPlantaDni = (int) body.get("operadorPlantaDni");
            int operadorLanchaDni = (int) body.get("operadorLanchaDni");
            return ResponseEntity.ok(
                operacionService.preparar(id, monoboyaId, operadorPlantaDni, operadorLanchaDni)
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
            int operadorLanchaDni = (int) body.get("operadorLanchaDni");
            return ResponseEntity.ok(operacionService.iniciar(id, operadorLanchaDni));
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

    @GetMapping("/opciones-planificacion")
    public Map<String, Object> opcionesPlanificacion() {
        List<Map<String, Object>> buques = buqueDAO.listarTodos().stream()
            .map(b -> Map.<String, Object>of(
                "nroIMO", b.getNroIMO(), "nombre", b.getNombre(), "capacidad", b.getCapacidad()))
            .collect(Collectors.toList());

        List<Map<String, Object>> plantas = plantaDAO.listarTodas().stream()
            .map(p -> Map.<String, Object>of("id", p.getId(), "nombre", p.getNombre()))
            .collect(Collectors.toList());

        List<Map<String, Object>> operadoresBuque = usuarioDAO.listarPorRol("OPERADOR_BUQUE").stream()
            .map(u -> Map.<String, Object>of("dni", u.getDni(), "nombre", u.getNombre()))
            .collect(Collectors.toList());

        return Map.of("buques", buques, "plantas", plantas, "operadoresBuque", operadoresBuque);
    }
@GetMapping("/mis-operaciones")
    public ResponseEntity<?> misOperaciones(@RequestParam int dni) {
        Usuario u;
        try {
            u = usuarioDAO.buscarPorDni(dni);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", Map.of(
                "code", "USUARIO_NO_ENCONTRADO", "message", "Usuario no encontrado")));
        }

        List<OperacionDAO.OperacionInfo> propias = operacionDAO.listarTodas().stream()
            .filter(o ->
                (o.getOperadorLanchaId() != null && o.getOperadorLanchaId() == u.getId()) ||
                (o.getOperadorBuqueId()  != null && o.getOperadorBuqueId()  == u.getId()) ||
                (o.getOperadorPlantaId() != null && o.getOperadorPlantaId() == u.getId())
            )
            .filter(o -> !"FINALIZADA".equals(o.getEstado()))
            .sorted((a, b) -> b.getId() - a.getId())
            .collect(Collectors.toList());

        return ResponseEntity.ok(propias);
    }
}