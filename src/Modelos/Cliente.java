package Modelos;

public class Cliente extends Persona{
    private String telefono;

    public Cliente(int id, String nombre, String email, String telefono) {
        super(id, nombre, email);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
