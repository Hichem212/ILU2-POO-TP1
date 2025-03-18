package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.Village.VillageSansChefException;

public class Scenario {

    public static void main(String[] args) {
        // Création du village et de ses habitants
        Village village = new Village("le village des irréductibles", 10, 5);
        Chef abraracourcix = new Chef("Abraracourcix", 10, village);
        village.setChef(abraracourcix);
        Druide druide = new Druide("Panoramix", 2, 5, 10);
        Gaulois obelix = new Gaulois("Obélix", 25);
        Gaulois asterix = new Gaulois("Astérix", 8);
        Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
        Gaulois bonemine = new Gaulois("Bonemine", 7);

        // Ajout des habitants
        village.ajouterHabitant(bonemine);
        village.ajouterHabitant(assurancetourix);
        village.ajouterHabitant(asterix);
        village.ajouterHabitant(obelix);
        village.ajouterHabitant(druide);
        village.ajouterHabitant(abraracourcix);

        // Affichage des villageois avec gestion de l'exception VillageSansChefException
        try {
            System.out.println(village.afficherVillageois());
        } catch (VillageSansChefException e) {
            System.err.println("Erreur : " + e.getMessage());
        }

        // Rechercher des vendeurs de fleurs (au départ, aucun vendeur)
        System.out.println(village.rechercherVendeursProduit("fleurs"));

        // Installation de vendeurs
        System.out.println(village.installerVendeur(bonemine, "fleurs", 20));
        System.out.println(village.rechercherVendeursProduit("fleurs"));

        System.out.println(village.installerVendeur(assurancetourix, "lyres", 5));
        System.out.println(village.installerVendeur(obelix, "menhirs", 2));
        System.out.println(village.installerVendeur(druide, "fleurs", 10));

        // Afficher les vendeurs de fleurs après installation
        System.out.println(village.rechercherVendeursProduit("fleurs"));

        // Achat de produits par différents personnages
        Etal etalFleur = village.rechercherEtal(bonemine);
        if (etalFleur != null) {
            try {
                System.out.println(etalFleur.acheterProduit(10, abraracourcix));
                System.out.println(etalFleur.acheterProduit(15, obelix));
                System.out.println(etalFleur.acheterProduit(15, assurancetourix));
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.err.println("Erreur lors de l'achat : " + e.getMessage());
            }
        } else {
            System.err.println("Bonemine n'occupe aucun étal.");
        }

        // Le vendeur Bonemine quitte son étal
        System.out.println(village.partirVendeur(bonemine));

        // Affichage du marché du village
        System.out.println(village.afficherMarche());

        // Tester un village sans chef pour vérifier l'exception VillageSansChefException
        try {
            Village village1 = new Village("Village Gaulois", 10, 5);
            System.out.println(village1.afficherVillageois());
        } catch (VillageSansChefException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
