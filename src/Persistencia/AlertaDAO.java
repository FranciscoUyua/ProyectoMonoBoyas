package Persistencia;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import Alertas.Alerta;

@Repository
public class AlertaDAO {

    private final JdbcTemplate jdbc;

    public AlertaDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Persiste la alerta y devuelve el ID generado por la BD.
     * Necesario para poder vincular usuario_alerta a esta alerta.
     */
    public int guardar(Alerta alerta, int operacionId, int medicionId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO alertas (tipo_alerta, mensaje, id_operacion, medicion_id) " +
                "VALUES (?, ?, ?, ?)",
                new String[]{"id"}
            );
            ps.setString(1, alerta.getTipoAlerta().toString());
            ps.setString(2, alerta.getMensaje());
            ps.setInt(3, operacionId);
            ps.setInt(4, medicionId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public List<AlertaInfo> listarPorOperacion(int operacionId) {
        return jdbc.query(
            "SELECT * FROM alertas WHERE id_operacion = ? ORDER BY timestamp DESC",
            (rs, rowNum) -> new AlertaInfo(
                rs.getInt("id"),
                rs.getString("tipo_alerta"),
                rs.getString("mensaje"),
                rs.getInt("id_operacion"),
                rs.getInt("medicion_id"),
                rs.getTimestamp("timestamp").toLocalDateTime()
            ),
            operacionId
        );
    }

    public List<AlertaInfo> listarTodas() {
        return jdbc.query(
            "SELECT * FROM alertas ORDER BY timestamp DESC",
            (rs, rowNum) -> new AlertaInfo(
                rs.getInt("id"),
                rs.getString("tipo_alerta"),
                rs.getString("mensaje"),
                rs.getInt("id_operacion"),
                rs.getInt("medicion_id"),
                rs.getTimestamp("timestamp").toLocalDateTime()
            )
        );
    }

    public void eliminar(int id) {
        jdbc.update("DELETE FROM alertas WHERE id = ?", id);
    }

    public static class AlertaInfo {
        private final int id;
        private final String tipoAlerta;
        private final String mensaje;
        private final int idOperacion;
        private final int medicionId;
        private final LocalDateTime timestamp;

        public AlertaInfo(int id, String tipoAlerta, String mensaje,
                          int idOperacion, int medicionId, LocalDateTime timestamp) {
            this.id = id;
            this.tipoAlerta = tipoAlerta;
            this.mensaje = mensaje;
            this.idOperacion = idOperacion;
            this.medicionId = medicionId;
            this.timestamp = timestamp;
        }

        public int getId()               { return id; }
        public String getTipoAlerta()    { return tipoAlerta; }
        public String getMensaje()       { return mensaje; }
        public int getIdOperacion()      { return idOperacion; }
        public int getMedicionId()       { return medicionId; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

    public void marcarComoReconocida(int idAlerta) {
    jdbc.update("UPDATE alertas SET estado = 'RECONOCIDA' WHERE id = ?", idAlerta);
}
}
