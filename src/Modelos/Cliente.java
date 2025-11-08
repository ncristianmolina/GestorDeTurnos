package Modelos;

public class Cliente extends Persona{
    private String telefono;

    public Cliente(String dni, String nombre, String apellido, String email, String password, String usuario, boolean esActivo, String telefono) {
        super(dni, nombre, apellido, email, password, usuario, esActivo);
        this.telefono = telefono;
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