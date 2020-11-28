import java.awt.*;

public abstract class Case {

    public abstract int getNumber();
    public abstract boolean isWall();
    public abstract boolean isAgent();
    public abstract boolean isGoalReached();
    public abstract Point getPos();
    public abstract String toString();
    public abstract boolean isAgresseur();
    public abstract boolean isVictime();
}
