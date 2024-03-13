package TicTacToe_V2._utile;

import java.util.Random;
import java.util.Scanner;

public class Utile {
    public static int trouverStr(String strRecherche, String[] strTab) {
        int indexStr = -1;

        for (int i = 0; i < strTab.length; i++) {
            if (strRecherche.equalsIgnoreCase(strTab[i]) ) {
                indexStr = i;
            }
        }

        return indexStr;
    }

    public static int randomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max doit être plus grand que min");
        }
        Random r = new Random(); //Instanciation de la classe Random
        return r.nextInt((max - min) + 1) + min;
    }

    public static double randomDouble(double max) {
        Random r = new Random(); //Instanciation de la classe Random
        return r.nextDouble()*max;
    }

    public static String lireString(String question) {
        String reponse;
        Scanner sc;

        sc = new Scanner(System.in);

        System.out.print(question);
        reponse = sc.nextLine();

        return reponse;
    }

    public static int lireInt(String question) {
        int entierLu;
        entierLu = Integer.parseInt(lireString(question));
        return  entierLu;
    }

    public static double lireDouble(String question) {
        double doubleLu;
        doubleLu = Double.parseDouble(lireString(question));
        return  doubleLu;
    }
}