package Persistencia;

import Equipamiento.Monoboya;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MonoboyaDAO {

    private final JdbcTemplate jdbc;

    public MonoboyaDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Guarda desde el objeto de dominio (sin operacion_activa_id ni planta_id)
    public void guardar(Monoboya m) {
        jdbc.update(
            "INSERT INTO monoboyas (id) VALUES (?) ON CONFLICT (id) DO NOTHING",
            m.getId()
        );
    }

    // Overload sin operacionActivaId
    public void guardar(int id, Integer plantaId) {
        guardar(id, plantaId, null);
    }

    // Guarda con todos los campos conocidos en el momento de la inserción
    public void guardar(int id, Integer plantaId, Integer operacionActivaId) {
        jdbc.update(
            "INSERT INTO monoboyas (id, planta_id, operacion_activa_id) " +
            "VALUES (?, ?, ?) ON CONFLICT (id) DO NOTHING",
            id, plantaId, operacionActivaId
        );
    }

    public void actualizarOperacionActiva(int monoboyaId, Integer operacionId) {
        jdbc.update(
            "UPDATE monoboyas SET operacion_activa_id = ? WHERE id = ?",
            operacionId, monoboyaId
        );
    }

    public void actualizarEstado(int monoboyaId, String estado) {
        jdbc.update(
            "UPDATE monoboyas SET estado = ? WHERE id = ?",
            estado, monoboyaId
        );
    }

    public Monoboya buscarPorId(int id) {
        return jdbc.queryForObject(
            "SELECT * FROM monoboyas WHERE id = ?",
            (rs, rowNum) -> new Monoboya(
                rs.getInt("id"),
                0,    // capacidad no se persiste; sensores se cargan aparte si se necesitan
                null, // operacion — se carga aparte si se necesita
                null  // publisher — se asigna en runtime, no se persiste
            ),
            id
        );
    }

    // Devuelve el ID de la operación activa, o null si no hay ninguna
    public Integer buscarOperacionActivaId(int monoboyaId) {
        return jdbc.queryForObject(
            "SELECT operacion_activa_id FROM monoboyas WHERE id = ?",
            (rs, rowNum) -> {
                int val = rs.getInt("operacion_activa_id");
                return rs.wasNull() ? null : val;
            },
            monoboyaId
        );
    }

    public List<Monoboya> listarTodas() {
        return jdbc.query(
            "SELECT * FROM monoboyas",
            (rs, rowNum) -> new Monoboya(rs.getInt("id"), 0, null, null)
        );
    }

    public List<Monoboya> listarPorPlanta(int plantaId) {
        return jdbc.query(
            "SELECT * FROM monoboyas WHERE planta_id = ?",
            (rs, rowNum) -> new Monoboya(rs.getInt("id"), 0, null, null),
            plantaId
        );
    }

    public void eliminar(int id) {
        jdbc.update("DELETE FROM monoboyas WHERE id = ?", id);
    }

    public List<MonoboyaInfo> listarTodasInfo() {
            return jdbc.query(
                "SELECT id, estado, planta_id, operacion_activa_id FROM monoboyas",
                (rs, rowNum) -> new MonoboyaInfo(
                    rs.getInt("id"),
                    rs.getString("estado"),
                    rs.getObject("planta_id", Integer.class),
                    rs.getObject("operacion_activa_id", Integer.class)
                )
            );
    }

        public MonoboyaInfo buscarInfoPorId(int id) {
            return jdbc.queryForObject(
                "SELECT id, estado, planta_id, operacion_activa_id FROM monoboyas WHERE id = ?",
                (rs, rowNum) -> new MonoboyaInfo(
                    rs.getInt("id"),
                    rs.getString("estado"),
                    rs.getObject("planta_id", Integer.class),
                    rs.getObject("operacion_activa_id", Integer.class)
                ),
                id
            );
    }

        public static class MonoboyaInfo {
            private final int id;
            private final String estado;
            private final Integer plantaId;
            private final Integer operacionActivaId;

            public MonoboyaInfo(int id, String estado, Integer plantaId, Integer operacionActivaId) {
                this.id = id;
                this.estado = estado;
                this.plantaId = plantaId;
                this.operacionActivaId = operacionActivaId;
            }

            public int getId() { return id; }
            public String getEstado() { return estado; }
            public Integer getPlantaId() { return plantaId; }
            public Integer getOperacionActivaId() { return operacionActivaId; }
        }
}
