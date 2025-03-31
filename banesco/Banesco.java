package banesco;

import java.util.Scanner;

public class Banesco {
    
    private static Cola colaClientes = new Cola();
    private static gestorClientes gestor = new gestorClientes("Clientes.in");

    public static void main(String[] args) {
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
        System.out.println("\n -- Usted ha seleccionado: Realizar trámite --");
        System.out.print("\n Por favor ingrese su número de cédula: ");

        int cedula = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = colaClientes.buscarCliente(cedula);

        if (cliente == null) { //#caso en el que el usuario no este en la cola (en clientes.in)
            System.out.print("No está registrado en la cola. Ingrese su nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Ingrese su apellido: ");
            String apellido = scanner.nextLine();
            
            System.out.print("Ingrese su saldo inicial: ");
            double saldo = scanner.nextDouble();
            cliente = new Cliente(nombre, apellido, cedula, saldo);
            
            gestor.guardarCliente(cliente);
            System.out.println("\nUsted ha sido agregado a la cola y registrado en el sistema. Espere su turno.");
            return;
        }

        //si el primer cliente es el que metio su cedula, hace su tramite normal
        Cliente primerCliente = colaClientes.verFrente();
        if (primerCliente.getCedula() == cedula) {
            int opcion;
            do {
                System.out.println("\nBienvenido " + cliente.getNombreCompleto() + ".");
                System.out.println("\n¿Qué desea hacer?");
                System.out.println("1. Retiro.");
                System.out.println("2. Depósito.");
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
                        break;
                    case 2:
                        //esto no esta completo, esta puesto para debugear y empezar con todos los otros que faltan
                        System.out.print("\nIngrese el monto a depositar: ");
                        double deposito = scanner.nextDouble();
                        cliente.depositar(deposito);
                        break;
                        
                        //de aqui en adelante terminar
                    case 3:
                        System.out.println("\nSu saldo actual es: $" + cliente.getSaldo());
                        break;
                    case 4:
                        System.out.println("\nActualización de libreta realizada.");
                        break;
                    case 5:
                        System.out.println("\nPago de servicios realizado.");
                        break;
                    case 6:
                        System.out.println("\nSaliendo del trámite.");
                        break;
                    default:
                        System.out.println("\nOpción no válida. Intente nuevamente.");
                }
            } while (opcion != 6);
            colaClientes.desencolar(); //cuando el usuario haga sus 5 tramites o se salga, se desencola
            System.out.println("\nTrámite finalizado. Cliente removido de la cola.");
        } else {
            System.out.println("\nUsted ya está en la cola. Espere su turno.");
        }
    }
    
    //modo cajero
    
        private static void modoTaquilla() {
        Scanner scanner = new Scanner(System.in);
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
                    gestor.cargarClientes(colaClientes); 
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
                    System.out.println("#mensajito de error");
            }
        } while (opcion != 3);

        System.out.println("Fin del día. Cerrando sistema.");
        System.out.println("(Operaciones de los clientes del dia de hoy guardadas en: Taquilla.log)");
    }
}

