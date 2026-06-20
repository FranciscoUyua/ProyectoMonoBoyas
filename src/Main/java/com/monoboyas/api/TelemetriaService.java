package com.monoboyas.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import Alertas.Alerta;
import Central.CentralDatos;
import Persistencia.AlertaDAO;
import Persistencia.MedicionDAO;
import Persistencia.OperacionDAO;
import Persistencia.UsuarioAlertaDAO;
import Sensores.Medicion;


@Service

public class TelemetriaService {
  
    private final MedicionDAO medicionDAO;
    private final AlertaDAO alertaDAO;
    private final UsuarioAlertaDAO usuarioAlertaDAO;
    private final OperacionDAO operacionDAO;
    private final CentralDatos centralDatos;

    

    public TelemetriaService(MedicionDAO medicionDAO, AlertaDAO alertaDAO, UsuarioAlertaDAO usuarioAlertaDAO, OperacionDAO operacionDAO, CentralDatos centralDatos) {
        this.medicionDAO = medicionDAO;
        this.alertaDAO = alertaDAO;
        this.usuarioAlertaDAO = usuarioAlertaDAO;
        this.operacionDAO = operacionDAO;
        this.centralDatos = centralDatos;
    }

    


    public TelemetriaResultado procesarMedicion(Medicion medicion) {

        int medicionId = medicionDAO.guardar(medicion);
        Map<String, Alerta> alertasPorRol = centralDatos.procesarTelemetria(medicion);

        if (alertasPorRol.isEmpty()) {
            return new TelemetriaResultado(medicionId, new ArrayList<>());
        }

        int operacionId = medicion.getIdOperacion();
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        List<AlertaDAO.AlertaInfo> infos = new ArrayList<>();

        for (Map.Entry<String, Alerta> entry : alertasPorRol.entrySet()) {
            String rol = entry.getKey();
            Alerta alerta = entry.getValue();

            int alertaId = alertaDAO.guardar(alerta, operacionId, medicionId);

            Integer usuarioId = switch (rol) {
                case "OPERADOR_BUQUE"  -> op.getOperadorBuqueId();
                case "OPERADOR_LANCHA" -> op.getOperadorLanchaId();
                case "OPERADOR_PLANTA" -> op.getOperadorPlantaId();
                default -> null;
            };
            if (usuarioId != null) {
                usuarioAlertaDAO.registrarRecepcion(alertaId, usuarioId);
            }

            infos.add(new AlertaDAO.AlertaInfo(
                alertaId,
                alerta.getTipoAlerta().toString(),
                alerta.getMensaje(),
                operacionId,
                medicionId,
                LocalDateTime.now()
            ));
        }

        return new TelemetriaResultado(medicionId, infos);
    }

    

    private void registrarRecepciones(int alertaId, int operacionId) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        if (op.getOperadorPlantaId() != null) usuarioAlertaDAO.registrarRecepcion(alertaId, op.getOperadorPlantaId());
        if (op.getOperadorBuqueId() != null) usuarioAlertaDAO.registrarRecepcion(alertaId, op.getOperadorBuqueId());
        if (op.getOperadorLanchaId() != null) usuarioAlertaDAO.registrarRecepcion(alertaId, op.getOperadorLanchaId());
    }

    

    public static class TelemetriaResultado {
    private final int medicionId;
    private final List<AlertaDAO.AlertaInfo> alertas;

    public TelemetriaResultado(int medicionId, List<AlertaDAO.AlertaInfo> alertas) {
        this.medicionId = medicionId;
        this.alertas = alertas;
    }

    public int getMedicionId() { return medicionId; }
    public List<AlertaDAO.AlertaInfo> getAlertas() { return alertas; }
    public boolean tieneAlertas() { return !alertas.isEmpty(); }
    }

}