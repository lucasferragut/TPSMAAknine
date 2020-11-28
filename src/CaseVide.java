import java.awt.*;

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
        return " . ";
    }

    @Override
    public boolean isAgresseur() {
        return false;
    }

    @Override
    public boolean isVictime() {
        return this.isVictime;
    }
}
