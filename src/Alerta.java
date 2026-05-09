public class Alerta {

    protected int id;
    protected String tipoAlerta;
    protected String mensaje;
    protected int id_operacion;
    protected int int_medicion;
    protected String string_medicion;

    public Alerta(int id, String tipoAlerta, String mensaje, int id_operacion, int int_medicion, String string_medicion) {
        this.id = id;
        this.tipoAlerta = tipoAlerta;
        this.mensaje = mensaje;
        this.id_operacion = id_operacion;
        this.int_medicion = int_medicion;
        this.string_medicion = string_medicion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getId_operacion() {
        return id_operacion;
    }

    public void setId_operacion(int id_operacion) {
        this.id_operacion = id_operacion;
    }

    public int getInt_medicion() {
        return int_medicion;
    }

    public void setInt_medicion(int int_medicion) {
        this.int_medicion = int_medicion;
    }

    public String getString_medicion() {
        return string_medicion;
    }

    public void setString_medicion(String string_medicion) {
        this.string_medicion = string_medicion;
    }

    


    



    
}
