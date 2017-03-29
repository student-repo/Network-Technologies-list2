import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;

/**
 * Created by ubuntu-master on 29.03.17.
 */
public class Task1B {

    public static void main(String[] args){
        JGraphAdapterDemo applet = new JGraphAdapterDemo();

        UndirectedGraph<String, DefaultEdge> g = applet.createGraph();
        applet.addEdge("v1", "v20", 0.95);

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
