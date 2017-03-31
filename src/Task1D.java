import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ubuntu-master on 29.03.17.
 */
public class Task1D {

    public static void main(String[] args){
        JGraphAdapterDemo applet = new JGraphAdapterDemo();

        UndirectedGraph<String, DefaultEdge> g = applet.createGraph();
        System.out.println(applet.addEdge("v1", "v20", 0.95));
        System.out.println(applet.addEdge("v1", "v10", 0.8));
        System.out.println(applet.addEdge("v5", "v15", 0.7));
        int i = 0;
        int randomNum1 = ThreadLocalRandom.current().nextInt(1, 20 + 1);
        int randomNum2 = ThreadLocalRandom.current().nextInt(1, 20 + 1);
        while(i < 4){
            if(applet.addEdge("v" + randomNum1, "v" + randomNum2, 0.4)){
                i++;
            }
            randomNum1 = ThreadLocalRandom.current().nextInt(1, 20 + 1);
            randomNum2 = ThreadLocalRandom.current().nextInt(1, 20 + 1);
        }



        System.out.println(applet.testNetwork(100000).getPercentResult());
//        g.addEdge("v1", "v20");
        g = applet.createGraph();




        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(applet.createGraphVisualization(g)));
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}
