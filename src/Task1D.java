import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ubuntu-master on 29.03.17.
 */
public class Task1D {

    public static void main(String[] args){
        Graph graph = new Graph();
        graph.initTask1GraphStructure();
        int i, randomNum1, randomNum2;

        graph.addEdge("v1", "v20", 0.95);
        graph.addEdge("v1", "v10", 0.8);
        graph.addEdge("v5", "v15", 0.7);

        i = 0;
        randomNum1 = ThreadLocalRandom.current().nextInt(1, 20 + 1);
        randomNum2 = ThreadLocalRandom.current().nextInt(1, 20 + 1);
        while(i < 4){
            if(graph.addEdge("v" + randomNum1, "v" + randomNum2, 0.4)){
                i++;
                System.out.println("add edge: " + "e(" + "v" + randomNum1 + ", " + "v" + randomNum2 + ")");
            }
            randomNum1 = ThreadLocalRandom.current().nextInt(1, graph.getVertexNumber() + 1);
            randomNum2 = ThreadLocalRandom.current().nextInt(1, graph.getVertexNumber() + 1);
        }
        System.out.println(graph.testNetwork(100000));

        UndirectedGraph<String, DefaultEdge> g = graph.createGraph();

        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(graph.createGraphVisualization(g)));
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}




//    UndirectedGraph<String, DefaultEdge> g = graph.createGraph();
//
//System.out.println(graph.testNetwork(100000).getPercentResult());
////        g.addEdge("v1", "v20");
//        g = graph.createGraph();
//
//
//
//
//        JFrame frame = new JFrame();
//        frame.getContentPane().add(new JScrollPane(graph.createGraphVisualization(g)));
//        frame.setTitle("JGraphT Adapter to JGraph Demo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
