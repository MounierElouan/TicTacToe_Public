package TicTacToe_V2;

import TicTacToe_V2._utile.*;

public class TicTacToeV2App {
    public TicTacToeV2App() {
        jouerTicTacToe();
    }

    private void jouerTicTacToe() {
        Robot robot = new Robot();

        lireRegles();
        do {
            new Partie(demanderTypePartie(), robot);
        } while (rejouerPartie());
        robot.ecrireDonneesRobot();
        System.out.println("Données enregistrées");
        System.out.println("Au revoir.");
    }

    private void lireRegles() {
        System.out.println("Tic Tac Toe :\nLes joueurs vont, chacun leur tour, choisir une case du plateau de jeu pour placer le symbole qu'ils représentent.");
        System.out.println("Les cases du plateau sont numérotés comme ceci :");
        String str = "      *      *\n  1   *  2   *  3\n      *      *\n* * * * * * * * * * *\n      *      *\n  4   *  5   *  6\n      *      *\n";
        str += "* * * * * * * * * * *\n      *      *\n  7   *  8   *  9\n      *      *\n";
        System.out.println(str + "\nIl existe plusieurs modes :");
    }

    private int demanderTypePartie() {
        String reponse;

        do {
            reponse = Utile.lireString("1 - Joueur vs Joueur\n2 - Joueur vs Robot\n3 - Robot vs Robot\nQuelle type de partie voulez-vous jouer ? ");
            if (!(reponse.equals("1") || reponse.equals("2") || reponse.equals("3"))) {
                System.out.println("Veuillez entrer un chiffre compris entre 1 et 3 inclusivement.");
            }
        } while (!(reponse.equals("1") || reponse.equals("2") || reponse.equals("3")));

        return Integer.parseInt(reponse);
    }

    private boolean rejouerPartie() {
        String rejouer;

        do {
            rejouer = Utile.lireString("Voulez-vous rejouer une partie de tic tac toe ? ");
            if (!rejouer.equalsIgnoreCase("o") && !rejouer.equalsIgnoreCase("n")) {
                System.out.println("Entrez un choix valide (o, n)");
            }
        } while (!rejouer.equals("o") && !rejouer.equals("n"));

        return rejouer.equalsIgnoreCase("o");
    }

    public static void main(String[] args) {
        new TicTacToeV2App();
    }
}
