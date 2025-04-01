package banesco;

class nodo {
    String dato; // como esta hecho para movimientos, aqui seran strings
    nodo sig;

    public nodo(String dato) {
        this.dato = dato;
        this.sig = null;
    }
}

public class Lista {
    private nodo L;
    private nodo aux;

    public Lista() {
        L = null;
    }

    public void insertar(String pdato) {
        nodo nuevo = new nodo(pdato);
        if (L == null) {
            L = nuevo;
        } else {
            aux = L;
            while (aux.sig != null) {
                aux = aux.sig;
            }
            aux.sig = nuevo;
        }
    }

    public void mostrar() {
        aux = L;
        if (estaVacia()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }

        StringBuilder movimientos = new StringBuilder("Movimientos: ");
        while (aux != null) {
            movimientos.append(aux.dato).append(" | ");
            aux = aux.sig;
        }
        if (movimientos.length() > 13) {
            movimientos.setLength(movimientos.length() - 3);
        }
        System.out.println(movimientos.toString());
    }

    public void eliminar(String valor) {
        aux = L;
        nodo aux2 = null;
        while (aux != null && !aux.dato.equals(valor)) {
            aux2 = aux;
            aux = aux.sig;
        }
        if (aux != null && aux.dato.equals(valor)) {
            if (aux == L) {
                L = L.sig;  
            } else {
                aux2.sig = aux.sig;
            }
            System.out.println(valor + " eliminado de la lista.");
        } else {
            System.out.println("Movimiento no encontrado en la lista.");
        }
    }

    public boolean estaVacia() {
        return L == null;
    }

    public boolean buscar(String valor) {
        aux = L;
        while (aux != null) {
            if (aux.dato.equals(valor)) {
                return true;
            }
            aux = aux.sig;
        }
        return false;
    }

    public String obtenerMovimientos() {
        StringBuilder movimientos = new StringBuilder();
        aux = L;
        while (aux != null) {
            movimientos.append(aux.dato);
            if (aux.sig != null) {
                movimientos.append("|");
            }
            aux = aux.sig;
        }
        return movimientos.toString();
    }

    public void limpiar() {
        L = null;
    }
}
