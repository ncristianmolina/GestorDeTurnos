package Modelos;

import Util.IdGenerator;

public abstract class Persona {

    private int id;
    private String nombre;
    private String apellido;
    private String email;

    public Persona(int id, String nombre, String apellido, String email) {
        this.id = IdGenerator.nextPersonaId();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public int getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
