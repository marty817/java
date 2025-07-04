package gestioneposta;

public class GestionePosta {
    public static void main(String[] args) {
        post ufficioPostale = new post();

        Persone p1 = new Persone("Luca");
        Persone p2 = new Persone("Giulia");
        Persone p3 = new Persone("Marco");

        ufficioPostale.entraInCoda(p1);
        ufficioPostale.entraInCoda(p2);
        ufficioPostale.entraInCoda(p3);

        System.out.println();
        ufficioPostale.mostraCoda();

        try {
            System.out.println("\nIl prossimo da servire è: " + ufficioPostale.chiEIlProssimo());

            System.out.println("\n" + ufficioPostale.servireProssimo());
            System.out.println(ufficioPostale.servireProssimo());
            System.out.println(ufficioPostale.servireProssimo());


            System.out.println(ufficioPostale.servireProssimo());

        } catch (CodaVuotaException e) {
            System.err.println("⚠️ Errore: " + e.getMessage());
        }

        System.out.println();
        ufficioPostale.mostraCoda();
    }
}