import com.sun.rowset.internal.Row;

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
        while(a < lAgent.size()){
            int i = r.nextInt(this.grid.length);
            int j = r.nextInt(this.grid.length);
            if(grid[i][j] == null){
                grid[i][j] = lAgent.get(a);
                lAgent.get(a).setPos(i,j);
                a++;
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

    public void Perception(Agent agent){
        Point pos = agent.getPos();
        Map<PointCardinal, Case> map = new HashMap<>();
        if(pos.x-1 < 0)
            map.put(PointCardinal.NORD, new Wall());
        else
            map.put(PointCardinal.NORD, grid[pos.x-1][pos.y]);

        if(pos.x+1 >= grid.length)
            map.put(PointCardinal.SUD, new Wall());
        else
            map.put(PointCardinal.SUD, grid[pos.x+1][pos.y]);

        if(pos.y-1 < 0)
            map.put(PointCardinal.OUEST, new Wall());
        else
            map.put(PointCardinal.OUEST, grid[pos.x][pos.y-1]);

        if(pos.y+1 >= grid.length)
            map.put(PointCardinal.EST, new Wall());
        else
            map.put(PointCardinal.EST, grid[pos.x][pos.y+1]);

        agent.setVoisinnage(map);
    }

    public void deplacement(PointCardinal Direction, Agent agent) {
        Point pos = agent.getPos();
        grid[pos.x][pos.y] = null;
        switch (Direction){
            case NORD:
                agent.setPos(pos.x-1,pos.y);
                grid[pos.x-1][pos.y] = agent;
                break;
            case SUD:
                agent.setPos(pos.x+1,pos.y);
                grid[pos.x+1][pos.y] = agent;
                break;
            case EST:
                agent.setPos(pos.x,pos.y+1);
                grid[pos.x][pos.y+1] = agent;
                break;
            case OUEST:
                agent.setPos(pos.x,pos.y-1);
                grid[pos.x][pos.y-1] = agent;
                break;
        }
    }

    @Override
    public String toString() {
        String returnStr = "";
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid.length; j++) {
                if (grid[i][j] == null)
                    returnStr += " . ";
                else if(grid[i][j] != null){
                    returnStr += "[" + grid[i][j].getNumber() + "]";
                }
            }
            returnStr += "\n";
        }
        return returnStr;
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
}
