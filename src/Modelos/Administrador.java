package Modelos;

public class Administrador extends Persona{
    private int nivelAcceso;

    public Administrador(String dni, String nombre, String apellido, String email, int nivelAcceso) {
        super(dni, nombre, apellido, email);
        this.nivelAcceso = nivelAcceso;
    }

    public int getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(int nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    /*que el administrador pueda agregar una activdad nueva
    que pueda modificar actividades
    que pueda eliminar
     */
}