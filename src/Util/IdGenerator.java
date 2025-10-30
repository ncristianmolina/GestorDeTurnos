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
    public static int nextTurnoId() {
        // Incrementa y luego retorna (1, 2, 3...)
        return ++turnoCount;
    }

    /**
     * Sincroniza el contador con el último ID más alto
     * encontrado en el archivo de persistencia.
     * @param lastId El ID más alto encontrado en los datos.
     */
    public static void setTurnoCount(int lastId) {
        turnoCount = lastId;
    }
}