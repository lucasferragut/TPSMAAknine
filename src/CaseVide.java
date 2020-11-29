import java.awt.*;
import java.util.concurrent.ExecutionException;

public class CaseVide extends Case {
    private Point pos;
    private boolean isVictime;

    public CaseVide(Point pos, boolean isVictime){
        this.pos = pos;
        this.isVictime = isVictime;
    }

    @Override
    public int getNumber() {
        return -101;
    }

    @Override
    public boolean isWall() {
        return false;
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
        return pos;
    }

    @Override
    public String toString() {
        if (isVictime)
            return "\033[0;34m" + "  .  " + "\033[0;37m";
        return "  .  ";
    }

    @Override
    public boolean isAgresseur() {
        return false;
    }

    @Override
    public boolean isVictime() {
        return this.isVictime;
    }

    @Override
    public void setVictime(){
        this.isVictime = true;
    }

    @Override
    protected void sendMessage(Message message) throws Exception {
        throw new Exception("On ne peut pas evoyer de message a une case vide");
    }

    @Override
    public boolean getLock() {
        return false;
    }

    @Override
    public void setLock(boolean lock) throws Exception {
        throw new Exception("Ne peut pas etre lock");
    }
}
