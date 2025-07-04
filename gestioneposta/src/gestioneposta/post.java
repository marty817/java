package gestioneposta;

import java.util.LinkedList;
import java.util.Queue;

public class post {
    private Queue<Persone> coda;

    public post() {
        coda = new LinkedList<>();
    }

    public void entraInCoda(Persone p) {
        coda.add(p);
        System.out.println(p.getNome() + " è entrato in coda.");
    }

    public String chiEIlProssimo() throws CodaVuotaException {
        Persone prossimo = coda.peek();
        if (prossimo != null) {
            return prossimo.getNome();
        } else {
            throw new CodaVuotaException("La coda è vuota! Nessuno da servire.");
        }
    }

    public String servireProssimo() throws CodaVuotaException {
        Persone servito = coda.poll();
        if (servito != null) {
            return servito.getNome() + " è stato servito.";
        } else {
            throw new CodaVuotaException("La coda è vuota! Nessuno da servire.");
        }
    }

    public void mostraCoda() {
        if (coda.isEmpty()) {
            System.out.println("La coda è vuota.");
        } else {
            System.out.println("Persone in coda:");
            for (Persone p : coda) {
                System.out.println("- " + p.getNome());
            }
        }
    }
}
