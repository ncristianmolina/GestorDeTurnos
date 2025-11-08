package Modelos;

public class Actividad {
    private int capacidadMaxima;
    private double precio;
    private String tipoActividad;


    public Actividad(int capacidad, double precio, String tipoActividad) {
        this.capacidadMaxima = capacidadMaxima;
        this.precio = precio;
        this.tipoActividad = tipoActividad;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidad(int capacidad) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }
}
