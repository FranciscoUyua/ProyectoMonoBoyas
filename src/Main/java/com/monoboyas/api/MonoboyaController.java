package com.monoboyas.api;

import Persistencia.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/monoboyas")
public class MonoboyaController {

    private final MonoboyaDAO monoboyaDAO;

    public MonoboyaController(MonoboyaDAO monoboyaDAO) {
        this.monoboyaDAO = monoboyaDAO;
    }

    @GetMapping
    public List<MonoboyaDAO.MonoboyaInfo> listar() {
        return monoboyaDAO.listarTodasInfo();
    }

    @GetMapping("/{id}")
    public MonoboyaDAO.MonoboyaInfo obtener(@PathVariable int id) {
        return monoboyaDAO.buscarInfoPorId(id);
    }
}