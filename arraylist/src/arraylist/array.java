package arraylist;

public class array {
	    public static void main(String[] args) {	        
	        ArrayList<String> nomi = new ArrayList<>();	        
	        nomi.add("Marco");
	        nomi.add("Luca");
	        nomi.add("Giulia");

	        System.out.println("Dopo l'aggiunta: " + nomi);	        

	        System.out.println("Dopo la rimozione: " + nomi);	       
	        nomi.add("Anna");

	        System.out.println("Lista finale: " + nomi);
	    }
	}

