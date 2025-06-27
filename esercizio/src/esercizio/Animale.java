
package esercizio;

public abstract class Animale {
    protected String nome;

    
    
    public Animale(String nome) {
        this.nome = nome;
    }
    

    public abstract void emettiVerso();

    public void descrizione() {
        System.out.println("Sono un animale di nome " + nome);
    }
    public static void main(String[] args) {
        System.out.println("La classe Animale è astratta e non può essere istanziata direttamente.");
    }
}

