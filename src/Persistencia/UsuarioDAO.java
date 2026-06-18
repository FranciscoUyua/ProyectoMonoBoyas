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
     * El rol se determina automáticamente por el tipo de objeto.
     */
    public void guardar(Usuario usuario) {
        String rol = determinarRol(usuario);
        jdbc.update(
            "INSERT INTO usuarios (id, nombre, contrasena, dni, rol) VALUES (?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING",
            usuario.getId(), usuario.getNombre(), usuario.getContrasena(), usuario.getDni(), rol
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
            usuario.getNombre(), usuario.getContrasena(), usuario.getId()
        );
    }

    public void eliminar(int id) {
        jdbc.update("DELETE FROM usuarios WHERE id = ?", id);
    }

    // ── Helpers privados ────────────────────────────────────────────

    /**
     * Determina el rol (discriminador) según el tipo concreto de Usuario.
     */
    private String determinarRol(Usuario usuario) {
        if (usuario instanceof Administrador) return "ADMIN";
        if (usuario instanceof OperadorLancha) return "OP_LANCHA";
        if (usuario instanceof OperadorBuque) return "OP_BUQUE";
        if (usuario instanceof OperadorPlanta) return "OP_PLANTA";
        return "USUARIO";
    }

    /**
     * Reconstruye la subclase correcta de Usuario según el rol almacenado en BD.
     */
    private Usuario instanciarUsuario(int id, String nombre, String contrasena, int dni, String rol) {
        switch (rol) {
            case "ADMIN":
                return new Administrador(id, nombre, contrasena, dni);
            case "OP_LANCHA":
                return new OperadorLancha(id, nombre, contrasena, dni);
            case "OP_BUQUE":
                return new OperadorBuque(id, nombre, contrasena, dni);
            case "OP_PLANTA":
                return new OperadorPlanta(id, nombre, contrasena, dni);
            default:
                // Fallback: no se puede instanciar Usuario directamente (es abstract),
                // así que usamos Administrador como default
                return new Administrador(id, nombre, contrasena, dni);
        }
    }
}
