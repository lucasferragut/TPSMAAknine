import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class main {
    public static void main(String[] args) throws Exception {

        List<Agent> lAgents = new ArrayList<>();
        Environnement env = new Environnement(5);
        for(int i =0;i < 24; i++){
            lAgents.add(new Agent(i,env));
        }
        env.addAgents(lAgents);

        System.out.println(env);

        for (Agent agent: lAgents) {
            new Thread(agent).start();
        }

        //Semaphore semaphore = env.getSemaphore();
        //while (!env.isFinish()){
            //Thread.sleep(5000);
            //semaphore.acquire();
            //System.out.println(env);
            //semaphore.release();
        //}
        //System.out.println(env);
    }
}
