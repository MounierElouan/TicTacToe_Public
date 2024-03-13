package TicTacToe_V2;

import java.util.Objects;

public class Case {
    private int position;
    private int ordre = 10;
    private double chance;

    public Case(int position, double chance) {
        setPosition(position);
        setChance(chance);
    }

    public double getChance() {
        return chance;
    }


    public void setChance(double chance) {
        if (chance >= 0 && chance <= 100) {
            this.chance = chance;
        } else {
            throw new IllegalArgumentException("Impossible de set la chance d'une case pour chance = " + chance);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position < 10 && position > 0) {
            this.position = position;
        } else {
            throw new IllegalArgumentException("Impossible de set la position d'une case pour position = " + position);
        }
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        if (ordre >= 1 && ordre <= 10) {
            this.ordre = ordre;
        } else {
            throw new IllegalArgumentException("Impossible de set l'ordre d'une case pour ordre = " + ordre);
        }
    }

    public int getCordX() {
        if (position <= 3) {
            return 2;
        } else if (position <= 6) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getCordY() {
        if (position == 1 || position == 4 || position == 7) {
            return 0;
        } else if (position == 2 || position == 5 || position == 8) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case c = (Case) o;
        return position == c.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return position + ", " + ordre + ", " + chance + "\n";
    }
}
