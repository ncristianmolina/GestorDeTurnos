package Modelos;

import Enum.TipoUsuario;

public class Cliente extends Persona {
    private String telefono;

    public Cliente() {}

    public Cliente(String dni, String nombre, String apellido, String email, String password,
                   String usuario, boolean esActivo) {
        super(dni, nombre, apellido, email, password, usuario, esActivo);
        setTipo(TipoUsuario.CLIENTE);
    }

    public Cliente(String dni, String nombre, String apellido, String email, String password,
                   String usuario, boolean esActivo, String telefono) {
        super(dni, nombre, apellido, email, password, usuario, esActivo);
        this.telefono = telefono;
        setTipo(TipoUsuario.CLIENTE);
    }

    // Constructor usado por JSON
    public Cliente(String dni, String nombre, String apellido, String email, String password,
                   String usuario, String tipo, boolean esActivo) {
        super(dni, nombre, apellido, email, password, usuario, esActivo);
        setTipo(TipoUsuario.valueOf(tipo.toUpperCase()));
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
