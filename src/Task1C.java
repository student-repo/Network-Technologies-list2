import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;

/**
 * Created by ubuntu-master on 29.03.17.
 */
public class Task1C {

    public static void main(String[] args){
        Graph graph = new Graph();
        graph.initTask1GraphStructure();

        UndirectedGraph<String, DefaultEdge> g = graph.createGraph();
        graph.addEdge("v1", "v20", 0.95);
        graph.addEdge("v1", "v10", 0.8);
        graph.addEdge("v5", "v15", 0.7);

        System.out.println(graph.testNetwork(100000));
        System.out.println();

        graph = new Graph();
        graph.initTask1GraphStructure();
        graph.addEdge("v1", "v20", 0.95);
        graph.addEdge("v1", "v10", 0.8);
        graph.addEdge("v5", "v15", 0.7);

        System.out.println(graph.testNetwork(100000));
        System.out.println();

        graph = new Graph();
        graph.initTask1GraphStructure();
        graph.addEdge("v1", "v20", 0.95);
        graph.addEdge("v1", "v10", 0.8);
        graph.addEdge("v5", "v15", 0.7);

        System.out.println(graph.testNetwork(100000));
        System.out.println();

        graph = new Graph();
        graph.initTask1GraphStructure();
        graph.addEdge("v1", "v20", 0.95);
        graph.addEdge("v1", "v10", 0.8);
        graph.addEdge("v5", "v15", 0.7);

        System.out.println(graph.testNetwork(100000));
        System.out.println();

        graph = new Graph();
        graph.initTask1GraphStructure();
        graph.addEdge("v1", "v20", 0.95);
        graph.addEdge("v1", "v10", 0.8);
        graph.addEdge("v5", "v15", 0.7);

        System.out.println(graph.testNetwork(100000));
        System.out.println();

        graph = new Graph();
        graph.initTask1GraphStructure();
        graph.addEdge("v1", "v20", 0.95);
        graph.addEdge("v1", "v10", 0.8);
        graph.addEdge("v5", "v15", 0.7);

        System.out.println(graph.testNetwork(100000));
        System.out.println();

        graph = new Graph();
        graph.initTask1GraphStructure();
        graph.addEdge("v1", "v20", 0.95);
        graph.addEdge("v1", "v10", 0.8);
        graph.addEdge("v5", "v15", 0.7);

        System.out.println(graph.testNetwork(100000));
        System.out.println();

        graph = new Graph();
        graph.initTask1GraphStructure();
        graph.addEdge("v1", "v20", 0.95);
        graph.addEdge("v1", "v10", 0.8);
        graph.addEdge("v5", "v15", 0.7);

        System.out.println(graph.testNetwork(100000));
        System.out.println();


    }
}

////        g.addEdge("v1", "v20");
//        g = graph.createGraph();
//
//
//
//
//                JFrame frame = new JFrame();
//                frame.getContentPane().add(new JScrollPane(graph.createGraphVisualization(g)));
//                frame.setTitle("JGraphT Adapter to JGraph Demo");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.pack();
//                frame.setVisible(true);

