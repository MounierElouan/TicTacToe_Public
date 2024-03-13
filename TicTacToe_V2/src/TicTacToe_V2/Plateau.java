package TicTacToe_V2;

import java.util.ArrayList;
import java.util.Collections;

public class Plateau implements Cloneable {
    public Case[] plateau = new Case[9];
    public boolean partieNulle = false;

    public Plateau() {
        setPlateau();
    }

    public Plateau clone() throws CloneNotSupportedException {
        Plateau clone;
        clone = (Plateau) super.clone();

        return clone;
    }

    private void setPlateau() {
        for (int i = 0; i < plateau.length; i++) {
            plateau[i] = new Case(i + 1, (double) 1 / 9);
        }
    }

    public Case getCase(int i) {
        if (i >= 0 && i < plateau.length) {
            return plateau[i];
        } else {
            throw new IllegalArgumentException("Impossible de récupérer une case du plateau pour indice = " + i);
        }
    }

    public void setCase(int i, int ordre) {
        i--;
        if (i < 0 || i >= plateau.length) {
            throw new IllegalArgumentException("Impossible de récupérer une case du plateau pour indice = " + i);
        }
        plateau[i].setOrdre(ordre);
    }

    public void removeCase(int i) {
        if (i < 0 || i >= plateau.length) {
            throw new IllegalArgumentException("Impossible de récupérer une case du plateau pour indice = " + i);
        }
        plateau[i].setOrdre(10);
    }

    public int getIndexDernierCoup() {
        trierOrdre();
        for (int i = 0; i < plateau.length; i++) {
            if (i == plateau.length - 1 || plateau[i + 1].getOrdre() == 10) {
                int index = plateau[i].getPosition();
                trierPosition();
                return index - 1;
            }
        }
        throw new ArrayIndexOutOfBoundsException("Pas de dernier coup trouvé pour plateau = " + this);
    }

    public int longueur() {
        int nbCasesValides = 0;

        for (int i = 0; i < plateau.length; i++) {
            if (plateau[i].getOrdre() != 10) {
                nbCasesValides++;
            }
        }

        return nbCasesValides;
    }

    public void trierPosition() {
        Case switchCase;
        for (int i = 0; i < plateau.length; i++) {
            for (int j = i + 1; j < plateau.length; j++) {
                if (plateau[j].getPosition() < plateau[i].getPosition()) {
                    switchCase = plateau[i];
                    plateau[i] = plateau[j];
                    plateau[j] = switchCase;
                }
            }
        }
    }

    public void trierOrdre() {
        Case switchCase;
        for (int i = 0; i < plateau.length; i++) {
            for (int j = i + 1; j < plateau.length; j++) {
                if (plateau[j].getOrdre() < plateau[i].getOrdre()) {
                    switchCase = plateau[i];
                    plateau[i] = plateau[j];
                    plateau[j] = switchCase;
                }
            }
        }
    }

    public boolean estGagnant() {
        if (longueur() < 5) {
            return false;
        }

        int sameY = 0;
        int sameX = 0;
        int sameDiag1 = 0;
        int sameDiag2 = 0;
        boolean pieceCentrale = false;
        int longueur = longueur();

        trierOrdre();
        Case[] casesGagnantes = new Case[((longueur + 1) / 2)];
        for (int i = longueur - 1; i >= 0; i -= 2) {
            casesGagnantes[i / 2] = plateau[i];
        }

        for (int i = 0; i < casesGagnantes.length - 1; i++) {
            for (int j = i + 1; j < casesGagnantes.length; j++) {
                if (casesGagnantes[i].getCordX() == casesGagnantes[j].getCordX()) {
                    sameX++;
                }
                if (casesGagnantes[i].getCordY() == casesGagnantes[j].getCordY()) {
                    sameY++;
                }
            }
        }

        for (int i = 0; i < casesGagnantes.length; i++) {
            if (casesGagnantes[i].getPosition() == 5) {
                pieceCentrale = true;
            }
        }

        if (pieceCentrale) {
            for (int i = 0; i < casesGagnantes.length; i++) {
                if (casesGagnantes[i].getPosition() == 1 || casesGagnantes[i].getPosition() == 9) {
                    sameDiag1++;
                }
                if (casesGagnantes[i].getPosition() == 3 || casesGagnantes[i].getPosition() == 7) {
                    sameDiag2++;
                }
            }
        }

        trierPosition();
        boolean estGagnant = sameDiag1 == 2 || sameDiag2 == 2 || sameX == 3 || sameY == 3;
        if (!estGagnant && longueur == 9) {
            partieNulle = true;
        }
        return estGagnant || partieNulle;
    }

    public ArrayList<Integer> tabPositionsLibres() {
        ArrayList<Integer> positionsLibres = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            if (plateau[i].getOrdre() == 10) {
                positionsLibres.add(plateau[i].getPosition());
            }
        }
        return positionsLibres;
    }

    public String afficherPlateau() {
        String plateauStr = "";

        for (int y = 13; y >= 0; y--) {
            for (int x = 0; x < 14; x++) {
                if (x == 4 || x == 9 || y == 4 || y == 9) {
                    plateauStr += " * ";
                } else if (getSymboleCaseDuPlateau(x, y) == 1) {
                    plateauStr += getStrX(x, y);
                } else if (getSymboleCaseDuPlateau(x, y) == 0) {
                    plateauStr += getStrO(x, y);
                } else {
                    plateauStr += "   ";
                }
            }
            plateauStr += "\n";
        }
        return plateauStr;
    }

    private String getStrX(int x, int y) {
        if ((x + 1) % 5 == (y + 1) % 5) {
            return " X ";
        } else if ((x + 1) % 5 == 5 - ((y + 1) % 5)) {
            return " X ";
        } else return "   ";
    }

    private String getStrO(int x, int y) {
        if ((x + 1) % 5 == (y + 1) % 5) {
            return "   ";
        } else if ((x + 1) % 5 == 5 - ((y + 1) % 5)) {
            return "   ";
        } else return " O ";
    }

    private int getSymboleCaseDuPlateau(int xStr, int yStr) {
        int caseX;
        int caseY;

        if (xStr > 9) {
            caseY = 2;
        } else if (xStr > 4) {
            caseY = 1;
        } else {
            caseY = 0;
        }
        if (yStr > 9) {
            caseX = 2;
        } else if (yStr > 4) {
            caseX = 1;
        } else {
            caseX = 0;
        }

        for (int i = 0; i < 9; i++) {
            if (plateau[i].getCordX() == caseX && plateau[i].getCordY() == caseY && plateau[i].getOrdre() != 10) {
                return plateau[i].getOrdre() % 2;
            }
        }
        return 2;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < plateau.length; i++) {
            str += plateau[i].getPosition();
            if (i != plateau.length - 1) {
                str += " ";
            }
        }
        str += ";";
        for (int i = 0; i < plateau.length; i++) {
            str += plateau[i].getOrdre();
            if (i != plateau.length - 1) {
                str += " ";
            }
        }
        str += ";";
        for (int i = 0; i < plateau.length; i++) {
            str += plateau[i].getChance();
            if (i != plateau.length - 1) {
                str += " ";
            }
        }
        return str;
    }
}
