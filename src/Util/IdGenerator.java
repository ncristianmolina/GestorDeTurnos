package Util;

/**
 * Generador de IDs autoincrementales solo para Turnos.
 * Debe ser inicializado al cargar los datos desde el archivo.
 */
public class IdGenerator {

    // El contador se mantiene estático
    private static int turnoCount = 0;

    /**
     * Devuelve el siguiente ID de turno disponible.
     * @return un ID de turno único.
     */
    public static synchronized int nextTurnoId() {
        return ++turnoCount;
    }

    /**
     * Sincroniza el contador con el último ID más alto
     * encontrado en el archivo de persistencia.
     * @param lastId El ID más alto encontrado en los datos.
     */
    public static synchronized void setTurnoCount(int lastId) {
        turnoCount = lastId;
    }

    /**
     * Método genérico para generar IDs por "tipo".
     * Por ahora solo soporta "turnos" y delega a nextTurnoId().
     * (Lo dejé preparado por si en el futuro querés manejar múltiples contadores.)
     *
     * @param tipo nombre lógico del contador ("turnos", etc.)
     * @return id generado
     */
    public static synchronized int generarId(String tipo) {
        if (tipo == null) {
            return nextTurnoId();
        }
        // Si en el futuro añadís más contadores, podés ampliar la lógica aquí.
        if ("turnos".equalsIgnoreCase(tipo)) {
            return nextTurnoId();
        }
        // fallback
        return nextTurnoId();
    }
}
