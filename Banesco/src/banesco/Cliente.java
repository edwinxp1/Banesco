package banesco;
import java.io.*;
//libreria para manejar las fechas
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Cliente {
    private String nombre;
    private String apellido;
    private int cedula;
    private double saldo;
    //lista para guardar los movimientos en la mini base de datos "movimientos.log"
    private Lista movimientos;

    public Cliente(String nombre, String apellido, int cedula, double saldoInicial) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.saldo = saldoInicial;
        //# 
        this.movimientos = new Lista();
        cargarMovimientos();
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }

    public double getSaldo() {
        return saldo;
    }
    
    public int getCedula() {
        return cedula;
    }
    
    public Lista getMovimientos() {
        return movimientos;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    //retirar y depositar ahora funcionan con la lista y los movimientos
    
    public void retirar(double monto) {
        if (monto > 0 && saldo >= monto) {
            saldo -= monto;
            System.out.println("Retiro exitoso. Nuevo saldo: $" + saldo);
            agregarMovimiento("Retiro: -" + monto);  
        } else {
            System.out.println("Error: Saldo insuficiente o monto inválido.");
        }
    }

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.println("Depósito exitoso. Nuevo saldo: $" + saldo);
            agregarMovimiento("Depósito: +" + monto);  
        } else {
            System.out.println("Monto inválido para depósito.");
        }
    }
    
    //logica para: mostrar, leer y guardar los movimientos desde el archivo "movimientos.log"
    //esto es necesario para e case 3 y 4
    public void mostrarMovimientos() {
        System.out.println("\nMovimientos de " + getNombreCompleto() + ":");
        movimientos.mostrar();
    }


    public void agregarMovimiento(String movimiento) {
    String fechaHora = Fecha();  
    String movimientoConFecha = "[" + fechaHora + "] " + movimiento;
    movimientos.insertar(movimientoConFecha);  
    guardarMovimiento(movimientoConFecha);  
    Enlibretar(movimientoConFecha);
    
}


    private void guardarMovimiento(String movimiento) {
        String nombreArchivo = "Movimientos_" + cedula + ".log";
        try (FileWriter fw = new FileWriter(nombreArchivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(movimiento);
        } catch (IOException e) {
            System.out.println("Error al guardar movimientos: " + e.getMessage());
        }
    }

    private void cargarMovimientos() {
        String nombreArchivo = "Movimientos_" + cedula + ".log";
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            return; 
        }
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                movimientos.insertar(linea);  
            }
        } catch (IOException e) {
            System.out.println("Error al cargar movimientos: " + e.getMessage());
        }
    }
    
    
    //metodo para ponerle fecha a los movimientos
    private String Fecha() {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date fecha = new Date();
    return formato.format(fecha);
}
    
    //metodos para la libreta
     
    private void Enlibretar(String movimiento) {
        String nombreArchivo = "libreta_" + cedula + ".log";
        try (FileWriter fw = new FileWriter(nombreArchivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(movimiento);
        } catch (IOException e) {
            System.out.println("Error al guardar en la libreta: " + e.getMessage());
        }
    }
    
    //metodo para ver la libreta 
    
    public void mirarLibreta() {
        String nombreArchivo = "libreta_" + cedula + ".log";
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("\nNo hay registros en la libreta para " + getNombreCompleto() + ".");
            return;
        }
        System.out.println("\n--- Libreta de " + getNombreCompleto() + " ---");
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea); 
            }
        } catch (IOException e) {
            System.out.println("Error al leer la libreta: " + e.getMessage());
        }
    }
    
    
    // pago de servicios
    public void pagoDeServicios() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Pago de Servicios ---");
        System.out.println("1. Luz - $5");
        System.out.println("2. Hidrológicas - $5");
        System.out.println("3. Teléfono - $5");
        System.out.println("4. Internet - $20");
        System.out.print("Seleccione el servicio a pagar: ");

        int opcion = scanner.nextInt();
        double monto = 0.0;
        String servicio = "";

        switch (opcion) {
            case 1:
                servicio = "Luz";
                monto = 5.0;
                break;
            case 2:
                servicio = "Hidrológicas";
                monto = 5.0;
                break;
            case 3:
                servicio = "Teléfono";
                monto = 5.0;
                break;
            case 4:
                servicio = "Internet";
                monto = 20.0;
                break;
            default:
                System.out.println("\nOpción inválida. Pago cancelado.");
                return;
        }

        if (saldo >= monto) {
            saldo -= monto;
            String movimiento = "Pago de " + servicio + ": -" + monto;
            System.out.println("\nPago de " + servicio + " realizado exitosamente. Nuevo saldo: $" + saldo);
            agregarMovimiento(movimiento);
        } else {
            System.out.println("\nSaldo insuficiente para pagar " + servicio + ".");
        }
    }
    
}
