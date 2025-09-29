package Modelos;

public class Administrador extends Persona{
    private int nivelAcceso;

    public Administrador(int id, String nombre, String email, int nivelAcceso) {
        super(id, nombre, email);
        this.nivelAcceso = nivelAcceso;
    }

    public int getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(int nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }
}
