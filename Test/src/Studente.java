public class Studente {
    // Attributi
    private String nome;
    private String cognome;
    private int annoNascita;

    // Costruttore
    public Studente(String nome, String cognome, int annoNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.annoNascita = annoNascita;
    }

    // Metodo per stampare la scheda dello studente
    public void stampaScheda() {
        System.out.println(nome + " " + cognome + ", nato nel " + annoNascita);
    }

    // Metodo main per testare la classe
    public static void main(String[] args) {
        Studente studente1 = new Studente("Mario", "Rossi", 2004);
        Studente studente2 = new Studente("Laura", "Bianchi", 2005);

        studente1.stampaScheda();  // Mario Rossi, nato nel 2004
        studente2.stampaScheda();  // Laura Bianchi, nato nel 2005
    }
}