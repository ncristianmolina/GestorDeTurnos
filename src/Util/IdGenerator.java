package Util;

public class IdGenerator {


    private static int turnoCount = 0;


    public static synchronized int nextTurnoId() {
        return ++turnoCount;
    }

    public static synchronized void setTurnoCount(int lastId) {
        turnoCount = lastId;
    }

    public static synchronized int generarId(String tipo) {
        if (tipo == null) {
            return nextTurnoId();
        }

        if ("turnos".equalsIgnoreCase(tipo)) {
            return nextTurnoId();
        }

        return nextTurnoId();
    }
}
