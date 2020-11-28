import javax.swing.text.Position;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Agent extends Case implements Runnable {

    private int number;
    private Environnement env;
    private Point pos;
    private final Point objectif;
    private LinkedList<Case> meilleurChemin;
    private boolean isAgresseur;
    private boolean isVictime;


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

    public void action() throws Exception {
        List<PointCardinal> lDirections = new ArrayList<PointCardinal>();
        PointCardinal pc;
        boolean isPossible;
        do{
            pc = PointCardinal.randomDirection(lDirections);
            lDirections.add(pc);
            //isPossible = deplacementPossible(pc);
        }while (!isPossible && lDirections.size() < PointCardinal.getSize());

        if(isPossible){
            env.deplacement(pc,this);
        }

        if (meilleurChemin.getFirst() == null)//Notre agent est à sa case objectif.
            return;
        if (meilleurChemin.getFirst() instanceof CaseVide){
            env.deplacement(meilleurChemin.getFirst().getPos(), this);
            return;
        }
        if (meilleurChemin.getFirst() instanceof Agent){
            env.deplacement(meilleurChemin.getFirst().getPos(), this);
            return;
        }




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

    @Override
    public String toString() {
        return "["+String.valueOf(this.getNumber())+"]";
    }

    @Override
    public boolean isAgresseur() {
        return this.isAgresseur;
    }

    @Override
    public boolean isVictime() {
        return this.isVictime;
    }

    public Point getObjectif(){
        return objectif;
    }

    //Retourne la meilleure direction en fonction de l'objectif de l'agent.
    public PointCardinal MeilleureDirection(Point objectif){
        //Si l'objectif est au NORD
        if (pos.x > objectif.x) {
            return PointCardinal.NORD;
        }
        //Si l'objectif est en SUD
        if (pos.x < objectif.x) {
            return PointCardinal.SUD;
        }
        //Si l'objectif est en EST
        if (pos.y < objectif.y) {
            return PointCardinal.EST;
        }
        //Si l'objectif est en OUEST
        if (pos.y > objectif.y) {
            return PointCardinal.OUEST;
        }
        return null;
    }

    public void setMeilleurChemin(List<Case> meilleureChemin) {
        this.meilleurChemin = meilleureChemin;
    }

}
