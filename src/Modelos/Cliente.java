package Modelos;

public class Cliente extends Persona{
    private String telefono;

    public Cliente() {
    }

    public Cliente(String dni, String nombre, String apellido, String email, String password, String usuario, boolean esActivo) {
        super(dni, nombre, apellido, email, password, usuario, esActivo);
    }


    public Cliente(String dni, String nombre, String apellido, String email, String password, String usuario, boolean esActivo, String telefono) {
        super(dni, nombre, apellido, email, password, usuario, esActivo);
        this.telefono = telefono;
    }

    public Cliente(String dni, String nombre, String apellido, String email, String password, String usuario, String tipo, boolean esActivo) {
    }

    public String getTelefono() {

        return telefono;
    }

    public void setTelefono(String telefono) {

        this.telefono = telefono;
    }




}


/*** Generar JSOM**/
/**@Lu **/