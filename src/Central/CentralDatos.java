package Central;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import Alertas.Alerta;
import Operaciones.Operacion;
import Sensores.Medicion;
import Sensores.Medicion.OrigenMedicion;

public class CentralDatos {

    public enum NivelAlerta {
        VERDE,
        AMARILLA,
        ROJA
    }

    private Map<Integer, Operacion> operacionesActivas = new HashMap<>();
    private Double ultimaPresionMonoboya;
    private Double ultimaPresionBuque;

    // --- 1.1 Amarre (kN) ---
    private static final double AMARRE_AMARILLA = 600.0;
    private static final double AMARRE_ROJA = 900.0;

    // --- 1.2 Tension en manguera (tf) ---
    private static final double TENSION_AMARILLA = 8.0;
    private static final double TENSION_ROJA = 12.0;

    // --- 1.3 Presion (Pa) ---
    private static final double PRESION_AMARILLA_ALTA = 1_400_000.0;
    private static final double PRESION_ROJA_ALTA = 1_600_000.0;
    private static final double PRESION_ROJA_BAJA = 50_000.0;
    private static final double PRESION_ROJA_DISCREPANCIA = 200_000.0;

    // --- 1.4 Caudal (l/s) ---
    private static final double CAUDAL_AMARILLA = 1600.0;
    // Caudal critico = 0 l/s. Se asume que mientras Monoboya recolecta datos,
    // la operación de bombeo está en curso.

    // --- 1.5 Oleaje (m) ---
    private static final double OLEAJE_AMARILLA = 2.5;
    private static final double OLEAJE_ROJA = 3.5;

    // --- 1.6 Orientacion / Giroscopio (grados) ---
    private static final double ORIENTACION_AMARILLA = 15.0;
    private static final double ORIENTACION_ROJA = 25.0;

    // --- 1.7 Corriente (m/s) ---
    private static final double CORRIENTE_AMARILLA = 1.5;
    private static final double CORRIENTE_ROJA = 2.2;

    // --- 1.8 Viento (km/h) ---
    private static final double VIENTO_AMARILLA = 55.0;
    private static final double VIENTO_ROJA = 75.0;

    public Optional<Alerta> procesarTelemetria(Medicion medicion) {
        return switch (medicion.getTipo()) {
            case TENSION -> verificarUmbralTension(medicion.getValor());
            case PRESION -> verificarUmbralPresion(medicion);
            case OLEAJE -> verificarUmbralOleaje(medicion.getValor());
            case ORIENTACION -> verificarUmbralOrientacion(medicion.getValor());
            case CORRIENTE -> verificarUmbralCorriente(medicion.getValor());
            case CAUDAL -> verificarUmbralCaudal(medicion.getValor());
            case VIENTO -> verificarUmbralViento(medicion.getValor(), medicion.getIdOperacion());//sin terminar
            case AMARRE -> verificarUmbralAmarre(medicion.getValor(), medicion.getIdOperacion());
        };
    }

    public void iniciarOperacion(Operacion operacion) {
        operacionesActivas.put(operacion.getId(), operacion);
    }

    public void finalizarOperacion(int idOperacion) {
        operacionesActivas.remove(idOperacion);
    }

    private Optional<Alerta> verificarUmbralAmarre(double valor,int idoperacion) {

        if (valor > AMARRE_ROJA) {
            //aca creo la alerta , todo lo demas sera asi
            System.out.println("SOY LA CENTRAL estoy por crear una Alerta critica de amarre");
            Operacion op = operacionesActivas.get(idoperacion);    
            Alerta alerta = new Alerta(1, Alerta.TipoAlerta.ROJA, "Amarre en riesgo de ruptura", op, 1, "kN");
            //alerta.operacion.viajarOperadorX()
            return Optional.of(alerta);

        } else if (valor > AMARRE_AMARILLA) {
            System.out.println("SOY LA CENTRAL [ALERTA AMARILLA] Amarre: " + valor + " kN");
            return Optional.of(alerta);

        } else {
            return Optional.empty(); // VERDE — no se genera alerta
        }
        //reportar(nivel, "Amarre", valor, "kN");
    }

    private Optional<Alerta> verificarUmbralTension(double valor) {
        if (valor > TENSION_ROJA) {
            nivel = NivelAlerta.ROJA;
            return Optional.of(alerta);
        } else if (valor > TENSION_AMARILLA) {
            nivel = NivelAlerta.AMARILLA;
            return Optional.of(alerta);
        } else {
            nivel = NivelAlerta.VERDE;
            return Optional.empty();
        }
    }

    private Optional<Alerta> verificarUmbralOleaje(double valor) {
        if (valor > OLEAJE_ROJA) {
            nivel = NivelAlerta.ROJA;
            return Optional.of(alerta);
        } else if (valor > OLEAJE_AMARILLA) {
            nivel = NivelAlerta.AMARILLA;
            return Optional.of(alerta);
        } else {
            nivel = NivelAlerta.VERDE;
            return Optional.empty();
        }
    }

    private Optional<Alerta>verificarUmbralOrientacion(double valor) {
        double valorAbsoluto = Math.abs(valor);

        if (valorAbsoluto > ORIENTACION_ROJA) {
            nivel = NivelAlerta.ROJA;
            return Optional.of(alerta);
        } else if (valorAbsoluto > ORIENTACION_AMARILLA) {
            nivel = NivelAlerta.AMARILLA;
            return Optional.of(alerta);
        } else {
            nivel = NivelAlerta.VERDE;
            return Optional.empty();
        }

    }

    private Optional<Alerta> verificarUmbralCorriente(double valor) {

        if (valor > CORRIENTE_ROJA) {
            nivel = NivelAlerta.ROJA;
            return Optional.of(alerta);
        } else if (valor > CORRIENTE_AMARILLA) {
            nivel = NivelAlerta.AMARILLA;
            return Optional.of(alerta);
        } else {
            nivel = NivelAlerta.VERDE;
            return Optional.empty();
        }
    }

    private Optional<Alerta> verificarUmbralViento(double valor,int idoperacion) {
        
        if (valor > VIENTO_ROJA) {
            nivel = NivelAlerta.ROJA;
            return Optional.of(alerta);
        } else if (valor > VIENTO_AMARILLA) {
            nivel = NivelAlerta.AMARILLA;
            return Optional.of(alerta);
        } else {
            nivel = NivelAlerta.VERDE;
            return Optional.empty();
        }

    }

    private Optional<Alerta> verificarUmbralCaudal(double valor) {
        
        if (valor <= 0.0) {
            nivel = NivelAlerta.ROJA;
            return Optional.of(alerta);
        } else if (valor > CAUDAL_AMARILLA) {
            nivel = NivelAlerta.AMARILLA;
            return Optional.of(alerta);
        } else {
            nivel = NivelAlerta.VERDE;
            return Optional.empty();
        }
    }

    private Optional<Alerta> verificarUmbralPresion(Medicion medicion) {
        double valor = medicion.getValor();

        if (valor > PRESION_ROJA_ALTA || valor < PRESION_ROJA_BAJA) {
            nivel = NivelAlerta.ROJA;
            return Optional.of(alerta);
        } else if (valor > PRESION_AMARILLA_ALTA) {
            nivel = NivelAlerta.AMARILLA;
            return Optional.of(alerta);
        } else {
            nivel = NivelAlerta.VERDE;
            return Optional.empty();
        }

        actualizarPresionYVerificarDiscrepancia(medicion);
    }

    private void actualizarPresionYVerificarDiscrepancia(Medicion medicion) {
        if (medicion.getOrigen() == OrigenMedicion.MONOBOYA) {
            ultimaPresionMonoboya = medicion.getValor();
        } else if (medicion.getOrigen() == OrigenMedicion.BUQUE) {
            ultimaPresionBuque = medicion.getValor();
        }

        if (ultimaPresionMonoboya != null && ultimaPresionBuque != null) {
            double discrepancia = Math.abs(ultimaPresionMonoboya - ultimaPresionBuque);
            if (discrepancia > PRESION_ROJA_DISCREPANCIA) {
                System.out.println("[ALERTA CRITICA] Discrepancia de presión Monoboya/Buque: "
                        + discrepancia + " Pa (posible fuga en la línea)");
            }
        }
    }


    private void reportar(NivelAlerta nivel, String nombreSensor, double valor, String unidad) {
        switch (nivel) {
            case AMARILLA -> System.out.println("[PRE-ALERTA] " + nombreSensor + ": " + valor + " " + unidad);
            case ROJA -> System.out.println("[ALERTA CRITICA] " + nombreSensor + ": " + valor + " " + unidad);
            case VERDE -> { /* operación normal, no se reporta nada */ }
        }
    }
}