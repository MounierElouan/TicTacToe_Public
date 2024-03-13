package TicTacToe_V2;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Robot {
    public static final double POURCENTAGE_EVOLUTION_PERDANT = 0.02; // 0.0 = 0%, 1 = 100%
    public static final double POURCENTAGE_EVOLUTION_GAGNANT = 0.08; // 0.0 = 0%, 1 = 100%
    private static final char FILE_SEPARATOR = File.separatorChar;
    private static final String PATH_DEFAUT = System.getProperty("user.dir") + FILE_SEPARATOR + "_fichiers" + FILE_SEPARATOR;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    ArrayList<ArrayList<Integer>> position = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Double>> chance = new ArrayList<ArrayList<Double>>();

    public Robot() {
        getDonneesRobot();
    }

    private void getDonneesRobot() {
        String ligne;

        try {
            BufferedReader entree = new BufferedReader(new FileReader(PATH_DEFAUT + "DonneesRobot")); //Ouverture de fichier
            ligne = entree.readLine(); //lecture d'une ligne
            while (ligne != null) { // Est-ce que je suis à la fin du fichier
                ajouterDonnees(ligne);
                ligne = entree.readLine(); //lecture d'une ligne
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void ajouterDonnees(String ligne) {
        String[] positionEtChance = ligne.split(";");
        String[] position = positionEtChance[0].split(" ");
        String[] chance = positionEtChance[1].split(" ");

        this.position.add(new ArrayList<Integer>());
        this.chance.add(new ArrayList<Double>());
        for (int i = 0; i < position.length; i++) {
            this.position.get(this.position.size()-1).add(Integer.parseInt(position[i]));
            this.chance.get(this.chance.size()-1).add(Double.parseDouble(chance[i]));
        }
    }

    public void ecrireDonneesRobot() {
        try {
            PrintWriter sortie = new PrintWriter(new FileWriter(PATH_DEFAUT + "DonneesRobot")); //ouverture de fichier
            sortie.print(strDonnees()); //écrire le texte
            sortie.close(); //fermeture du fichier
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private String strDonnees() {
        String str = "";

        for (int i = 0; i < position.size(); i++) {
            for (int j = 0; j < position.get(i).size(); j++) {
                str += position.get(i).get(j);
                if (j + 1 < position.get(i).size()) {
                    str += " ";
                }
            }
            str += ";";
            for (int j = 0; j < chance.get(i).size(); j++) {
                str += chance.get(i).get(j);
                if (j + 1 < chance.get(i).size()) {
                    str += " ";
                }
            }
            if (i + 1 < chance.size()) {
                str += LINE_SEPARATOR;
            }
        }

        return str;
    }

    public int ajouter(Plateau plateau) {
        int emplacement = rechercherPosition(plateau);
        if (emplacement == -1) {
            ajouterPlateau(plateau);
            emplacement = position.size() - 1;
        }

        return emplacement;
    }

    public int jouer(Plateau plateau) {
        int emplacement = ajouter(plateau);
        return getPositionCaseAleatoire(emplacement);
    }

    private int getPositionCaseAleatoire(int emplacement) {
        ArrayList<Double> possibilites = chance.get(emplacement);
        Random rand = new Random();
        double randomChance = rand.nextDouble();
        double totalChance = 0;

        for (int i = 0; i < possibilites.size(); i++) {
            if (possibilites.get(i) + totalChance >= randomChance && totalChance < randomChance) {
                return position.get(emplacement).get(i);
            }
            totalChance += possibilites.get(i);
        }
        throw new ArrayIndexOutOfBoundsException("Aucun emplacement n'a pu être trouvé pour chance = " + randomChance);
    }

    private void ajouterPlateau(Plateau plateau) {
        position.add(new ArrayList<Integer>());
        chance.add(new ArrayList<Double>());
        for (int i = 0; i < 9; i++) {
            if (plateau.getCase(i).getOrdre() == 10) {
                position.get(position.size() - 1).add(plateau.getCase(i).getPosition());
                chance.get(chance.size() - 1).add(1.0 / (9-plateau.longueur()));
            }
        }
    }

    private int rechercherPosition(Plateau plateau) {
        int positionPlateau = -1;
        boolean caseEstEgal = true;
        ArrayList<Integer> positionsLibres = plateau.tabPositionsLibres();

        for (int i = 0; i < position.size() && positionPlateau == -1; i++) {
            if (position.get(i).size() == positionsLibres.size()) {
                for (int j = 0; j < position.get(i).size(); j++) {
                    if (positionsLibres.get(j) != position.get(i).get(j)) {
                        caseEstEgal = false;
                    }
                }
            } else {
                caseEstEgal = false;
            }
            if (caseEstEgal) {
                positionPlateau = i;
            }
            caseEstEgal = true;
        }

        return positionPlateau;
    }

    public void gagne(Plateau plateau) {
        int emplacement;
        int positionDernierCoup;
        int indexDernierCoup;
        int nbCycles = 1;
        double enlever;
        double ajouterTotal;
        int coupGagnant = -1;

        while (plateau.longueur() > 0) {
            ajouterTotal = 0;
            indexDernierCoup = plateau.getIndexDernierCoup();
            positionDernierCoup = plateau.getCase(indexDernierCoup).getPosition();
            plateau.removeCase(indexDernierCoup);
            emplacement = rechercherPosition(plateau);

            for (int i = 0; i < 9-plateau.longueur(); i++) {
                if (position.get(emplacement).get(i) != positionDernierCoup) {
                    enlever = chance.get(emplacement).get(i) * (POURCENTAGE_EVOLUTION_GAGNANT / nbCycles);
                    chance.get(emplacement).set(i, chance.get(emplacement).get(i) - enlever);
                    ajouterTotal += enlever;
                } else {
                    coupGagnant = i;
                }
            }
            chance.get(emplacement).set(coupGagnant, chance.get(emplacement).get(coupGagnant) + ajouterTotal);
            plateau.removeCase(plateau.getIndexDernierCoup());
            nbCycles++;
        }
    }

    public void perd(Plateau plateau) {
        int emplacement;
        int positionDernierCoup;
        int indexDernierCoup;
        int nbCycles = 1;
        double ajouter;
        double enleverTotal;
        int coupPerdant = -1;

        indexDernierCoup = plateau.getIndexDernierCoup();
        plateau.removeCase(indexDernierCoup);

        while (plateau.longueur() > 0) {
            indexDernierCoup = plateau.getIndexDernierCoup();
            positionDernierCoup = plateau.getCase(indexDernierCoup).getPosition();
            plateau.removeCase(indexDernierCoup);
            emplacement = rechercherPosition(plateau);

            for (int i = 0; i < 9-plateau.longueur() || coupPerdant == -1; i++) {
                if (position.get(emplacement).get(i) == positionDernierCoup) {
                    coupPerdant = i;
                }
            }
            enleverTotal = chance.get(emplacement).get(coupPerdant) * (POURCENTAGE_EVOLUTION_PERDANT / (nbCycles));
            chance.get(emplacement).set(coupPerdant, chance.get(emplacement).get(coupPerdant) - enleverTotal);
            ajouter = enleverTotal / (8-plateau.longueur());
            for (int i = 0; i < 9-plateau.longueur(); i++) {
                if (position.get(emplacement).get(i) != positionDernierCoup) {
                    chance.get(emplacement).set(i, chance.get(emplacement).get(i) + ajouter);
                }
            }
            chance.get(emplacement).set(coupPerdant, chance.get(emplacement).get(coupPerdant) + enleverTotal);
            plateau.removeCase(plateau.getIndexDernierCoup());
            nbCycles++;
        }
    }
}