package Alertas;

import Operaciones.Operacion;

public class Alerta {

    protected int id;
    protected String mensaje;
    protected Operacion operacion;
    protected double double_medicion;
    protected String string_medicion;

    public enum EstadoAlerta {
        NO_RECONOCIDA,
        RECONOCIDA
    }
    protected EstadoAlerta estado;

    public enum TipoAlerta {
        VERDE,
        AMARILLA,
        ROJA
    }
    protected TipoAlerta tipo_Alerta;

    public Alerta(int id, TipoAlerta tipo_Alerta, String mensaje, Operacion operacion, double valor_medicion, String string_medicion) {
        this.id = id;
        this.tipo_Alerta = tipo_Alerta;
        this.operacion = operacion;
        this.double_medicion = valor_medicion;
        this.string_medicion = string_medicion;
        this.estado = EstadoAlerta.NO_RECONOCIDA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoAlerta getTipo_Alerta() {
        return tipo_Alerta;
    }

    public void setTipo_Alerta(TipoAlerta tipo_Alerta) {
        this.tipo_Alerta = tipo_Alerta;
    }

    public EstadoAlerta getEstadoAlerta() {
        return estado;
    }

    public void setEstado(EstadoAlerta estado) {
        this.estado = estado;
    }
    public void cambiarEstado(){
        this.estado = EstadoAlerta.RECONOCIDA;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    public double getDouble_medicion() {
        return double_medicion;
    }

    public void setDouble_medicion(double double_medicion) {
        this.double_medicion = double_medicion;
    }

    public String getString_medicion() {
        return string_medicion;
    }

    public void setString_medicion(String string_medicion) {
        this.string_medicion = string_medicion;
    }

    public EstadoAlerta getEstado() {
        return estado;
    }
}