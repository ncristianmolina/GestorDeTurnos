package Modelos;

public class Actividad {
    private int capacidadMaxima;
    private double precio;
    private String tipoActividad;
    private int idActividad;

    public Actividad(int capacidadMaxima, double precio, String tipoActividad, int idActividad) {
        this.capacidadMaxima = capacidadMaxima;
        this.precio = precio;
        this.tipoActividad = tipoActividad;
        this.idActividad = idActividad;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "capacidadMaxima=" + capacidadMaxima +
                ", precio=" + precio +
                ", tipoActividad='" + tipoActividad + '\'' +
                ", idActividad=" + idActividad +
                '}';
    }
}
