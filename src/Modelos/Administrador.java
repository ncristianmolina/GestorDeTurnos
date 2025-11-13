package Modelos;

import Enum.TipoUsuario;

public class Administrador extends Persona {
    private int nivelAcceso;


    public Administrador(String dni, String nombre, String apellido, String email, String password,
                         String usuario, boolean esActivo, int nivelAcceso) {
        super(dni, nombre, apellido, email, password, usuario, esActivo);
        this.nivelAcceso = nivelAcceso;
        setTipo(TipoUsuario.ADMIN);
    }


    public Administrador(String dni, String nombre, String apellido, String email, String password,
                         String usuario, String tipo, boolean esActivo) {
        super(dni, nombre, apellido, email, password, usuario, esActivo);
        setTipo(TipoUsuario.valueOf(tipo.toUpperCase()));
    }

    public int getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(int nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }
}
