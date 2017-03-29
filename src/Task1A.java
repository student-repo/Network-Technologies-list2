import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;

/**
 * Created by ubuntu-master on 29.03.17.
 */
public class Task1A {

    public static void main(String[] args){
        JGraphAdapterDemo applet = new JGraphAdapterDemo();


        System.out.println(applet.testNetwork(100000).getPercentResult());

        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(applet.createGraphVisualization(applet.createGraph())));
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
