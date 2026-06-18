package Main;

import Persistencia.*;
import Equipamiento.*;
import Usuarios.*;
import Sensores.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Test de integración con la base de datos PostgreSQL (Neon).
 * Ejecuta operaciones CRUD para verificar que la capa de persistencia funciona.
 * 
 * Ejecutar con: mvn spring-boot:run -Dspring-boot.run.mainClass=Main.TestPersistencia
 */
@SpringBootApplication
@ComponentScan({"Main", "Persistencia"})
public class TestPersistencia implements CommandLineRunner {

    @Autowired private PlantaDAO plantaDAO;
    @Autowired private MonoboyaDAO monoboyaDAO;
    @Autowired private BuqueDAO buqueDAO;
    @Autowired private UsuarioDAO usuarioDAO;
    @Autowired private SensorDAO sensorDAO;
    @Autowired private MedicionDAO medicionDAO;
    @Autowired private AlertaDAO alertaDAO;

    public static void main(String[] args) {
        SpringApplication.run(TestPersistencia.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("   TEST DE PERSISTENCIA - PostgreSQL (Neon)");
        System.out.println("══════════════════════════════════════════════════════\n");

        try {
            testPlanta();
            testBuque();
            testUsuarios();
            testMonoboya();
            testMediciones();

            System.out.println("\n══════════════════════════════════════════════════════");
            System.out.println("   ✅ TODOS LOS TESTS PASARON CORRECTAMENTE");
            System.out.println("══════════════════════════════════════════════════════\n");
        } catch (Exception e) {
            System.err.println("\n❌ ERROR EN TEST: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void testPlanta() {
        System.out.println("── Test: Planta ─────────────────────────────────────");
        plantaDAO.guardar(1, "Planta San Lorenzo");
        PlantaDAO.PlantaInfo planta = plantaDAO.buscarPorId(1);
        System.out.println("  ✔ Planta guardada y leída: ID=" + planta.getId());

        List<PlantaDAO.PlantaInfo> todas = plantaDAO.listarTodas();
        System.out.println("  ✔ Total plantas en BD: " + todas.size());
    }

    private void testBuque() {
        System.out.println("── Test: Buque ──────────────────────────────────────");
        Buque buque = new Buque(9876543, 50000, "MT Concordia");
        buqueDAO.guardar(buque);

        Buque leido = buqueDAO.buscarPorNroIMO(9876543);
        System.out.println("  ✔ Buque guardado y leído: " + leido.getNombre() + " (IMO: " + leido.getNroIMO() + ")");
    }

    private void testUsuarios() {
        System.out.println("── Test: Usuarios ───────────────────────────────────");
        
        OperadorLancha opLancha = new OperadorLancha(1, "Juan (Lancha)", "123", 11111111);
        OperadorBuque opBuque = new OperadorBuque(2, "Capitán Smith", "456", 22222222);
        OperadorPlanta opPlanta = new OperadorPlanta(3, "Pedro (Sala Control)", "789", 33333333);
        Administrador admin = new Administrador(4, "Admin Sistema", "admin", 99999999);

        usuarioDAO.guardar(opLancha);
        usuarioDAO.guardar(opBuque);
        usuarioDAO.guardar(opPlanta);
        usuarioDAO.guardar(admin);

        Usuario leido = usuarioDAO.buscarPorDni(11111111);
        System.out.println("  ✔ Usuario guardado y leído: " + leido.getNombre() + " (tipo: " + leido.getClass().getSimpleName() + ")");

        List<Usuario> todos = usuarioDAO.listarTodos();
        System.out.println("  ✔ Total usuarios en BD: " + todos.size());
    }

    private void testMonoboya() {
        System.out.println("── Test: Monoboya ───────────────────────────────────");
        monoboyaDAO.guardar(101, 8, 1); // id=101, capacidad=8, planta_id=1

        Monoboya leida = monoboyaDAO.buscarPorId(101);
        System.out.println("  ✔ Monoboya guardada y leída: ID=" + leida.getId() + ", capacidad=" + leida.getSensores().length);
    }

    private void testMediciones() {
        System.out.println("── Test: Mediciones (batch) ─────────────────────────");
        
        // Primero guardar un sensor para la foreign key
        SensorDePresion sensor = new SensorDePresion(1, new MockSensorDataProvider());
        sensorDAO.guardar(sensor, 101); // sensor id=1, monoboya_id=101

        // Crear un lote de mediciones
        List<Medicion> lote = List.of(
            new Medicion(1, 100.5, "Pa"),
            new Medicion(1, 101.2, "Pa"),
            new Medicion(1, 99.8, "Pa"),
            new Medicion(1, 102.0, "Pa"),
            new Medicion(1, 98.5, "Pa")
        );

        medicionDAO.guardarLote(lote);
        System.out.println("  ✔ Lote de " + lote.size() + " mediciones insertado");

        List<MedicionDAO.MedicionInfo> leidas = medicionDAO.listarPorSensor(1, 10);
        System.out.println("  ✔ Mediciones leídas de BD: " + leidas.size());
        for (MedicionDAO.MedicionInfo m : leidas) {
            System.out.println("    → Sensor " + m.getSensorId() + " | Valor: " + m.getValor() + " " + m.getUnidad() + " | " + m.getTimestamp());
        }
    }
}
