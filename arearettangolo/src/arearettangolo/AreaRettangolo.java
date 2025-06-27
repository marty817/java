
package arearettangolo;

import java.util.Scanner;

public class AreaRettangolo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
      
        System.out.print("Inserisci la base del rettangolo: ");
        double base = scanner.nextDouble();

        System.out.print("Inserisci l'altezza del rettangolo: ");
        double altezza = scanner.nextDouble();

        
        double area = base * altezza;

        
        System.out.println("L'area del rettangolo è: " + area);
        
        scanner.close();
    }
}