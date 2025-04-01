package banesco;

public class Cliente {
    private String nombre;
    private String apellido;
    private int cedula;
    private double saldo;

    public Cliente(String nombre, String apellido, int cedula, double saldoInicial) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.saldo = saldoInicial;
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

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    public void retirar(double monto) {
        if (monto > 0 && saldo >= monto) {
            saldo -= monto;
            System.out.println("Retiro exitoso. Nuevo saldo: $" + saldo);
        } else {
            System.out.println("Error: Saldo insuficiente o monto inválido.");
        }
    }


    
    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
        } else {
            System.out.println("Monto inválido para depósito");
        }
    }
    
    
}
