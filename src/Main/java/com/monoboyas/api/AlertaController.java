package Main.java.com.monoboyas.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Persistencia.AlertaDAO;

@RestController
@RequestMapping("/v1/alertas")
public class AlertaController {

    private final AlertaDAO alertaDAO;

    public AlertaController(AlertaDAO alertaDAO) {
        this.alertaDAO = alertaDAO;
    }

    @GetMapping
    public Map<String, Object> listar(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String estado) {

        List<AlertaDAO.AlertaInfo> alertas = alertaDAO.listarTodas();

        if (tipo != null) {
            alertas = alertas.stream()
                .filter(a -> tipo.equalsIgnoreCase(a.getTipoAlerta()))
                .collect(Collectors.toList());
        }

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