//cronometro para mostrar el tiempo restante al usuario al hacer sus tramites
//todo esta trabajado en milisegundos
package banesco;

public class Cronometro {
    private long tiempoInicio;
    private long tiempoFin;
    private long limite;  

    public void iniciar(long limiteSegundos) {
        tiempoInicio = System.currentTimeMillis();
        limite = limiteSegundos * 1000;  
    }

    public void detener() {
        tiempoFin = System.currentTimeMillis();
    }

    public boolean tiempoExcedido() {
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
        return tiempoTranscurrido > limite;
    }

    public double tiempoTranscurrido() {
        return (tiempoFin - tiempoInicio) / 1000.0;  
    }

    public void mostrarTiempo(String operacion) {
        System.out.printf("Tiempo para %s: %.2f segundos\n", operacion, tiempoTranscurrido());
    }
}