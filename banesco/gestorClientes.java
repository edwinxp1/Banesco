package banesco;

import java.io.*;


public class gestorClientes {
     private String nombreArchivo;

    public gestorClientes(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    // reader
    public void cargarClientes(Cola colaClientes) {
        try (Reader reader = new FileReader(nombreArchivo);
             BufferedReader br = new BufferedReader(reader)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("/");
                if (datos.length == 4) {
                    String nombre = datos[0];
                    String apellido = datos[1];
                    int cedula = Integer.parseInt(datos[2]);
                    double saldo = Double.parseDouble(datos[3]);

                    Cliente cliente = new Cliente(nombre, apellido, cedula, saldo);
                    colaClientes.encolar(cliente);
                }
            }
            System.out.println("\nClientes cargados desde el archivo correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Creando uno nuevo...");
            crearArchivoClientes();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
    }

    // writer
    public void guardarCliente(Cliente cliente) {
        try (Writer writer = new FileWriter(nombreArchivo, true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter pw = new PrintWriter(bw)) {

            pw.println(cliente.getNombre() + "/" + cliente.getApellido() + "/" + 
                       cliente.getCedula() + "/" + cliente.getSaldo());

            System.out.println("Cliente guardado en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }
    
    // usado para debugear
    private void crearArchivoClientes() {
        try (Writer writer = new FileWriter(nombreArchivo);
             PrintWriter pw = new PrintWriter(writer)) {

            pw.println("Juan/Perez/12345/1000.50");
            pw.println("Maria/Gomez/67890/2000.75");
            pw.println("Carlos/Lopez,54321/1500.25");

            System.out.println("Archivo creado con clientes predefinidos.");
        } catch (IOException e) {
            System.out.println("Error al crear el archivo.");
        }
    }
}

