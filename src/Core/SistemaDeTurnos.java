package Core;

import Crud.*;
import java.util.Scanner;

public class SistemaDeTurnos {

    private final Scanner scanner = new Scanner(System.in);

    private final CrudClientes crudClientes;
    private final CrudActividades crudActividades;
    private final CrudTurnos crudTurnos;
    private final Login login;

    public SistemaDeTurnos() {
        this.crudClientes = new CrudClientes(scanner);
        this.crudActividades = new CrudActividades(scanner);
        this.crudTurnos = new CrudTurnos(scanner, crudClientes, crudActividades);
        this.login = new Login(scanner, crudClientes, crudActividades, crudTurnos);
    }

    public void iniciar() {
        login.iniciar();
    }
}
