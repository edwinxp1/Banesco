package banesco;


public class Cola {

    private class Nodo {
        private Cliente cliente;
        private Nodo siguiente;

        public Nodo(Cliente cliente) {
            this.cliente = cliente;
            this.siguiente = null;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente) {
            this.siguiente = siguiente;
        }
    }

    //A partir de aqui empieza la cola
    //sus atributos
    private Nodo frente;
    private Nodo finalCola;
    private int tamano;

    //contructor
    public Cola() {
        this.frente = null;
        this.finalCola = null;
        this.tamano = 0;
    }

    //metodos correspondientes
    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamano() {
        return this.tamano;
    }

    public void encolar(Cliente cliente) {
        Nodo nuevo = new Nodo(cliente);
        if (estaVacia()) {
            this.frente = nuevo;
            this.finalCola = this.frente;
        } else {
            this.finalCola.setSiguiente(nuevo);
            this.finalCola = nuevo;
        }
        tamano++;
    }

    public Cliente desencolar() {
        if (estaVacia()) {
            return null;
        } else {
            Cliente cliente = frente.getCliente();
            frente = frente.getSiguiente();
            if (frente == null) {
                finalCola = null;
            }
            tamano--;
            return cliente;
        }
    }

    public Cliente verFrente() {
        return estaVacia() ? null : frente.getCliente();
    }

    public boolean contiene(int cedula) {
        Nodo aux = frente;
        while (aux != null) {
            if (aux.getCliente().getCedula() == cedula) {
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }

    public Cliente buscarCliente(int cedula) {
        Nodo aux = frente;
        while (aux != null) {
            if (aux.getCliente().getCedula() == cedula) {
                return aux.getCliente();
            }
            aux = aux.getSiguiente();
        }
        return null;
    }

    public void imprimirCola() {
        Nodo aux = frente;
        if (estaVacia()) {
            System.out.println("\nLa cola está vacía.");
            return;
        }

        System.out.println("\nClientes en la cola:");
        while (aux != null) {
            System.out.println("- " + aux.getCliente().getNombreCompleto() + " (Cédula: " + aux.getCliente().getCedula() + ")");
            aux = aux.getSiguiente();
        }
    }
     
}
