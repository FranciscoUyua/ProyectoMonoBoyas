package Main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.monoboyas.api.TelemetriaService;

import Central.CentralDatos;
import Equipamiento.Buque;
import Equipamiento.Monoboya;
import Operaciones.Operacion;
import Persistencia.BuqueDAO;
import Persistencia.MonoboyaDAO;
import Persistencia.OperacionDAO;
import Persistencia.PlantaDAO;
import Persistencia.SensorDAO;
import Persistencia.UsuarioDAO;
import Sensores.Medicion;
import Sensores.Medicion.OrigenMedicion;
import Sensores.MockSensorDataProvider;
import Sensores.Sensor;
import Sensores.SensorDeAmarre;
import Usuarios.OperadorBuque;
import Usuarios.OperadorLancha;
import Usuarios.OperadorPlanta;

@SpringBootApplication
@ComponentScan({"Main", "Persistencia", "com.monoboyas.api"})
public class TestTelemetria implements CommandLineRunner {

    @Autowired private PlantaDAO plantaDAO;
    @Autowired private BuqueDAO buqueDAO;
    @Autowired private MonoboyaDAO monoboyaDAO;
    @Autowired private UsuarioDAO usuarioDAO;
    @Autowired private SensorDAO sensorDAO;
    @Autowired private OperacionDAO operacionDAO;
    @Autowired private CentralDatos centralDatos;
    @Autowired private TelemetriaService telemetriaService;

    public static void main(String[] args) {
        SpringApplication.run(TestTelemetria.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Test: pipeline completo de telemetría (Amarre crítico) ===");

        int plantaId = 501;
        int buqueNroIMO = 5551234;
        int monoboyaId = 501;
        int sensorId = 501;
        int operadorBuqueId = 501;
        int operadorLanchaId = 502;
        int operadorPlantaId = 503;

        plantaDAO.guardar(plantaId, "Planta Test Telemetria");

        Buque buque = new Buque(buqueNroIMO, 50000, "Buque Test", null);
        buqueDAO.guardar(buque);

        Monoboya monoboya = new Monoboya(monoboyaId, 5, null, null);
        monoboyaDAO.guardar(monoboya);

        Sensor sensorAmarre = new SensorDeAmarre(sensorId, new MockSensorDataProvider());
        sensorDAO.guardar(sensorAmarre, monoboyaId);

        OperadorBuque opBuque = new OperadorBuque(operadorBuqueId, "Capitán Test", "1234", 11111111);
        OperadorLancha opLancha = new OperadorLancha(operadorLanchaId, "Lanchero Test", "1234", 22222222);
        OperadorPlanta opPlanta = new OperadorPlanta(operadorPlantaId, "Operador Planta Test", "1234", 33333333);
        usuarioDAO.guardar(opBuque);
        usuarioDAO.guardar(opLancha);
        usuarioDAO.guardar(opPlanta);

        // Operación: en BD y en dominio, con el MISMO id
        int operacionId = operacionDAO.crearPlanificada(buqueNroIMO, plantaId, "DESCARGA", operadorBuqueId);
        operacionDAO.actualizarParaPreparar(operacionId, monoboyaId, operadorPlantaId, operadorLanchaId);
        System.out.println("✔ Operación creada en BD: ID=" + operacionId);

        Operacion operacionDominio = new Operacion(operacionId, buque, opBuque);
        operacionDominio.asignarMonoboya(monoboya);
        operacionDominio.asignarOperadorLancha(opLancha);
        operacionDominio.asignarOperadorPlanta(opPlanta);

        centralDatos.iniciarOperacion(operacionDominio);
        System.out.println("✔ Operación registrada en CentralDatos.operacionesActivas");

        // Medición que supera AMARRE_ROJA (900 kN)
        Medicion medicion = new Medicion(sensorId, 950.0, "kN", Sensor.TipoSensor.AMARRE, OrigenMedicion.MONOBOYA, operacionId);

        TelemetriaService.TelemetriaResultado resultado = telemetriaService.procesarMedicion(medicion);

        System.out.println("\n=== Resultado ===");
        System.out.println("Medición guardada con ID: " + resultado.getMedicionId());
        System.out.println("¿Generó alertas? " + resultado.tieneAlertas());
        for (var info : resultado.getAlertas()) {
            System.out.println("  → Alerta ID " + info.getId() + " | Tipo: " + info.getTipoAlerta()
                + " | Mensaje: " + info.getMensaje());
        }
    }
}