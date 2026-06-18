package Persistencia;

import Alertas.Alerta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AlertaDAO {

    private final JdbcTemplate jdbc;

    public AlertaDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void guardar(Alerta alerta, int operacionId, int medicionId) {
        jdbc.update(
            "INSERT INTO alertas (tipo_alerta, mensaje, id_operacion, medicion_id) " +
            "VALUES (?, ?, ?, ?)",
            alerta.getTipoAlerta(),
            alerta.getMensaje(),
            operacionId,
            medicionId
        );
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
}
