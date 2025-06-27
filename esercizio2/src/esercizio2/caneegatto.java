package esercizio2;

public class caneegatto {

}
public class Cane extends Animale {
    public Cane(String nome) {
        super(nome);
    }

    @Override
    public void emettiVerso() {
        System.out.println("Bau!");
    }

    public static void main(String[] args) {
        Cane c = new Cane("Fido");
        c.descrizione();
        c.emettiVerso();
    }
}
