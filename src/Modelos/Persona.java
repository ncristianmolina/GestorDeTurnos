package Modelos;

public abstract class Persona {

    private int id;
    private String nombre;
    private String email;

    public Persona(int id, String nombre, String email) {
        this.id = IdGenerator.nextPersonaId();
        this.nombre = nombre;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
