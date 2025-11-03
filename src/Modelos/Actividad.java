package Modelos;
import Enum.Actividades;

public class Actividad {
    private int capacidadMaxima;
    private double precio;
    private Actividades actividad;

    public Actividad(int capacidad, double precio, Actividades actividad) {
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

    public Actividades getActividad() {
        return actividad;
    }

    public void setActividad(Actividades actividad) {
        this.actividad = actividad;
    }
}
