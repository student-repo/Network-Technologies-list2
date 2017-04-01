
import org.jgrapht.alg.KShortestPaths;

import javax.swing.*;

public class Task1A {

    public static void main(String[] args){
        Graph graph = new Graph();
        System.out.println(graph.testNetwork(100000));
        System.out.println();
        graph = new Graph();
        System.out.println(graph.testNetwork(100000));
        System.out.println();

        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(graph.createGraphVisualization(graph.createGraph())));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);




    }
}
