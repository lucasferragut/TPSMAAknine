import java.awt.*;

public abstract class Case {

    public abstract int getNumber();
    public abstract boolean isWall();
    public abstract boolean isAgent();
    public abstract boolean isGoalReached();
    public abstract Point getPos();
    public abstract String toString();
    public abstract boolean isAgresseur() throws Exception;
    public abstract boolean isVictime() throws Exception;
    public abstract void setVictime() throws Exception;
    protected abstract void sendMessage(Point nextPos) throws Exception;
}
