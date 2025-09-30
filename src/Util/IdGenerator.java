package Util;

public class IdGenerator {
    private static int personaCount = 0;
    private static int turnoCount = 0;

    public static int nextPersonaId() {
        return ++personaCount;
    }

    public static int nextTurnoId() {
        return ++turnoCount;
    }

    // Para sincronizar con archivo al iniciar
    public static void setPersonaCount(int lastId) {
        personaCount = lastId;
    }

    public static void setTurnoCount(int lastId) {
        turnoCount = lastId;
    }
}
