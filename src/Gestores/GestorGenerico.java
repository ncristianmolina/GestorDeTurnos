package Gestores;

import java.util.ArrayList;
import java.util.List;

public class GestorGenerico<T> {
    protected List<T> lista;

    public GestorGenerico() {
        this.lista = new ArrayList<>();
    }

    public void agregar(T elemento) {
        lista.add(elemento);
        System.out.println("Elemento agregado correctamente.");
    }

    public List<T> listar() {
        return new ArrayList<>(lista);
    }

    public int cantidad() {
        return lista.size();
    }

    public T buscarPorIndice(int index) {
        if (index >= 0 && index < lista.size()) {
            return lista.get(index);
        }
        return null;
    }

    public List<T> getLista() {
        return lista;
    }
}
