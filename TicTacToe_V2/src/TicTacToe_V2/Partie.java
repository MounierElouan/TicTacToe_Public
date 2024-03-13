package TicTacToe_V2;

import TicTacToe_V2._utile.*;

public class Partie {
    public Partie(int typePartie, Robot robot) {
        if (typePartie == 1) {
            System.out.println("\nPartie lancée : Joueur vs Joueur.\n");
            partieJJ();
        } else if (typePartie == 2) {
            System.out.println("\nPartie lancée : Joueur vs Robot.\n");
            if (demanderOpposantRobot() == 1) {
                partieJR(robot);
            } else {
                partieRJ(robot);
            }
        } else if (typePartie == 3) {
            System.out.println("\nPartie lancée : Robot vs Robot.\n");
            partieRR(robot);
        }
    }

    private void partieRR(Robot robot) {
        int nbPartiesTotal = Utile.lireInt("Combien de parties les robots vont jouer ? ");
        int nbPartiesJouees = 0;
        int pourcentageTermine = 0;
        int partiesGagneesRobot1 = 0;
        int partiesGagneesRobot2 = 0;

        while (nbPartiesJouees < nbPartiesTotal) {
            int tour = 1;
            Plateau plateau = new Plateau();

            do {
                plateau.setCase(robot.jouer(plateau), tour);
                tour++;
            } while (!plateau.estGagnant());
            if (plateau.partieNulle) {
                tour = -1;
            }
            robot.gagne(plateau);
            robot.perd(plateau);
            if (tour % 2 == 1) {
                partiesGagneesRobot1++;
            }
            if (tour % 2 == 0) {
                partiesGagneesRobot2++;
            }
            nbPartiesJouees++;
            if ((100.0 / nbPartiesTotal) * nbPartiesJouees > pourcentageTermine) {
                pourcentageTermine++;
                System.out.println(pourcentageTermine + "% des parties ont été jouées.");
            }
        }
        System.out.print(nbPartiesTotal - (partiesGagneesRobot1 + partiesGagneesRobot2) + " parties nulles, ");
        System.out.print(partiesGagneesRobot1 + " parties gagnées par le robot 1 et ");
        System.out.println(partiesGagneesRobot2 + " parties gagnées par le robot 2 sur " + nbPartiesTotal + " parties au total.");
    }

    private void partieJR(Robot robot) {
        int tour = 1;
        Plateau plateau = new Plateau();
        String joueur1 = Utile.lireString("Quel est le nom du joueur ? ");
        String joueur2 = "Robot";

        if (joueur1.replaceAll("\\s+", "").equals("")) {
            joueur1 = "Joueur";
        }
        do {
            if (tour % 2 == 1) {
                robot.ajouter(plateau);
                plateau.setCase(demanderQuoiJouer(joueur1, tour, plateau), tour);
                tour++;
            } else if (tour % 2 == 0) {
                System.out.println("Le robot joue...");
                plateau.setCase(robot.jouer(plateau), tour);
                tour++;
            }
            System.out.println(plateau.afficherPlateau() + "\n");
        } while (!plateau.estGagnant());
        if (plateau.partieNulle) {
            tour = -1;
        }
        if (tour % 2 == 0) {
            robot.perd(plateau);
        }
        if (tour % 2 == 1) {
            robot.gagne(plateau);
        }
        afficherGagnant(tour % 2, joueur1, joueur2);

    }

    private void partieRJ(Robot robot) {
        int tour = 1;
        Plateau plateau = new Plateau();
        String joueur1 = "Robot";
        String joueur2 = Utile.lireString("Quel est le nom du joueur ? ");

        if (joueur2.replaceAll("\\s+", "").equals("")) {
            joueur2 = "Joueur";
        }
        do {
            if (tour % 2 == 1) {
                System.out.println("Le robot joue...");
                plateau.setCase(robot.jouer(plateau), tour);
                tour++;
            } else if (tour % 2 == 0) {
                robot.ajouter(plateau);
                plateau.setCase(demanderQuoiJouer(joueur1, tour, plateau), tour);
                tour++;
            }
            System.out.println(plateau.afficherPlateau() + "\n");
        } while (!plateau.estGagnant());
        if (plateau.partieNulle) {
            tour = -1;
        }
        if (tour % 2 == 1) {
            robot.gagne(plateau);
        }
        if (tour % 2 == 0) {
            robot.perd(plateau);
        }
        afficherGagnant(tour % 2, joueur1, joueur2);
    }

    private void partieJJ() {
        int tour = 1;
        Plateau plateau = new Plateau();
        String joueur1 = Utile.lireString("Quel est le nom du joueur 1 ? ");
        String joueur2 = Utile.lireString("Quel est le nom du joueur 2 ? ");

        if (joueur1.replaceAll("\\s+", "").equals("")) {
            joueur1 = "Joueur 1";
        }
        if (joueur2.replaceAll("\\s+", "").equals("")) {
            joueur2 = "Joueur 2";
        }
        do {
            if (tour % 2 == 1) {
                plateau.setCase(demanderQuoiJouer(joueur1, tour, plateau), tour);
                tour++;
            } else if (tour % 2 == 0) {
                plateau.setCase(demanderQuoiJouer(joueur2, tour, plateau), tour);
                tour++;
            }
            System.out.println(plateau.afficherPlateau() + "\n");
        } while (!plateau.estGagnant());
        if (plateau.partieNulle) {
            tour = -1;
        }
        afficherGagnant(tour % 2, joueur1, joueur2);
    }

    private void afficherGagnant(int numeroGagnant, String joueur1, String joueur2) {
        if (numeroGagnant == 0) {
            System.out.println(joueur1 + " a gagné, felicitation!\n");
        } else if (numeroGagnant == 1) {
            System.out.println(joueur2 + " a gagné, felicitation!\n");
        } else {
            System.out.println("C'est une partie nulle!\n");
        }
    }

    private int demanderQuoiJouer(String nomJoueur, int tourJoueur, Plateau plateau) {
        int numeroCase = -1;
        String strNumeroCase;
        boolean entreeCorrect = false;

        do {
            strNumeroCase = Utile.lireString("Au tour de " + nomJoueur + " de jouer. Où voulez-vous mettre le symbole '" + getSymbole(tourJoueur) + "' ? ");
            try {
                numeroCase = Integer.parseInt(strNumeroCase);

                if (numeroCase < 1 || numeroCase > 9) {
                    System.out.println("Veuillez entrer un chiffre compris entre 1 et 9 inclusivement.");
                    entreeCorrect = false;
                } else {
                    entreeCorrect = true;
                    if (plateau.getCase(numeroCase - 1).getOrdre() != 10) {
                        System.out.println("Cette case est déjà occupée. Choisissez-en une autre!");
                        entreeCorrect = false;
                    }
                }
            } catch (Exception exception) {
                System.out.println("Veuillez entrer un chiffre compris entre 1 et 9 inclusivement.");
            }
        } while (!entreeCorrect);

        return numeroCase;
    }

    private int demanderOpposantRobot() {
        String reponse;

        do {
            reponse = Utile.lireString("Voulez-vous commencer la partie (1) ou que le robot commence (2) ? ");
            if (!(reponse.equals("1") || reponse.equals("2"))) {
                System.out.println("Veuillez entrer un choix de réponse valide (1 ou 2).");
            }
        } while (!(reponse.equals("1") || reponse.equals("2")));

        return Integer.parseInt(reponse);
    }

    private char getSymbole(int tourJoueur) {
        if (tourJoueur % 2 == 0) {
            return 'O';
        } else {
            return 'X';
        }
    }
}
