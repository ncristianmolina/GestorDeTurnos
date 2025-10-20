package Gestores;

import java.util.ArrayList;
import java.util.List;

public class GestorGenerico<T> {
    private List<T> lista;

    public GestorGenerico() {
        this.lista = new ArrayList<>();
    }

    public void agregar(T item) {
        lista.add(item);
    }

    public void eliminar(T item) {
        lista.remove(item);
    }

    public T buscarPorIndice(int index) {
        if (index >= 0 && index < lista.size()) {
            return lista.get(index);
        }
        return null;
    }

    public List<T> listar() {
        return new ArrayList<>(lista);
    }

    public int cantidad() {
        return lista.size();
    }
}
