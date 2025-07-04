package java2;

public class Persona {
	    String nome, cognome;
	    Persona(String n, String c) {
	        nome = n; cognome = c;
	    }
	    void presentati() {
	        System.out.println("Ciao, sono " + nome + " " + cognome);
	    }
	}

	class Studente extends Persona {
	    String matricola;
	    Studente(String n, String c, String m) {
	        super(n, c);
	        matricola = m;
	    }
	    @Override
	    void presentati() {
	        System.out.println("Sono lo studente " + nome + " " + cognome + ", matricola: " + matricola);
	    }
	}

	class Professore extends Persona {
	    String materia;
	    Professore(String n, String c, String mat) {
	        super(n, c);
	        materia = mat;
	    }
	    @Override
	    void presentati() {
	        System.out.println("Sono il prof. " + nome + " " + cognome + ", insegno " + materia);
	    }
	}

	public class Main {
	    public static void main(String[] args) {
	        Persona[] persone = {
	            new Studente("Mario", "Rossi", "A123"),
	            new Professore("Anna", "Bianchi", "Matematica"),
	            new Persona("Luca", "Verdi")
	        };

	        for (Persona p : persone) {
	            p.presentati();
	        }
	    }
	}
