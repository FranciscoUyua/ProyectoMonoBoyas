package Persistencia;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class OperacionDAO {

    private final JdbcTemplate jdbc;

    public OperacionDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // ── ESCRITURA ────────────────────────────────────────────────────────

    // Crea una operación en estado PLANIFICADA y devuelve el ID generado
    public int crearPlanificada(int buqueNroIMO, int plantaId, String tipo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO operaciones (buque_nro_imo, planta_id, tipo, estado, esta_activa) " +
                "VALUES (?, ?, ?, 'PLANIFICADA', FALSE)",
                new String[]{"id"}
            );
            ps.setInt(1, buqueNroIMO);
            ps.setInt(2, plantaId);
            ps.setString(3, tipo);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    // Guarda con ID explícito (uso legacy / tests)
    public void guardar(int id, int monoboyaId, Integer buqueNroIMO, int pasajeMonoboyaBarco,
                        Integer operadorLanchaId, Integer operadorBuqueId, Integer operadorPlantaId) {
        jdbc.update(
            "INSERT INTO operaciones (id, monoboya_id, buque_nro_imo, pasaje_monoboya_barco, " +
            "operador_lancha_id, operador_buque_id, operador_planta_id, esta_activa, estado) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, TRUE, 'EN_CURSO') ON CONFLICT (id) DO NOTHING",
            id, monoboyaId, buqueNroIMO, pasajeMonoboyaBarco,
            operadorLanchaId, operadorBuqueId, operadorPlantaId
        );
    }

    public void actualizarParaPreparar(int id, int monoboyaId, int opPlantaId, int opLanchaId) {
        jdbc.update(
            "UPDATE operaciones SET monoboya_id = ?, operador_planta_id = ?, " +
            "operador_lancha_id = ?, estado = 'PREPARADA' WHERE id = ?",
            monoboyaId, opPlantaId, opLanchaId, id
        );
    }

    public void actualizarParaIniciar(int id, int opBuqueId) {
        jdbc.update(
            "UPDATE operaciones SET operador_buque_id = ?, estado = 'EN_CURSO', esta_activa = TRUE WHERE id = ?",
            opBuqueId, id
        );
    }

    public void actualizarEstado(int id, String estado) {
        boolean activa = "EN_CURSO".equals(estado);
        jdbc.update(
            "UPDATE operaciones SET estado = ?, esta_activa = ? WHERE id = ?",
            estado, activa, id
        );
    }

    public void finalizarOperacion(int id) {
        actualizarEstado(id, "FINALIZADA");
    }

    public void eliminar(int id) {
        jdbc.update("DELETE FROM operaciones WHERE id = ?", id);
    }

    // ── CONSULTAS ────────────────────────────────────────────────────────

    public OperacionInfo buscarPorId(int id) {
        return jdbc.queryForObject(
            "SELECT * FROM operaciones WHERE id = ?",
            (rs, rowNum) -> mapRow(rs),
            id
        );
    }

    public List<OperacionInfo> listarPorEstado(String estado) {
        return jdbc.query(
            "SELECT * FROM operaciones WHERE estado = ? ORDER BY id",
            (rs, rowNum) -> mapRow(rs),
            estado
        );
    }

    public List<OperacionInfo> listarActivas() {
        return listarPorEstado("EN_CURSO");
    }

    public List<OperacionInfo> listarTodas() {
        return jdbc.query(
            "SELECT * FROM operaciones ORDER BY id",
            (rs, rowNum) -> mapRow(rs)
        );
    }

    // ── MAPPER ───────────────────────────────────────────────────────────

    private OperacionInfo mapRow(java.sql.ResultSet rs) throws java.sql.SQLException {
        return new OperacionInfo(
            rs.getInt("id"),
            rs.getObject("monoboya_id") != null ? rs.getInt("monoboya_id") : null,
            rs.getObject("buque_nro_imo") != null ? rs.getInt("buque_nro_imo") : null,
            rs.getInt("pasaje_monoboya_barco"),
            rs.getObject("operador_lancha_id") != null ? rs.getInt("operador_lancha_id") : null,
            rs.getObject("operador_buque_id") != null ? rs.getInt("operador_buque_id") : null,
            rs.getObject("operador_planta_id") != null ? rs.getInt("operador_planta_id") : null,
            rs.getBoolean("esta_activa"),
            rs.getString("estado"),
            rs.getString("tipo"),
            rs.getObject("planta_id") != null ? rs.getInt("planta_id") : null
        );
    }

    // ── DTO ──────────────────────────────────────────────────────────────

    public static class OperacionInfo {
        private final int id;
        private final Integer monoboyaId;
        private final Integer buqueNroIMO;
        private final int pasajeMonoboyaBarco;
        private final Integer operadorLanchaId;
        private final Integer operadorBuqueId;
        private final Integer operadorPlantaId;
        private final boolean estaActiva;
        private final String estado;
        private final String tipo;
        private final Integer plantaId;

        public OperacionInfo(int id, Integer monoboyaId, Integer buqueNroIMO, int pasajeMonoboyaBarco,
                             Integer operadorLanchaId, Integer operadorBuqueId, Integer operadorPlantaId,
                             boolean estaActiva, String estado, String tipo, Integer plantaId) {
            this.id = id;
            this.monoboyaId = monoboyaId;
            this.buqueNroIMO = buqueNroIMO;
            this.pasajeMonoboyaBarco = pasajeMonoboyaBarco;
            this.operadorLanchaId = operadorLanchaId;
            this.operadorBuqueId = operadorBuqueId;
            this.operadorPlantaId = operadorPlantaId;
            this.estaActiva = estaActiva;
            this.estado = estado;
            this.tipo = tipo;
            this.plantaId = plantaId;
        }

        public int getId()                    { return id; }
        public Integer getMonoboyaId()        { return monoboyaId; }
        public Integer getBuqueNroIMO()       { return buqueNroIMO; }
        public int getPasajeMonoboyaBarco()   { return pasajeMonoboyaBarco; }
        public Integer getOperadorLanchaId()  { return operadorLanchaId; }
        public Integer getOperadorBuqueId()   { return operadorBuqueId; }
        public Integer getOperadorPlantaId()  { return operadorPlantaId; }
        public boolean isEstaActiva()         { return estaActiva; }
        public String getEstado()             { return estado; }
        public String getTipo()               { return tipo; }
        public Integer getPlantaId()          { return plantaId; }
    }
}
