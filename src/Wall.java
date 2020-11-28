import java.awt.*;

public class Wall extends Case {

    public int getNumber(){
        return -100;
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public boolean isAgent() {
        return false;
    }

    @Override
    public boolean isGoalReached() {
        return false;
    }

    @Override
    public Point getPos() {
        return null;
    }

    @Override
    public String toString() {
        return "Erreur, vous essayez d'afficher un mur";
    }
}
