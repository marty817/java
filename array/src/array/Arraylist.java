package array;

public class Arraylist {
	public class Main {
	import java.util.ArrayList;
	
	    static class Prodotto {
	        String nome;
	        double prezzo;

	        public Prodotto(String nome, double prezzo) {
	            this.nome = nome;
	            this.prezzo = prezzo;
	        }

	      
	        public String toString() {
	            return nome + " - €" + prezzo;
	        }
	    

	    public static void main(String[] args) {
	      
	        ArrayList<Prodotto> prodotti = new ArrayList<>();

	        prodotti.add(new Prodotto("Pane", 1.50));
	        prodotti.add(new Prodotto("Latte", 0.99));
	        prodotti.add(new Prodotto("Pasta", 1.20));

	  
	        System.out.println("Prodotti iniziali:");
	        for (Prodotto p : prodotti) {
	            System.out.println(p);
	        }

	        
	        prodotti.remove(1);

	      
	        prodotti.add(new Prodotto("Formaggio", 2.30));

	      
	        System.out.println("\nProdotti aggiornati:");
	        for (Prodotto p : prodotti) {
	            System.out.println(p);
	        }
	    }
	}