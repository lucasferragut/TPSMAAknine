import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Agent extends Case implements Runnable {

    private int number;
    private Environnement env;
    private Point pos;
    private final Point objectif;
    private Map<PointCardinal, Case> voisinnage;

    public Agent(int number, Environnement env) {
        this.number = number;
        this.env = env;
        this.objectif = new Point(number / env.getSize(), number % env.getSize());
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isAgent() {
        return true;
    }

    @Override
    public boolean isGoalReached() {
        if (pos.x == objectif.x && pos.y == objectif.y)
            return true;
        return false;
    }

    public void perception() {
        env.Perception(this);
    }

    public void action() {
        List<PointCardinal> lDirections = new ArrayList<PointCardinal>();
        PointCardinal pc;
        boolean isPossible;
        do{
            pc = PointCardinal.randomDirection(lDirections);
            lDirections.add(pc);
            isPossible = deplacementPossible(pc);
        }while (!isPossible && lDirections.size() < PointCardinal.getSize());

        if(isPossible){
            env.deplacement(pc,this);
        }
    }

    private boolean deplacementPossible(PointCardinal pc) {
        Case c = voisinnage.get(pc);
        if(c == null)
            return true;
        return false;
    }

    @Override
    public void run() {
        Semaphore semaphore = env.getSemaphore();
        while (!env.isFinish()){
            try {
                semaphore.acquire();
                perception();
                action();
            } catch (InterruptedException e) {
                System.out.println("Whololo");
            }
            finally {
                semaphore.release();
            }

        }

    }

    public void setPos(int x, int y) {
        this.pos = new Point(x,y);
    }

    public Point getPos() {
        return pos;
    }

    public Point getObjectif(){
        return objectif;
    }

    public void setVoisinnage(Map<PointCardinal, Case> voisinnage) {
        this.voisinnage = voisinnage;
    }
}
