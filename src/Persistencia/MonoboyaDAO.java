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
            "INSERT INTO monoboyas (id, capacidad_maxima) VALUES (?, ?) ON CONFLICT (id) DO NOTHING",
            m.getId(), m.getSensores().length
        );
    }

    // Overload sin operacionActivaId (usado en TestPersistencia y casos donde aún no hay operación)
    public void guardar(int id, int capacidadMaxima, Integer plantaId) {
        guardar(id, capacidadMaxima, plantaId, null);
    }

    // Guarda con todos los campos conocidos en el momento de la inserción
    public void guardar(int id, int capacidadMaxima, Integer plantaId, Integer operacionActivaId) {
        jdbc.update(
            "INSERT INTO monoboyas (id, capacidad_maxima, planta_id, operacion_activa_id) " +
            "VALUES (?, ?, ?, ?) ON CONFLICT (id) DO NOTHING",
            id, capacidadMaxima, plantaId, operacionActivaId
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
                rs.getInt("capacidad_maxima"),
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
            (rs, rowNum) -> new Monoboya(
                rs.getInt("id"),
                rs.getInt("capacidad_maxima"),
                null, null
            )
        );
    }

    public List<Monoboya> listarPorPlanta(int plantaId) {
        return jdbc.query(
            "SELECT * FROM monoboyas WHERE planta_id = ?",
            (rs, rowNum) -> new Monoboya(
                rs.getInt("id"),
                rs.getInt("capacidad_maxima"),
                null, null
            ),
            plantaId
        );
    }

    public void eliminar(int id) {
        jdbc.update("DELETE FROM monoboyas WHERE id = ?", id);
    }
}
