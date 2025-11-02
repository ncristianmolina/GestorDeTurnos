package Enum;

public enum Actividades {
    FUNCIONAL (25),
    MUSCULACION(30),
    SPINNING (15),
    BIKEFORCE (15),
    ZUMBA (30),
    MASAJE (1),
    STRETCHING (10),
    GAP (10),
    NUTRICION (1),
    YOGA (20),
    PILATES (10),
    RITMOSLATINOS (20),
    ENTRENAMIENTOADULTOSMAYORES (10);

    private final int capacidadMax;

    Actividades(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }
}
