import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgrapht.*;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
// resolve ambiguity
import org.jgrapht.graph.DefaultEdge;

/**
 * A demo applet that shows how to use JGraph to visualize JGraphT graphs.
 *
 * @author Barak Naveh
 * @since Aug 3, 2003
 */
public class JGraphAdapterDemo {

    private static int screenWidth;
    private int vertexNumber = 20;
    private int vertexWidth = 50;
    private int vertexHeight = 50;
    private HashMap<Edge, Double> edgeUnspoiltProbability;
    private HashMap<String, HashSet<String>> graphStructure;
    private int nodeVisualizationDistance = 80;
    private int testSuccessfulNumber = 0;
    private int testNumber = 0;


    public JGraphAdapterDemo(){
        initgraphStructure();
        initEdgeUnspoiltProbability();

    }

    private void initEdgeUnspoiltProbability() {
        edgeUnspoiltProbability = new HashMap<>();
        for(String v1: graphStructure.keySet()){
            for(String v2: graphStructure.get(v1)){
                if(!edgeUnspoiltProbability.containsKey(new Edge(v1, v2))){
                    edgeUnspoiltProbability.put(new Edge(v1, v2), 0.95);
                }
            }
        }
//        for(Edge e: edgeUnspoiltProbability.keySet()){
//            System.out.println("edge: " + e.getVertex1() + ", " + e.getVertex2());
//        }
//        System.out.println(edgeUnspoiltProbability.size());
    }

    private void initgraphStructure() {
        graphStructure = new HashMap<>();

        for(int i = 1; i <= vertexNumber; i++){
            HashSet<String> h = new HashSet<String>();
            if(i < vertexNumber){
                h.add("v" + (i + 1));
            }
            if(i > 1){
                h.add("v" + (i - 1));
            }
            graphStructure.put("v" + i, h);
        }
//        System.out.println(graphStructure);
    }

    public void testNetwork(int testSize){
        int success = 0;
        int test = 0;

        UndirectedGraph<String, DefaultEdge> g;
        for(int i = 0; i < testSize; i++){
            g = createGraph();
            runSingleNetworkTest(g);
            if(new ConnectivityInspector(g).isGraphConnected()){
                success++;
            }
            test++;
        }

        System.out.println("Success number: " + success);
        System.out.println("Test number: " + test);

    }

    private void runSingleNetworkTest(UndirectedGraph<String, DefaultEdge> g){
        double randomNum;
        for(Edge e: edgeUnspoiltProbability.keySet()){
            randomNum = ThreadLocalRandom.current().nextDouble();
            if(randomNum >= edgeUnspoiltProbability.get(e)){
                g.removeEdge(e.getVertex1(), e.getVertex2());
            }
        }
    }




    public static void main(String[] args){
        setScreenWidth();
        JGraphAdapterDemo applet = new JGraphAdapterDemo();

        applet.testNetwork(100000);



        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(applet.createGraphVisualization(applet.createGraph())));
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }


    private UndirectedGraph<String, DefaultEdge> createGraph(){
        UndirectedGraph<String, DefaultEdge> g =
                new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

        for(String v: graphStructure.keySet()){
            g.addVertex(v);
        }

        for (String v1 : graphStructure.keySet()) {
//            System.out.println("Key: " + v1 + ", Value: " + graphStructure.get(v1));
            for (String v : graphStructure.get(v1)) {
                g.addEdge(v1, v);
            }
        }
//          to remove all edges from vertex
//        for(String v: graphStructure.get("v5")){
//            g.removeEdge("v5", v);
//        }
//            g.removeEdge("v6", "v5");


//        System.out.println(new ConnectivityInspector(g).isGraphConnected());

//        System.out.println(g.edgeSet());

        return g;
    }


    private JGraph createGraphVisualization(UndirectedGraph<String, DefaultEdge> g){

//        UndirectedGraph<String, DefaultEdge> g = createGraph();


        // create a visualization using JGraph, via an adapter
        JGraphModelAdapter<String, DefaultEdge> jgAdapter = new JGraphModelAdapter<>(g);

        JGraph jgraph = new JGraph(jgAdapter);


        for(String v: g.vertexSet()){
            int vNumber = Integer.parseInt(v.substring(1));
            if(vNumber % 2 == 0){
                positionVertexAt(jgAdapter, v, vNumber * nodeVisualizationDistance, 200);
            }
            else {
                positionVertexAt(jgAdapter, v, vNumber * nodeVisualizationDistance, 100);
            }
        }
        return  jgraph;
    }

    private void positionVertexAt(JGraphModelAdapter<String, DefaultEdge> jgAdapter, Object vertex, int x, int y){
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();

        Rectangle2D newBounds = new Rectangle2D.Double(x, y, vertexWidth, vertexHeight);

        GraphConstants.setBounds(attr, newBounds);

        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
    }

    private static void setScreenWidth() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
    }

    /**
     * A listenable directed multigraph that allows loops and parallel edges.
     */
    private static class ListenableDirectedMultigraph<V, E>
            extends DefaultListenableGraph<V, E>
    {
        private static final long serialVersionUID = 1L;

        ListenableDirectedMultigraph(Class<E> edgeClass)
        {
            super(new DirectedMultigraph<>(edgeClass));
        }
    }
}
