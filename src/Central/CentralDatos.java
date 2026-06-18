package Central;

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

    public void procesarTelemetria(Medicion medicion) {
        switch (medicion.getTipo()) {
            case TENSION -> verificarUmbralTension(medicion.getValor());
            case PRESION -> verificarUmbralPresion(medicion);
            case OLEAJE -> verificarUmbralOleaje(medicion.getValor());
            case ORIENTACION -> verificarUmbralOrientacion(medicion.getValor());
            case CORRIENTE -> verificarUmbralCorriente(medicion.getValor());
            case CAUDAL -> verificarUmbralCaudal(medicion.getValor());
            case VIENTO -> verificarUmbralViento(medicion.getValor());
            case AMARRE -> verificarUmbralAmarre(medicion.getValor());
        }
    }

    private Double ultimaPresionMonoboya;
    private Double ultimaPresionBuque;

    private void verificarUmbralPresion(Medicion medicion) {
        double valor = medicion.getValor();

        NivelAlerta nivel;
        if (valor > PRESION_ROJA_ALTA || valor < PRESION_ROJA_BAJA) {
            nivel = NivelAlerta.ROJA;
        } else if (valor > PRESION_AMARILLA_ALTA) {
            nivel = NivelAlerta.AMARILLA;
        } else {
            nivel = NivelAlerta.VERDE;
        }
        reportar(nivel, "Presión (" + medicion.getOrigen() + ")", valor, "Pa");

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

    private void verificarUmbralAmarre(double valor) {
        reportar(clasificarAlerta(valor, AMARRE_AMARILLA, AMARRE_ROJA), "Amarre", valor, "kN");
    }

    private void verificarUmbralTension(double valor) {
        reportar(clasificarAlerta(valor, TENSION_AMARILLA, TENSION_ROJA), "Tensión de manguera", valor, "tf");
    }


    private void verificarUmbralCaudal(double valor) {
        NivelAlerta nivel;
        if (valor <= 0.0) {
            nivel = NivelAlerta.ROJA;
        } else if (valor > CAUDAL_AMARILLA) {
            nivel = NivelAlerta.AMARILLA;
        } else {
            nivel = NivelAlerta.VERDE;
        }
        reportar(nivel, "Caudal", valor, "l/s");
    }

    private void verificarUmbralOleaje(double valor) {
        reportar(clasificarAlerta(valor, OLEAJE_AMARILLA, OLEAJE_ROJA), "Oleaje", valor, "m");
    }

    private void verificarUmbralOrientacion(double valor) {
        double valorAbsoluto = Math.abs(valor);
        reportar(clasificarAlerta(valorAbsoluto, ORIENTACION_AMARILLA, ORIENTACION_ROJA), "Orientación", valor, "°");
    }

    private void verificarUmbralCorriente(double valor) {
        reportar(clasificarAlerta(valor, CORRIENTE_AMARILLA, CORRIENTE_ROJA), "Corriente", valor, "m/s");
    }

    private void verificarUmbralViento(double valor) {
        reportar(clasificarAlerta(valor, VIENTO_AMARILLA, VIENTO_ROJA), "Viento", valor, "km/h");
    }

    private NivelAlerta clasificarAlerta(double valor, double umbralAmarilla, double umbralCritica) {
        if (valor > umbralCritica) {
            return NivelAlerta.ROJA;
        }
        if (valor > umbralAmarilla) {
            return NivelAlerta.AMARILLA;
        }
        return NivelAlerta.VERDE;
    }

    private void reportar(NivelAlerta nivel, String nombreSensor, double valor, String unidad) {
        switch (nivel) {
            case AMARILLA -> System.out.println("[PRE-ALERTA] " + nombreSensor + ": " + valor + " " + unidad);
            case ROJA -> System.out.println("[ALERTA CRITICA] " + nombreSensor + ": " + valor + " " + unidad);
            case VERDE -> { /* operación normal, no se reporta nada */ }
        }
    }
}