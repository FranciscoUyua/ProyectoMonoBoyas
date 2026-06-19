package Persistencia;

import Usuarios.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioDAO {

    private final JdbcTemplate jdbc;

    public UsuarioDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Guarda cualquier subclase de Usuario.
     */
    public void guardar(Usuario usuario) {
        jdbc.update(
            "INSERT INTO usuarios (id, nombre, contrasena, dni, rol) VALUES (?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING",
            usuario.getId(),
            usuario.getNombre(),
            usuario.getContrasena(),
            usuario.getDni(),
            usuario.getRol()
        );
    }

    public Usuario buscarPorDni(int dni) {
        return jdbc.queryForObject(
            "SELECT * FROM usuarios WHERE dni = ?",
            (rs, rowNum) -> instanciarUsuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("contrasena"),
                rs.getInt("dni"),
                rs.getString("rol")
            ),
            dni
        );
    }

    public Usuario buscarPorId(int id) {
        return jdbc.queryForObject(
            "SELECT * FROM usuarios WHERE id = ?",
            (rs, rowNum) -> instanciarUsuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("contrasena"),
                rs.getInt("dni"),
                rs.getString("rol")
            ),
            id
        );
    }

    public List<Usuario> listarTodos() {
        return jdbc.query(
            "SELECT * FROM usuarios",
            (rs, rowNum) -> instanciarUsuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("contrasena"),
                rs.getInt("dni"),
                rs.getString("rol")
            )
        );
    }

    public List<Usuario> listarPorRol(String rol) {
        return jdbc.query(
            "SELECT * FROM usuarios WHERE rol = ?",
            (rs, rowNum) -> instanciarUsuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("contrasena"),
                rs.getInt("dni"),
                rs.getString("rol")
            ),
            rol
        );
    }

    public void actualizar(Usuario usuario) {
        jdbc.update(
            "UPDATE usuarios SET nombre = ?, contrasena = ? WHERE id = ?",
            usuario.getNombre(),
            usuario.getContrasena(),
            usuario.getId()
        );
    }

    public void eliminar(int id) {
        jdbc.update(
            "DELETE FROM usuarios WHERE id = ?",
            id
        );
    }

    /**
     * Reconstruye la subclase correspondiente según el rol almacenado en la BD.
     */
    private Usuario instanciarUsuario(int id, String nombre, String contrasena, int dni, String rol) {

        return switch (rol) {
            case "ADMIN" ->
                new Administrador(id, nombre, contrasena, dni);

            case "OPERADOR_LANCHA" ->
                new OperadorLancha(id, nombre, contrasena, dni);

            case "OPERADOR_BUQUE" ->
                new OperadorBuque(id, nombre, contrasena, dni);

            case "OPERADOR_PLANTA" ->
                new OperadorPlanta(id, nombre, contrasena, dni);

            default ->
                throw new IllegalArgumentException("Rol desconocido: " + rol);
        };
    }
}