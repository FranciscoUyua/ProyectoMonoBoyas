package Main.java.com.monoboyas.api;

import java.time.LocalDateTime;
import java.util.Optional;

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
        Optional<Alerta> alertaOpt = centralDatos.procesarTelemetria(medicion);

    
        if (alertaOpt.isEmpty()) {
            return new TelemetriaResultado(medicionId, null);
        }
        Alerta alerta = alertaOpt.get();
        int operacionId = medicion.getIdOperacion();
        int alertaId = alertaDAO.guardar(alerta, operacionId, medicionId);
        registrarRecepciones(alertaId, operacionId);
        AlertaDAO.AlertaInfo info = new AlertaDAO.AlertaInfo(
            alertaId,
            alerta.getTipoAlerta().toString(),
            alerta.getMensaje(),
            operacionId,
            medicionId,
            LocalDateTime.now()
        );

        return new TelemetriaResultado(medicionId, info);
    }

    

    private void registrarRecepciones(int alertaId, int operacionId) {
        OperacionDAO.OperacionInfo op = operacionDAO.buscarPorId(operacionId);
        if (op.getOperadorPlantaId() != null) usuarioAlertaDAO.registrarRecepcion(alertaId, op.getOperadorPlantaId());
        if (op.getOperadorBuqueId() != null) usuarioAlertaDAO.registrarRecepcion(alertaId, op.getOperadorBuqueId());
        if (op.getOperadorLanchaId() != null) usuarioAlertaDAO.registrarRecepcion(alertaId, op.getOperadorLanchaId());
    }

    

    public static class TelemetriaResultado {
        private final int medicionId;
        private final AlertaDAO.AlertaInfo alerta;

        public TelemetriaResultado(int medicionId, AlertaDAO.AlertaInfo alerta) {

        this.medicionId = medicionId;
        this.alerta = alerta;

        }

        public int getMedicionId() { return medicionId; }
        public AlertaDAO.AlertaInfo getAlerta() { return alerta; }
        public boolean tieneAlerta() { return alerta != null; }

    }

}