package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        Etal etal = new Etal();
        try {
            etal.acheterProduit(10, null);
        } catch (NullPointerException e) {
            System.err.println("Exception attrapée (acheteur null) : " + e.getMessage());
        }

        try {
            etal.acheterProduit(-5, new Gaulois("Abraracourcix", 8));
        } catch (IllegalArgumentException e) {
            System.err.println("Exception attrapée (quantité négative) : " + e.getMessage());
        }

        try {
            etal.acheterProduit(10, new Gaulois("Abraracourcix", 8));
        } catch (IllegalStateException e) {
            System.err.println("Exception attrapée (étal vide) : " + e.getMessage());
        }

        System.out.println("Fin des tests");
    }
}

