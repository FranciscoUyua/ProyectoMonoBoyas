package Persistencia;

import Sensores.Medicion;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class MedicionDAO {

    private final JdbcTemplate jdbc;

    public MedicionDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Guarda una medición individual y devuelve el ID generado por la BD.
     */
    public int guardar(Medicion medicion) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO mediciones (sensor_id, valor, unidad, timestamp) VALUES (?, ?, ?, ?)",
                new String[]{"id"}
            );
            ps.setInt(1, medicion.getIdSensor());
            ps.setDouble(2, medicion.getValor());
            ps.setString(3, medicion.getUnidad());
            ps.setTimestamp(4, Timestamp.valueOf(medicion.getTimestamp()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Guarda un lote de mediciones en una sola operación.
     * Ideal para el carril rápido de telemetría (8 mediciones/segundo).
     */
    public void guardarLote(List<Medicion> lote) {
        String sql = "INSERT INTO mediciones (sensor_id, valor, unidad, timestamp) VALUES (?, ?, ?, ?)";

        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Medicion m = lote.get(i);
                ps.setInt(1, m.getIdSensor());
                ps.setDouble(2, m.getValor());
                ps.setString(3, m.getUnidad());
                ps.setTimestamp(4, Timestamp.valueOf(m.getTimestamp()));
            }

            @Override
            public int getBatchSize() {
                return lote.size();
            }
        });
    }

    /**
     * Obtiene las últimas N mediciones de un sensor.
     */
    public List<MedicionInfo> listarPorSensor(int sensorId, int limite) {
        return jdbc.query(
            "SELECT * FROM mediciones WHERE sensor_id = ? ORDER BY timestamp DESC LIMIT ?",
            (rs, rowNum) -> new MedicionInfo(
                rs.getInt("id"),
                rs.getInt("sensor_id"),
                rs.getDouble("valor"),
                rs.getString("unidad"),
                rs.getTimestamp("timestamp").toLocalDateTime()
            ),
            sensorId, limite
        );
    }

    /**
     * Obtiene todas las mediciones en un rango de tiempo.
     */
    public List<MedicionInfo> listarPorRangoTiempo(int sensorId, Timestamp desde, Timestamp hasta) {
        return jdbc.query(
            "SELECT * FROM mediciones WHERE sensor_id = ? AND timestamp BETWEEN ? AND ? ORDER BY timestamp DESC",
            (rs, rowNum) -> new MedicionInfo(
                rs.getInt("id"),
                rs.getInt("sensor_id"),
                rs.getDouble("valor"),
                rs.getString("unidad"),
                rs.getTimestamp("timestamp").toLocalDateTime()
            ),
            sensorId, desde, hasta
        );
    }

    /**
     * Clase auxiliar para mediciones leídas de la BD.
     * Agrega el campo 'id' (generado por SERIAL) que la clase Medicion original no tiene.
     */
    public static class MedicionInfo {
        private final int id;
        private final int sensorId;
        private final double valor;
        private final String unidad;
        private final java.time.LocalDateTime timestamp;

        public MedicionInfo(int id, int sensorId, double valor, String unidad, java.time.LocalDateTime timestamp) {
            this.id = id;
            this.sensorId = sensorId;
            this.valor = valor;
            this.unidad = unidad;
            this.timestamp = timestamp;
        }

        public int getId() { return id; }
        public int getSensorId() { return sensorId; }
        public double getValor() { return valor; }
        public String getUnidad() { return unidad; }
        public java.time.LocalDateTime getTimestamp() { return timestamp; }
    }
}
