package java1;

public class studente {
	
	    private String nome;
	    private String cognome;
	    private int annoNascita;

	    public studente(String nome, String cognome, int annoNascita) {
	        this.nome = nome;
	        this.cognome = cognome;
	        this.annoNascita = annoNascita;
	    }

	    public void stampaScheda() {
	        System.out.println(nome + " " + cognome + ", nato nel " + annoNascita);
	    }

	    public static void main(String[] args) {
	        studente studente1 = new studente("Mario", "Rossi", 2004);
	        studente studente2 = new studente("Laura", "Bianchi", 2005);

	        studente1.stampaScheda(); 
	        studente2.stampaScheda(); 
	    }
	}

