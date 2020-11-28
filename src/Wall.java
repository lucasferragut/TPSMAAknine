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

    @Override
    public boolean isAgresseur() throws Exception {
        throw new Exception("Un wall ne peut pas etre un agresseur");
    }

    @Override
    public boolean isVictime() throws Exception {
        throw new Exception("Un wall ne peut pas etre une victime");
    }

    @Override
    public void setVictime() throws Exception {
        throw new Exception("Un wall ne peut pas etre une victime");

    }

    @Override
    protected void sendMessage(Point nextPos) throws Exception {
        throw new Exception("On ne peut pas envoyer de meaage a un wall");
    }
}
