package com.monoboyas.api;

import java.util.List;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.monoboyas.persistencia.OperacionDAO;
import com.monoboyas.persistencia.SensorDAO;
import com.monoboyas.sensores.Medicion;
import com.monoboyas.sensores.Sensor;

import com.monoboyas.sensores.*;
import java.util.Map;
import java.util.HashMap;

@Component
public class TelemetriaScheduler {

    private final TelemetriaService telemetriaService;
    private final OperacionDAO operacionDAO;
    private final SensorDAO sensorDAO;
    private final Random random = new Random();
    private final Map<String, ISensorDataProvider> providers = new HashMap<>();
    private final Map<String, Double> ultimosValores = new HashMap<>();

    public TelemetriaScheduler(TelemetriaService telemetriaService,
            OperacionDAO operacionDAO, SensorDAO sensorDAO) {
        this.telemetriaService = telemetriaService;
        this.operacionDAO = operacionDAO;
        this.sensorDAO = sensorDAO;

        // Inicializar proveedores (APIs web y archivos locales)
        providers.put("PRESION", new ArchivoDataProvider("presion.txt"));
        providers.put("OLEAJE", new ApiOleajeProvider());
        providers.put("TENSION", new ArchivoDataProvider("tension.txt"));
        providers.put("VIENTO", new ApiVientoProvider());
        providers.put("CORRIENTE", new ApiCorrienteProvider());
        providers.put("CAUDAL", new ArchivoDataProvider("caudal.txt"));
        providers.put("ORIENTACION", new ArchivoDataProvider("giroscopio.txt"));
        providers.put("AMARRE", new ArchivoDataProvider("amarre.txt"));
    }

    @Scheduled(fixedRate = 3000) // cada 3 segundos
    public void generarLecturas() {
        // Buscar operaciones activas
        List<OperacionDAO.OperacionInfo> activas = operacionDAO.listarPorEstado("ACTIVA");
        if (activas.isEmpty())
            return;

        for (OperacionDAO.OperacionInfo op : activas) {
            if (op.getMonoboyaId() == null)
                continue;

            List<SensorDAO.SensorInfo> sensores = sensorDAO.listarPorMonoboya(op.getMonoboyaId());

            for (SensorDAO.SensorInfo sensor : sensores) {
                double valor;
                ISensorDataProvider provider = providers.get(sensor.getTipo());

                if (provider != null) {
                    try {
                        valor = provider.obtenerDato();
                        ultimosValores.put(sensor.getTipo(), valor); // Guardar caché
                    } catch (Exception e) {
                        System.err.println("Error leyendo " + sensor.getTipo() + ": " + e.getMessage());
                        // Si falla la API o se acaba el archivo, usar el último valor o fallback
                        valor = ultimosValores.getOrDefault(sensor.getTipo(), generarValorSimulado(sensor.getTipo()));
                    }
                } else {
                    valor = generarValorSimulado(sensor.getTipo());
                }

                Sensor.TipoSensor tipo = Sensor.TipoSensor.valueOf(sensor.getTipo());

                Medicion medicion = new Medicion(
                        sensor.getId(), valor, sensor.getUnidad(),
                        tipo, Medicion.OrigenMedicion.MONOBOYA, op.getId());

                telemetriaService.procesarMedicion(medicion);
            }
        }
    }

    private double generarValorSimulado(String tipo) {
        return switch (tipo) {
            case "PRESION" -> 8.5 + random.nextDouble() * 3; // 8.5 - 11.5 bar
            case "VIENTO" -> 12 + random.nextDouble() * 15; // 12 - 27 kn
            case "OLEAJE" -> 0.8 + random.nextDouble() * 1.5; // 0.8 - 2.3 m
            case "CORRIENTE" -> 0.8 + random.nextDouble() * 1.8; // 0.8 - 2.6 kn
            case "CAUDAL" -> 900 + random.nextDouble() * 400; // 900 - 1300 m3/h
            case "AMARRE" -> 35 + random.nextDouble() * 30; // 35 - 65 t
            default -> 50 + random.nextDouble() * 50;
        };
    }
}