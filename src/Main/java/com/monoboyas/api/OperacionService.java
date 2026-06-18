package com.monoboyas.api;
import Persistencia.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperacionService {

    private static final String PLANIFICADA = "PLANIFICADA";
    private static final String PREPARADA   = "PREPARADA";
    private static final String EN_CURSO    = "EN_CURSO";
    private static final String DETENIDA    = "DETENIDA";
    private static final String FINALIZADA  = "FINALIZADA";

    private final OperacionDAO operacionDAO;
    private final MonoboyaDAO  monoboyaDAO;

    public OperacionService(OperacionDAO operacionDAO, MonoboyaDAO monoboyaDAO) {
        this.operacionDAO = operacionDAO;
        this.monoboyaDAO  = monoboyaDAO;
    }

    // ── TRANSICIONES DE ESTADO ───────────────────────────────────────────

    public OperacionDAO.OperacionInfo planificar(int buqueId, int plantaId, String tipo) {
        int id = operacionDAO.crearPlanificada(buqueId, plantaId, tipo);
        return operacionDAO.buscarPorId(id);
    }

    public OperacionDAO.OperacionInfo preparar(int operacionId, int monoboyaId,
                                               int operadorPlantaId, int operadorLanchaId) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        requireEstado(op, PLANIFICADA, "preparar");
        operacionDAO.actualizarParaPreparar(operacionId, monoboyaId, operadorPlantaId, operadorLanchaId);
        return operacionDAO.buscarPorId(operacionId);
    }

    public OperacionDAO.OperacionInfo iniciar(int operacionId, int operadorBuqueId) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        requireEstado(op, PREPARADA, "iniciar");

        boolean monoboyaOcupada = operacionDAO.listarPorEstado(EN_CURSO).stream()
            .anyMatch(o -> op.getMonoboyaId() != null && op.getMonoboyaId().equals(o.getMonoboyaId()));
        if (monoboyaOcupada) {
            throw new IllegalStateException(
                "La monoboya " + op.getMonoboyaId() + " ya tiene una operación EN_CURSO");
        }

        operacionDAO.actualizarParaIniciar(operacionId, operadorBuqueId);
        if (op.getMonoboyaId() != null) {
            monoboyaDAO.actualizarOperacionActiva(op.getMonoboyaId(), operacionId);
            monoboyaDAO.actualizarEstado(op.getMonoboyaId(), "OCUPADA");
        }
        return operacionDAO.buscarPorId(operacionId);
    }

    public OperacionDAO.OperacionInfo detener(int operacionId) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        requireEstado(op, EN_CURSO, "detener");
        operacionDAO.actualizarEstado(operacionId, DETENIDA);
        return operacionDAO.buscarPorId(operacionId);
    }

    public OperacionDAO.OperacionInfo reanudar(int operacionId) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        requireEstado(op, DETENIDA, "reanudar");
        operacionDAO.actualizarEstado(operacionId, EN_CURSO);
        return operacionDAO.buscarPorId(operacionId);
    }

    public OperacionDAO.OperacionInfo finalizar(int operacionId) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        if (FINALIZADA.equals(op.getEstado())) {
            throw new IllegalStateException(
                "La operación " + operacionId + " ya está FINALIZADA y no puede modificarse");
        }
        if (!EN_CURSO.equals(op.getEstado()) && !DETENIDA.equals(op.getEstado())) {
            throw new IllegalStateException(
                "Solo se puede finalizar una operación EN_CURSO o DETENIDA. Estado actual: " + op.getEstado());
        }
        operacionDAO.actualizarEstado(operacionId, FINALIZADA);
        if (op.getMonoboyaId() != null) {
            monoboyaDAO.actualizarOperacionActiva(op.getMonoboyaId(), null);
            monoboyaDAO.actualizarEstado(op.getMonoboyaId(), "DISPONIBLE");
        }
        return operacionDAO.buscarPorId(operacionId);
    }

    // ── CONSULTAS ────────────────────────────────────────────────────────

    public List<OperacionDAO.OperacionInfo> obtenerActivas() {
        return operacionDAO.listarPorEstado(EN_CURSO);
    }

    public OperacionDAO.OperacionInfo obtenerPorId(int id) {
        return operacionDAO.buscarPorId(id);
    }

    public List<OperacionDAO.OperacionInfo> obtenerTodas() {
        return operacionDAO.listarTodas();
    }

    // ── VALIDACIÓN ───────────────────────────────────────────────────────

    private void requireEstado(OperacionDAO.OperacionInfo op, String requerido, String accion) {
        if (FINALIZADA.equals(op.getEstado())) {
            throw new IllegalStateException(
                "La operación " + op.getId() + " ya está FINALIZADA y no puede modificarse");
        }
        if (!requerido.equals(op.getEstado())) {
            throw new IllegalStateException(
                "No se puede " + accion + " la operación " + op.getId() +
                ": estado actual " + op.getEstado() + ", se requiere " + requerido);
        }
    }
}
