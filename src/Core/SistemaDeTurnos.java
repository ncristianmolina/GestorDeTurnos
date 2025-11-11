package Core;

import Gestores.GestorGenerico;
import Modelos.Cliente;
import Modelos.Turno;
import Modelos.Administrador;

import java.util.Scanner;

public class SistemaDeTurnos {

    private final Scanner scanner = new Scanner(System.in);

    private GestorGenerico<Turno> gestorTurnos;
    private GestorGenerico<Cliente> gestorClientes;
    private GestorGenerico<Administrador> gestorAdmins;

    public SistemaDeTurnos() {
        this.gestorTurnos = new GestorGenerico<>();
        this.gestorClientes = new GestorGenerico<>();
        this.gestorAdmins = new GestorGenerico<>();
    }

    public GestorGenerico<Turno> getGestorTurnos() {
        return gestorTurnos;
    }

    public GestorGenerico<Cliente> getGestorClientes() {
        return gestorClientes;
    }

    public GestorGenerico<Administrador> getGestorAdmins() {
        return gestorAdmins;
    }

    public void iniciar() {
    }
}