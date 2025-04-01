package banesco;

import java.util.Scanner;

public class Banesco {
    
    private static Cola colaClientes = new Cola();
    private static gestorClientes gestor = new gestorClientes("Clientes.in");

    public static void main(String[] args) {
        //gestor.cargarClientes(colaClientes);
        inicio();
    }
    
    private static void inicio() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        
        do {
            System.out.println("\nBienvenido a Banesco");
            System.out.println("1. Realizar tramite.");
            System.out.println("2. Salir.");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            
            switch(opcion) {
                case 1:
                    System.out.println("\n--- Operacion seleccionada: ---");
                    realizarTramite(colaClientes, gestor);
                    break;
                 
                case 2:
                    System.out.println("\nGracias por usar nuestro sistema. ¡Hasta pronto!");
                    break;
                
                case 0:
                    System.out.println("\nUsted ha ingresado al modo taquilla: atencion al cliente.");
                    modoTaquilla();
                    break;                
                    
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            }
        } while(opcion != 2);  
        scanner.close();
    }
    
    /*especificaciones: el usuario solo puede hacer 5 movimientos y una vez es atendido sale de la cola.
    cada movimiento tiene que estar cronometrado: 
    4 minutos para un retiro 
    3 para un depósito 
    1.5 para consulta de movimientos 
    5 para actualización delibretas 
    2 para pago de servicios
    */
    private static void realizarTramite(Cola colaClientes, gestorClientes gestor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n-- Usted ha seleccionado: Realizar trámite --");
        System.out.print("\nPor favor, ingrese su número de cédula: ");
        int cedula = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente;
        if (!gestor.existeCliente(cedula)) {
            System.out.print("No está registrado en el sistema. Ingrese su nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese su apellido: ");
            String apellido = scanner.nextLine();
            cliente = new Cliente(nombre, apellido, cedula, 0.0);
            gestor.guardarCliente(cliente);
            colaClientes.encolar(cliente);
            System.out.println("\nUsted ha sido agregado a la cola y registrado en el sistema. Espere su turno.");
            return;
        } else {
            cliente = gestor.obtenerCliente(cedula);
            if (!colaClientes.contiene(cedula)) {
                colaClientes.encolar(cliente);
            }
        }

        Cliente primerCliente = colaClientes.verFrente();
        if (primerCliente == null || primerCliente.getCedula() != cedula) {
            System.out.println("\nUsted ya está en la cola. Espere su turno.");
            return;
        }

        int opcion;
        int movimientos = 0;

        do {
            System.out.println("\nBienvenido " + cliente.getNombreCompleto() + ".");
            System.out.println("\n¿Qué desea hacer?");
            System.out.println("1. Retiro.");
            System.out.println("2. Depósito a mi cuenta.");
            System.out.println("3. Consulta de movimientos.");
            System.out.println("4. Actualización de libreta.");
            System.out.println("5. Pago de servicios.");
            System.out.println("6. Salir.");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();


            switch (opcion) {
                case 1:
                    System.out.print("\nIngrese el monto a retirar: ");
                    double retiro = scanner.nextDouble();
                    cliente.retirar(retiro);
                    movimientos++;
                    break;
                case 2:
                    System.out.print("\nIngrese el monto a depositar: ");
                    double deposito = scanner.nextDouble();
                    if (deposito > 0) {
                        cliente.depositar(deposito);
                        System.out.println("Depósito exitoso. Nuevo saldo: $" + cliente.getSaldo());
                        gestor.actualizarSaldoCliente(cliente.getCedula(), cliente.getSaldo());
                    } else {
                        System.out.println("El monto debe ser positivo.");
                    }
                    movimientos++;
                    break;
                case 3:
                    System.out.println("\n--- Consulta de Movimientos ---");
                    cliente.mostrarMovimientos();  
                    movimientos++;
                    break;

                case 4:
                    System.out.println("\n--- Actualización de Libreta ---");
                    cliente.actualizarLibreta(); 
                    movimientos++;
                    break;
                case 5:
                    System.out.println("\nPago de servicios realizado.");
                    movimientos++;
                    break;
                case 6:
                    System.out.println("\nSaliendo del trámite.");
                    colaClientes.desencolar();
                    System.out.println("\nTrámite finalizado. Cliente removido de la cola.");
                    gestor.eliminarCliente(cliente.getCedula());
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            }

            if (movimientos >= 5) {
                System.out.println("\nHa alcanzado el límite de 5 movimientos. Finalizando el trámite.");
                opcion = 6;
            }

        } while (opcion != 6);

        colaClientes.desencolar();
        System.out.println("\nTrámite finalizado. Cliente removido de la cola.");
        gestor.eliminarCliente(cliente.getCedula());
    }
    
    //modo cajero
    
        private static void modoTaquilla() {
        Scanner scanner = new Scanner(System.in);
        colaClientes = new Cola();
        gestor.cargarClientes(colaClientes);
        int opcion;

        do {
            System.out.println("\n--- MODO TAQUILLA: ATENCIÓN AL CLIENTE ---");
            System.out.println("1. Ver la cola.");
            System.out.println("2. Sacar a alguien de la cola.");
            System.out.println("3. Terminar el día.");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- CLIENTES EN COLA ---");
                    colaClientes.imprimirCola();
                    break;

                case 2:
                    if (!colaClientes.estaVacia()) {
                        Cliente atendido = colaClientes.desencolar();
                        System.out.println("\nCliente atendido: " + atendido.getNombre() + " (Cédula: " + atendido.getCedula() + ")");
                    } else {
                        System.out.println("\nNo hay clientes en la cola.");
                    }
                    break;

                case 3:
                    System.out.println("\nFinalizando el día... Guardando estado.");
                    break;

                default:
                    System.out.println("Ha ocurrido un error.");
            }
        } while (opcion != 3);

        System.out.println("Fin del día. Cerrando sistema.");
        System.out.println("(Operaciones de los clientes del dia de hoy guardadas en: Taquilla.log)"); //#por terminar
    }
}

