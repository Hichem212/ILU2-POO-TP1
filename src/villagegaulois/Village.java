package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import personnages.Druide;

public class Village {
    private String nom;
    private Chef chef;
    private Gaulois[] villageois;
    private int nbVillageois = 0;
    private Marche marche;

    public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
        this.nom = nom;
        villageois = new Gaulois[nbVillageoisMaximum];
        marche = new Marche(nbEtals);
    }
    
    public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
        System.out.println(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".");
        int etalLibre = marche.trouverEtalLibre();
        if (etalLibre != -1) {
            marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
            String resultat = "Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (etalLibre + 1) + ".";
            System.out.println(resultat);
            return resultat;
        } else {
            return "Aucun étal libre pour " + vendeur.getNom() + ".";
        }
    }

    public String rechercherVendeursProduit(String produit) {
        Etal[] etalsTrouves = marche.trouverEtals(produit);
        if (etalsTrouves == null || etalsTrouves.length == 0) {
            return "Il n'y a pas de vendeur qui propose des " + produit + " au marché.";
        }

        StringBuilder chaine = new StringBuilder("Les vendeurs qui proposent des ");
        chaine.append(produit).append(" sont :\n");
        for (Etal etal : etalsTrouves) {
            chaine.append("- ").append(etal.getVendeur().getNom()).append("\n");
        }
        return chaine.toString();
    }

    public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }
    
    public String partirVendeur(Gaulois vendeur) {
        Etal etal = rechercherEtal(vendeur);
        if (etal != null && etal.isEtalOccupe()) {
            String result = etal.libererEtal(); // Suppose que cette méthode retourne une description ou un résumé
            return "Le vendeur " + vendeur.getNom() + " quitte son étal. " + result;
        }
        return vendeur.getNom() + " n'occupe aucun étal.";
    }


    public String afficherMarche() {
        StringBuilder chaine = new StringBuilder("Le marché du village \"" + nom + "\" possède plusieurs étals :\n");
        chaine.append(marche.afficherMarche());
        return chaine.toString();
    }


    private static class Marche {
        Etal[] etals;

        private Marche(int nbEtals) {
            etals = new Etal[nbEtals];
            for (int i = 0; i < nbEtals; i++) {
                etals[i] = new Etal();
            }
        }

        private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
            if (indiceEtal >= 0 && indiceEtal < etals.length) {
                etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
            }
        }

        private int trouverEtalLibre() {
            int indiceEtalLibre = -1;
            for (int i = 0; i < etals.length && indiceEtalLibre < 0; i++) {
                if (!etals[i].isEtalOccupe()) {
                    indiceEtalLibre = i;
                }
            }
            return indiceEtalLibre;
        }

        private Etal[] trouverEtals(String produit) {
            int nbEtal = 0;
            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
                    nbEtal++;
                }
            }
            Etal[] etalsProduitsRecherche = null;
            if (nbEtal > 0) {
                etalsProduitsRecherche = new Etal[nbEtal];
                int nbEtalTrouve = 0;
                for (int i = 0; i < etals.length && nbEtalTrouve < nbEtal; i++) {
                    if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
                        etalsProduitsRecherche[nbEtalTrouve] = etals[i];
                        nbEtalTrouve++;
                    }
                }
            }
            return etalsProduitsRecherche;
        }

        private Etal trouverVendeur(Gaulois gaulois) {
            boolean vendeurTrouve = false;
            Etal etalVendeur = null;
            for (int i = 0; i < etals.length && !vendeurTrouve; i++) {
                Gaulois vendeur = etals[i].getVendeur();
                if (vendeur != null) {
                    vendeurTrouve = vendeur.getNom().equals(gaulois.getNom());
                    if (vendeurTrouve) {
                        etalVendeur = etals[i];
                    }
                }
            }
            return etalVendeur;
        }
        
        private String afficherMarche() {
            StringBuilder chaine = new StringBuilder();
            int nbEtalLibre = 0;

            for (Etal etal : etals) {
                if (etal.isEtalOccupe()) {
                    chaine.append(etal.afficherEtal());
                } else {
                    nbEtalLibre++;
                }
            }

            chaine.append("Il reste " + nbEtalLibre + " étals non utilisés dans le marché.\n");
            return chaine.toString();
        }

    } // Fin de la classe Marche

    public String getNom() {
        return nom;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public void ajouterHabitant(Gaulois gaulois) {
        if (nbVillageois < villageois.length) {
            villageois[nbVillageois] = gaulois;
            nbVillageois++;
        }
    }

    public Gaulois trouverHabitant(String nomGaulois) {
        if (nomGaulois.equals(chef.getNom())) {
            return chef;
        }
        for (int i = 0; i < nbVillageois; i++) {
            Gaulois gaulois = villageois[i];
            if (gaulois.getNom().equals(nomGaulois)) {
                return gaulois;
            }
        }
        return null;
    }

    public String afficherVillageois() {
        StringBuilder chaine = new StringBuilder();
        if (nbVillageois < 1) {
            chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
        } else {
            chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
            for (int i = 0; i < nbVillageois; i++) {
                chaine.append("- " + villageois[i].getNom() + "\n");
            }
        }
        return chaine.toString();
    }
} 
