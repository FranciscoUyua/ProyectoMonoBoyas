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

    public void procesarTelemetria(Medicion medicion) {
         switch (medicion.getTipo()) {
            case TENSION -> verificarUmbralTension(medicion.getValor(), medicion.getIdOperacion());
            case PRESION -> verificarUmbralPresion(medicion);
            case OLEAJE -> verificarUmbralOleaje(medicion.getValor(), medicion.getIdOperacion());
            case ORIENTACION -> verificarUmbralOrientacion(medicion.getValor(), medicion.getIdOperacion());
            case CORRIENTE -> verificarUmbralCorriente(medicion.getValor(), medicion.getIdOperacion());
            case CAUDAL -> verificarUmbralCaudal(medicion.getValor(), medicion.getIdOperacion());
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

    private void verificarUmbralAmarre(double valor,int idoperacion) {

        if (valor > AMARRE_ROJA) {
            //aca creo la alerta , todo lo demas sera asi
            System.out.println("SOY LA CENTRAL estoy por crear una Alerta Roja de amarre");
            Operacion op = operacionesActivas.get(idoperacion);    
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.ROJA, "Amarre en riesgo de ruptura", op, 1, "kN");
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.ROJA, "Amarre en riesgo de ruptura", op, 1, "kN");
            Alerta alertaPlanta = new Alerta(3, Alerta.TipoAlerta.ROJA, "Amarre en riesgo de ruptura", op, 1, "kN");

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);
            alertaPlanta.getOperacion().enviarAlertaOperadorPlanta(alertaPlanta);
            //juan tiene q revisar los return

        } else if (valor > AMARRE_AMARILLA) {
            System.out.println("SOY LA CENTRAL [ALERTA AMARILLA] Amarre: " + valor + " kN");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.AMARILLA, "Amarre en riesgo de ruptura", op, 1, "kN");
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.AMARILLA, "Amarre en riesgo de ruptura", op, 1, "kN");
            Alerta alertaPlanta = new Alerta(3, Alerta.TipoAlerta.AMARILLA, "Amarre en riesgo de ruptura", op, 1, "kN");
            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);
            alertaPlanta.getOperacion().enviarAlertaOperadorPlanta(alertaPlanta);
        } 
    }

    private void verificarUmbralTension(double valor, int idoperacion) {
        if (valor > TENSION_ROJA) {
            System.out.println("SOY LA CENTRAL estoy por crear una Alerta Roja de Tension");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.ROJA, "Manguera tensada", op, 1, "tf");
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.AMARILLA, "Manguera tensada", op, 1, "tf");
            Alerta alertaPlanta = new Alerta(3, Alerta.TipoAlerta.ROJA, "Manguera tensada", op, 1, "tf");

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);
            alertaPlanta.getOperacion().enviarAlertaOperadorPlanta(alertaPlanta);

        } else if (valor > TENSION_AMARILLA) {
            System.out.println("SOY LA CENTRAL [ALERTA AMARILLA] Tension: " + valor + " tf");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.AMARILLA, "Manguera tensada", op, 1, "tf");
            Alerta alertaPlanta = new Alerta(3, Alerta.TipoAlerta.AMARILLA, "Manguera tensada", op, 1, "tf");
            // Lancha = Verde en esta franja, no se genera Alerta para Lancha

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaPlanta.getOperacion().enviarAlertaOperadorPlanta(alertaPlanta);
        }
    }

    private void verificarUmbralOleaje(double valor, int idoperacion) {
        if (valor > OLEAJE_ROJA) {
            System.out.println("SOY LA CENTRAL estoy por crear una Alerta Roja de Oleaje");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.AMARILLA, "Oleaje elevado", op, 1, "m");
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.ROJA, "Oleaje elevado", op, 1, "m");

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);

        } else if (valor > OLEAJE_AMARILLA) {
            System.out.println("SOY LA CENTRAL [ALERTA AMARILLA] Oleaje: " + valor + " m");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.AMARILLA, "Oleaje elevado", op, 1, "m");
            // Buque = Verde, Planta = Verde, no se generan esas alertas

            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);
        }   
    }

    private void verificarUmbralOrientacion(double valor, int idoperacion) {
        double valorAbsoluto = Math.abs(valor);

        if (valorAbsoluto > ORIENTACION_ROJA) {
            System.out.println("SOY LA CENTRAL estoy por crear una Alerta Roja de Orientacion");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.ROJA, "Inclinación excesiva", op, 1, "°");
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.ROJA, "Inclinación excesiva", op, 1, "°");
            Alerta alertaPlanta = new Alerta(3, Alerta.TipoAlerta.AMARILLA, "Inclinación excesiva", op, 1, "°");

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);
            alertaPlanta.getOperacion().enviarAlertaOperadorPlanta(alertaPlanta);

        } else if (valorAbsoluto > ORIENTACION_AMARILLA) {
            System.out.println("SOY LA CENTRAL [ALERTA AMARILLA] Orientacion: " + valor + " grados");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.AMARILLA, "Inclinación excesiva", op, 1, "°");
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.AMARILLA, "Inclinación excesiva", op, 1, "°");
            // Planta = Verde, no se genera Alerta para Planta

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);
        }
}

    

    private void verificarUmbralCorriente(double valor, int idoperacion) {

        if (valor > CORRIENTE_ROJA) {
            System.out.println("SOY LA CENTRAL estoy por crear una Alerta Roja de Corriente");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.AMARILLA, "Corriente excesiva", op, 1, "m/s");
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.ROJA, "Corriente excesiva", op, 1, "m/s");
            // Planta = Verde, no se genera Alerta para Planta

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);

        } else if (valor > CORRIENTE_AMARILLA) {
            System.out.println("SOY LA CENTRAL [ALERTA AMARILLA] Corriente: " + valor + " m/s");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.AMARILLA, "Corriente excesiva", op, 1, "m/s");
            // Buque = Verde, Planta = Verde

            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);
        }
    }

    private void verificarUmbralViento(double valor, int idoperacion) {

        if (valor > VIENTO_ROJA) {
            System.out.println("SOY LA CENTRAL estoy por crear una Alerta Roja de Viento");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.AMARILLA, "Viento excesivo", op, 1, "km/h");
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.ROJA, "Viento excesivo", op, 1, "km/h");
            // Planta = Verde, no se genera Alerta para Planta

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);

        } else if (valor > VIENTO_AMARILLA) {
            System.out.println("SOY LA CENTRAL [ALERTA AMARILLA] Viento: " + valor + " km/h");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.AMARILLA, "Viento excesivo", op, 1, "km/h");
            // Buque = Verde, Planta = Verde

            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);
        }
}

    private void verificarUmbralCaudal(double valor, int idoperacion) {

        if (valor <= 0.0) {
            System.out.println("SOY LA CENTRAL estoy por crear una Alerta de Caudal (bloqueo)");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.AMARILLA, "Caudal en cero, posible bloqueo", op, 1, "l/s");
            Alerta alertaPlanta = new Alerta(3, Alerta.TipoAlerta.AMARILLA, "Caudal en cero, posible bloqueo", op, 1, "l/s");
            // Lancha = Verde, no se genera Alerta para Lancha

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaPlanta.getOperacion().enviarAlertaOperadorPlanta(alertaPlanta);

        } else if (valor > CAUDAL_AMARILLA) {
            // Los tres roles son Verde en esta franja segun la tabla amarilla, no se genera ninguna Alerta
            System.out.println("SOY LA CENTRAL: caudal elevado (" + valor + " l/s) pero sin alerta para ningun operador");
        }
    }

    private void verificarUmbralPresion(Medicion medicion) {
        double valor = medicion.getValor();
        int idoperacion = medicion.getIdOperacion();
        if (valor > PRESION_ROJA_ALTA || valor < PRESION_ROJA_BAJA) {
            System.out.println("SOY LA CENTRAL estoy por crear una Alerta Roja de Presion");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.ROJA, "Presión fuera de rango (" + medicion.getOrigen() + ")", op, 1, "Pa");
            Alerta alertaLancha = new Alerta(2, Alerta.TipoAlerta.AMARILLA, "Presión fuera de rango (" + medicion.getOrigen() + ")", op, 1, "Pa");
            Alerta alertaPlanta = new Alerta(3, Alerta.TipoAlerta.ROJA, "Presión fuera de rango (" + medicion.getOrigen() + ")", op, 1, "Pa");

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaLancha.getOperacion().enviarAlertaOperadorLancha(alertaLancha);
            alertaPlanta.getOperacion().enviarAlertaOperadorPlanta(alertaPlanta);

        } else if (valor > PRESION_AMARILLA_ALTA) {
            System.out.println("SOY LA CENTRAL [ALERTA AMARILLA] Presion: " + valor + " Pa");
            Operacion op = operacionesActivas.get(idoperacion);
            Alerta alertaBuque = new Alerta(1, Alerta.TipoAlerta.AMARILLA, "Presión fuera de rango (" + medicion.getOrigen() + ")", op, 1, "Pa");
            Alerta alertaPlanta = new Alerta(3, Alerta.TipoAlerta.AMARILLA, "Presión fuera de rango (" + medicion.getOrigen() + ")", op, 1, "Pa");
            // Lancha = Verde en esta franja

            alertaBuque.getOperacion().enviarAlertaOperadorBuque(alertaBuque);
            alertaPlanta.getOperacion().enviarAlertaOperadorPlanta(alertaPlanta);
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

}