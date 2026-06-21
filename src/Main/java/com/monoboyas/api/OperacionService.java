package com.monoboyas.api;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monoboyas.persistencia.MonoboyaDAO;
import com.monoboyas.persistencia.OperacionDAO;
import com.monoboyas.persistencia.UsuarioDAO;
import com.monoboyas.usuarios.Usuario;

@Service
public class OperacionService {

    private static final String PLANIFICADA = "PLANIFICADA";
    private static final String PREPARADA   = "PREPARADA";
    private static final String ACTIVA      = "ACTIVA";   // antes EN_CURSO
    private static final String PAUSADA     = "PAUSADA";  // antes DETENIDA
    private static final String FINALIZADA  = "FINALIZADA";

    private final OperacionDAO operacionDAO;
    private final MonoboyaDAO  monoboyaDAO;
    private final UsuarioDAO   usuarioDAO;

    public OperacionService(OperacionDAO operacionDAO, MonoboyaDAO monoboyaDAO, UsuarioDAO usuarioDAO) {
        this.operacionDAO = operacionDAO;
        this.monoboyaDAO  = monoboyaDAO;
        this.usuarioDAO   = usuarioDAO;
    }

    // ── TRANSICIONES DE ESTADO ───────────────────────────────────────────

    public OperacionDAO.OperacionInfo planificar(int buqueNroIMO, int plantaId, String tipo) {
    Usuario operadorBuque = usuarioDAO.primerOperadorBuqueDisponible()
        .orElseThrow(() -> new IllegalStateException("No hay operadores de buque disponibles"));
    int id = operacionDAO.crearPlanificada(buqueNroIMO, plantaId, tipo, operadorBuque.getId());
    return operacionDAO.buscarPorId(id);
}

    public OperacionDAO.OperacionInfo preparar(int operacionId, int monoboyaId,
                                               int operadorPlantaDni, int operadorLanchaDni) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        requireEstado(op, PLANIFICADA, "preparar");

        Usuario operadorPlanta = validarYObtenerOperador(operadorPlantaDni, "OPERADOR_PLANTA");
        Usuario operadorLancha = validarYObtenerOperador(operadorLanchaDni, "OPERADOR_LANCHA");

        operacionDAO.actualizarParaPreparar(operacionId, monoboyaId, operadorPlanta.getId(), operadorLancha.getId());
        return operacionDAO.buscarPorId(operacionId);
    }

    public OperacionDAO.OperacionInfo iniciar(int operacionId, int operadorLanchaDni) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        requireEstado(op, PREPARADA, "iniciar");

        // Solo confirma que quien da el visto bueno tiene el rol correcto.
        // No asigna a nadie nuevo: ya quedó todo asignado en preparar().
        validarYObtenerOperador(operadorLanchaDni, "OPERADOR_LANCHA");

        boolean monoboyaOcupada = operacionDAO.listarPorEstado(ACTIVA).stream()
            .anyMatch(o -> op.getMonoboyaId() != null && op.getMonoboyaId().equals(o.getMonoboyaId()));
        if (monoboyaOcupada) {
            throw new IllegalStateException(
                "La monoboya " + op.getMonoboyaId() + " ya tiene una operación ACTIVA");
        }

        operacionDAO.actualizarEstado(operacionId, ACTIVA);
        if (op.getMonoboyaId() != null) {
            monoboyaDAO.actualizarOperacionActiva(op.getMonoboyaId(), operacionId);
            monoboyaDAO.actualizarEstado(op.getMonoboyaId(), "OCUPADA");
        }
        return operacionDAO.buscarPorId(operacionId);
    }

    public OperacionDAO.OperacionInfo detener(int operacionId, int operadorBuqueDni) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        requireEstado(op, ACTIVA, "detener");
        validarYObtenerOperador(operadorBuqueDni, "OPERADOR_BUQUE");
        operacionDAO.actualizarEstado(operacionId, PAUSADA);
        return operacionDAO.buscarPorId(operacionId);
    }

    public OperacionDAO.OperacionInfo reanudar(int operacionId, int operadorBuqueDni) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        requireEstado(op, PAUSADA, "reanudar");
        validarYObtenerOperador(operadorBuqueDni, "OPERADOR_BUQUE");
        operacionDAO.actualizarEstado(operacionId, ACTIVA);
        return operacionDAO.buscarPorId(operacionId);
    }

    public OperacionDAO.OperacionInfo finalizar(int operacionId) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        if (FINALIZADA.equals(op.getEstado())) {
            throw new IllegalStateException(
                "La operación " + operacionId + " ya está FINALIZADA y no puede modificarse");
        }
        if (!ACTIVA.equals(op.getEstado()) && !PAUSADA.equals(op.getEstado())) {
            throw new IllegalStateException(
                "Solo se puede finalizar una operación ACTIVA o PAUSADA. Estado actual: " + op.getEstado());
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
        return operacionDAO.listarPorEstado(ACTIVA);
    }

    public OperacionDAO.OperacionInfo obtenerPorId(int id) {
        return operacionDAO.buscarPorId(id);
    }

    public List<OperacionDAO.OperacionInfo> obtenerTodas() {
        return operacionDAO.listarTodas();
    }

    // ── VALIDACIÓN ───────────────────────────────────────────────────────

    private Usuario validarYObtenerOperador(int dni, String rolEsperado) {
        Usuario usuario = usuarioDAO.buscarPorDni(dni);
        if (!rolEsperado.equals(usuario.getRol())) {
            throw new IllegalArgumentException(
                "El usuario con DNI " + dni + " tiene rol " + usuario.getRol() +
                ", se requiere " + rolEsperado);
        }
        return usuario;
    }

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