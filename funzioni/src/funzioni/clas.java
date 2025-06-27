package funzioni;

public class clas 
{
  
    public static int somma(int a, int b) {
        return a + b;
    }

    
    public static int differenza(int a, int b) {
        return a - b;
    }

    
    public static int moltiplicazione(int a, int b) {
        return a * b;
    }

    
    public static double divisione(int a, int b) {
        if (b == 0) {
            System.out.println("Errore: divisione per zero!");
            return 0; 
        }
        return a / b;
    }

    public static void main(String[] args) {
        int x = 10;
        int y = 5;

        System.out.println("Somma: " + somma(x, y));
        System.out.println("Differenza: " + differenza(x, y));
        System.out.println("Moltiplicazione: " + moltiplicazione(x, y));
        System.out.println("Divisione: " + divisione(x, y));
    }
}


