package Modelos;
import Enum.TipoActividad;

public class Actividad {
    private int capacidadMaxima;
    private double precio;
    private TipoActividad actividad;

    public Actividad(int capacidad, double precio, TipoActividad actividad) {
        this.capacidadMaxima = capacidadMaxima;
        this.precio = precio;
        this.actividad = actividad;
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

    public TipoActividad getActividad() {
        return actividad;
    }

    public void setActividad(TipoActividad actividad) {
        this.actividad = actividad;
    }
}
