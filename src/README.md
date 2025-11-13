Repositorio GitHub: https://github.com/ncristianmolina/GestorDeTurnos

UML: https://app.creately.com/d/wKDIZLaA9H2/edit

Proyecto: Gestor de Turnos

Integrantes del grupo:
Luciana Hamade
Julieta Requena
Adela Santillan Garcia
Cristian Molina

Este proyecto fue desarrollado como entrega final de la materia Programación II - Tecnicatura Universitaria en Programación UTN.
El mismo tiene el propósito de aplicar los principios fundamentales de la programación orientada a objetos utilizando el lenguaje Java,
tales como herencia, encapsulamiento, polimorfismo y manejo de excepciones, además de incorporar persistencia de datos mediante archivos JSON.

La idea principal es tener un sistema que permita gestionar turnos de manera eficiente, ofreciendo funciones tanto para clientes como para administradores. 
Los clientes pueden reservar, cancelar o ver sus turnos, mientras que los administradores pueden manejar tanto los clientes como las actividades que ofrece el sistema.


Funcionalidades principales:

Inicio de sesión para clientes y administradores con usuarios cargados desde archivos JSON.

Gestión de clientes: alta, baja, modificación y listado.

Gestión de actividades: creación, edición, eliminación y consulta.

Gestión de turnos: reserva, cancelación y listado general.

Persistencia de datos mediante archivos JSON para conservar la información entre ejecuciones.

Validaciones para evitar reservas duplicadas o superar la capacidad máxima de las actividades.

Estructura del proyecto:

Modelos: contiene las clases principales como Persona, Cliente, Administrador, Actividad y Turno.

Gestores: manejan la lógica y las validaciones del sistema.

Crud: se encarga de las operaciones que realiza el usuario desde consola como alta, baja o modificación.

ManejoJSON: gestiona la lectura y escritura de los archivos JSON.

Core: contiene el flujo principal del sistema con la clase SistemaDeTurnos.

Ejecución:

Clonar o descargar el repositorio desde GitHub.

Abrir el proyecto en NetBeans o en cualquier IDE que soporte Java.

Ejecutar la clase SistemaDeTurnos del paquete Core.

Iniciar sesión con un usuario existente o crear uno nuevo desde el panel de administrador.

Archivos de datos:
El sistema usa la carpeta src/data/ donde se guardan los archivos:

persona.json

actividades.json

turnos.json

Cada vez que se realiza una operación de alta, baja o modificación, los cambios se guardan automáticamente.

