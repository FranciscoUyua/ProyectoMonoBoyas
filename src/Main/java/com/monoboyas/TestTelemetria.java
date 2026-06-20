package com.monoboyas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.monoboyas.api.TelemetriaService;

import com.monoboyas.central.CentralDatos;
import com.monoboyas.equipamiento.Buque;
import com.monoboyas.equipamiento.Monoboya;
import com.monoboyas.operaciones.Operacion;
import com.monoboyas.persistencia.BuqueDAO;
import com.monoboyas.persistencia.MonoboyaDAO;
import com.monoboyas.persistencia.OperacionDAO;
import com.monoboyas.persistencia.PlantaDAO;
import com.monoboyas.persistencia.SensorDAO;
import com.monoboyas.persistencia.UsuarioDAO;
import com.monoboyas.sensores.Medicion;
import com.monoboyas.sensores.Medicion.OrigenMedicion;
import com.monoboyas.sensores.MockSensorDataProvider;
import com.monoboyas.sensores.Sensor;
import com.monoboyas.sensores.SensorDeAmarre;
import com.monoboyas.usuarios.OperadorBuque;
import com.monoboyas.usuarios.OperadorLancha;
import com.monoboyas.usuarios.OperadorPlanta;

@SpringBootApplication
@ComponentScan({ "Main", "Persistencia", "com.monoboyas.api" })
public class TestTelemetria implements CommandLineRunner {

    @Autowired
    private PlantaDAO plantaDAO;
    @Autowired
    private BuqueDAO buqueDAO;
    @Autowired
    private MonoboyaDAO monoboyaDAO;
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private SensorDAO sensorDAO;
    @Autowired
    private OperacionDAO operacionDAO;
    @Autowired
    private CentralDatos centralDatos;
    @Autowired
    private TelemetriaService telemetriaService;

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

        OperadorBuque opBuque = new OperadorBuque(operadorBuqueId, "Capitán Test", "1234", 111222111);
        OperadorLancha opLancha = new OperadorLancha(operadorLanchaId, "Lanchero Test", "1234", 22232222);
        OperadorPlanta opPlanta = new OperadorPlanta(operadorPlantaId, "Operador Planta Test", "1234", 333433);
        usuarioDAO.guardar(opBuque);
        usuarioDAO.guardar(opLancha);
        usuarioDAO.guardar(opPlanta);

        // Operación: en BD y en dominio, con el MISMO id
        int operacionId = operacionDAO.crearPlanificada(buqueNroIMO, plantaId, "DESCARGA", operadorBuqueId);
        operacionDAO.actualizarParaPreparar(operacionId, monoboyaId, operadorPlantaId, operadorLanchaId);
        System.out.println("✔ Operación creada en BD: ID=" + operacionId);

        Operacion operacionDominio = new Operacion(operacionId, buque, opBuque, null);

        operacionDominio.asignarMonoboya(monoboya);
        operacionDominio.asignarOperadorLancha(opLancha);
        operacionDominio.asignarOperadorPlanta(opPlanta);

        centralDatos.iniciarOperacion(operacionDominio);
        System.out.println("✔ Operación registrada en CentralDatos.operacionesActivas");

        // Medición que supera AMARRE_ROJA (900 kN)
        Medicion medicion = new Medicion(sensorId, 950.0, "kN", Sensor.TipoSensor.AMARRE, OrigenMedicion.MONOBOYA,
                operacionId);

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