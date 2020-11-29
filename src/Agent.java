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
    private Point nextPos;
    private boolean lock;


    public Agent(int number, Environnement env) {
        this.number = number;
        this.env = env;
        isVictime = false;
        isAgresseur = false;
        lock = false;
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

    public void perception() throws Exception {
        env.Perception(this);
    }

    public void action() throws Exception {
        if(isVictime || isAgresseur) {
            if (env.isFree(nextPos)) {
                env.deplacement(nextPos, this);
                this.isVictime = false;
                this.isAgresseur = false;
                env.testLock(this);
            }
        }
        else if (meilleurChemin.size() == 0)
            env.testLock(this);
        else if (meilleurChemin.getFirst() instanceof CaseVide && !meilleurChemin.getFirst().isVictime() && !meilleurChemin.getFirst().isAgresseur()){
            this.isAgresseur = true;
            env.deplacement(meilleurChemin.getFirst().getPos(), this);
            this.isAgresseur = false;
            env.testLock(this);
        }
        else if (meilleurChemin.getFirst() instanceof Agent){
            for (Case currentCase : meilleurChemin) {
                if (currentCase.isVictime() || currentCase.isAgresseur())
                    return;
            }

            LinkedList<Case> chemainfuite = env.meilleurCheminCaseVide(meilleurChemin.getFirst().getPos(), this.pos);
            for (Case currentCase : chemainfuite) {
                if (currentCase.isVictime() || currentCase.isAgresseur())
                    return;
            }
            chemainfuite.getLast().setVictime();
            for (int i = chemainfuite.size()-2; i >= 0; i--)
            {
                chemainfuite.get(i).sendMessage(chemainfuite.get(i+1).getPos());
            }
            meilleurChemin.getFirst().sendMessage(chemainfuite.getFirst().getPos());
            this.isAgresseur = true;
            nextPos = meilleurChemin.getFirst().getPos();
        }
    }


    @Override
    public void run() {
        Semaphore semaphore = env.getSemaphore();
        while (!env.isFinish() && !this.lock){
            try {
                semaphore.acquire();
                perception();
                action();
                //System.out.println(env);
            } catch (Exception e) {
                System.out.println("Whololo " + e);
            }
            finally {
                semaphore.release();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        if (this.getNumber() < 10){
            if (isVictime)
                return "\033[0;34m"+"[ "+this.getNumber()+" ]"+"\033[0;37m";
            if (isAgresseur)
                return "\033[0;31m"+"[ "+this.getNumber()+" ]"+"\033[0;37m";
            if (lock)
                return "\033[1;32m"+"[ "+this.getNumber()+" ]"+"\033[0;37m";
            return "[ "+this.getNumber()+" ]";
        }
        if (this.getNumber() < 100){
            if (isVictime)
                return "\033[0;34m"+"["+this.getNumber()+" ]"+"\033[0;37m";
            if (isAgresseur)
                return "\033[0;31m"+"["+this.getNumber()+" ]"+"\033[0;37m";
            if (lock)
                return "\033[1;32m"+"["+this.getNumber()+" ]"+"\033[0;37m";
            return "["+ this.getNumber()+" ]";
        }
        if (isVictime)
            return "\033[0;34m"+"["+this.getNumber()+"]"+"\033[0;37m";
        if (isAgresseur)
            return "\033[0;31m"+"["+this.getNumber()+"]"+"\033[0;37m";
        if (lock)
            return "\033[1;32m"+"["+this.getNumber()+"]"+"\033[0;37m";
        return "["+ this.getNumber()+"]";

    }

    @Override
    public boolean isAgresseur() {
        return this.isAgresseur;
    }

    @Override
    public boolean isVictime() {
        return this.isVictime;
    }

    @Override
    public void setVictime() {
        this.isVictime = true;
    }

    @Override
    protected void sendMessage(Point nextPos) {
        this.nextPos = nextPos;
        this.isVictime = true;
    }

    @Override
    public boolean getLock() {
        return lock;
    }

    @Override
    public void setLock(boolean lock) {
        this.lock = lock;
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

    public void setMeilleurChemin(LinkedList<Case> meilleureChemin) {
        this.meilleurChemin = meilleureChemin;
    }

}
