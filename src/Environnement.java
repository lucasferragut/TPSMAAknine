import com.sun.rowset.internal.Row;
import com.sun.xml.internal.ws.api.pipe.Tube;
import javafx.util.Pair;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Environnement {

    private Case[][] grid;
    private static Semaphore semaphore;
    private int nbAgents;

    public Environnement(int size) {
        this.grid = new Agent[size][size];
        semaphore = new Semaphore(1);
    }

    public void PlaceAgents(List<Agent> lAgent){
        Random r = new Random();
        int a = 0;
        int j = 0;
        int i = 0;

        while(a < lAgent.size()){

            i = r.nextInt(this.grid.length);
            j = r.nextInt(this.grid.length);
            if(grid[i][j] == null){
                grid[i][j] = lAgent.get(a);
                lAgent.get(a).setPos(i,j);
                a++;
            }
        }

        for (i = 0; i < this.grid.length; i++){
            for (j = 0; j < this.grid.length; j++){
                if(grid[i][j] == null){
                    grid[i][j] = new CaseVide(new Point(i,j),false);
                }
            }
        }
    }

    public boolean isFinish(){
        for (Case[] u: grid) {
            for (Case elem: u) {
                if(elem != null && !elem.isGoalReached()){
                    return false;
                }
            }
        }
        return true;
    }

    public void deplacement(Point destination, Agent agent) throws Exception {
        Point pos = agent.getPos();
        grid[pos.x][pos.y] = new CaseVide(pos, !agent.isAgresseur());

        if (agent.getPos().x > destination.x+1
                || agent.getPos().x < destination.x-1
                || agent.getPos().y > destination.y+1
                || agent.getPos().y < destination.y-1)
            throw new Exception("tp");
        grid[destination.x][destination.y] = agent;
        agent.setPos(destination.x,destination.y);
    }

    @Override
    public String toString() {
        StringBuilder returnStr = new StringBuilder();
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid.length; j++){
                returnStr.append(grid[i][j]);
            }
            returnStr.append("\n");
        }
        return returnStr.toString();
    }

    public void addAgents(List<Agent> lAgents) throws Exception {
        if (lAgents.size() > (grid.length*grid.length)-2)
            throw new Exception("marche pas");
        this.PlaceAgents(lAgents);
    }

    public int getSize(){
        return grid.length;
    }

    public Semaphore getSemaphore(){
        return semaphore;
    }

    //Retourne une liste de cases correspondant au meilleur chemin possible entre une case de départ (Start) et une case objectif (goal)
    public LinkedList<Case> MeilleurCheminObjectif(Point start, Point goal){
        LinkedList<Case> chemin = new LinkedList<>();
        LinkedList<Tuple<Point, Point>> l = new LinkedList<>();
        l.add(new Tuple(start, null));

        if (start.equals(goal))
            return new LinkedList<>();

        int i = 0;
        Point currentPoint;
        do{
            currentPoint = l.get(i).getEnfant();

            for(Point voisin : getVoisins(currentPoint, l.get(i).getParent())){
                l.add(new Tuple(voisin,start));
            }
            i++;
        }while(currentPoint != goal || (goal == null && grid[currentPoint.x][currentPoint.y] instanceof CaseVide));

        Point enfant = goal;
        for (int j = chemin.size()-1; j >= 0; j--){
            if(l.get(j).getEnfant().equals(enfant)){
                chemin.addFirst(grid[l.get(j).getEnfant().x][l.get(j).getEnfant().y]);
                enfant = l.get(j).getParent();
            }
        }
        return chemin;
    }

    public void Perception(Agent agent) {
        agent.setMeilleurChemin(MeilleurCheminObjectif(agent.getPos(), agent.getObjectif()));
    }

    public List<Case> MeilleurCheminCaseVide(Point start){
        return MeilleurCheminObjectif(start, null);
    }

    public List<Point> getVoisins(Point point, Point parent){
        List<Point> lPoint = new ArrayList();
        if(point.x-1 >= 0 && !grid[point.x-1][point.y].getPos().equals(parent))
            lPoint.add(grid[point.x-1][point.y].getPos());

        if(point.x+1 < grid.length && !grid[point.x+1][point.y].getPos().equals(parent))
            lPoint.add(grid[point.x+1][point.y].getPos());

        if(point.y-1 >= 0 && !grid[point.x][point.y-1].getPos().equals(parent))
            lPoint.add(grid[point.x][point.y-1].getPos());

        if(point.y+1 >= grid.length && !grid[point.x][point.y+1].getPos().equals(parent))
            lPoint.add(grid[point.x][point.y+1].getPos());
        return lPoint;
    }


    public class Tuple<X, Y> {
        public final X enfant;
        public final Y parent;
        public Tuple(X enfant, Y parent) {
            this.enfant = enfant;
            this.parent = parent;
        }

        public X getEnfant(){
            return enfant;
        }

        public Y getParent(){
            return parent;
        }

    }

}


