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

public class JGraphAdapterDemo {

    private static int screenWidth;
    private int vertexNumber = 20;
    private int vertexWidth = 50;
    private int vertexHeight = 50;
    private HashMap<Edge, Double> edgeUnspoiltProbability;
    private HashMap<String, HashSet<String>> graphStructure;
    private int nodeVisualizationDistance = 80;


    public JGraphAdapterDemo(){
        initGraphStructure();
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
    }
    //        for(Edge e: edgeUnspoiltProbability.keySet()){
//            System.out.println("edge: " + e.getVertex1() + ", " + e.getVertex2());
//        }
//        System.out.println(edgeUnspoiltProbability.size());


    public boolean addEdge(String v1, String v2, double unspoiltProbability){
        ///////////////////////////////////////////////////
//        System.out.println("graphStructure before: ");
//        for(String s: graphStructure.keySet()){
//            System.out.println(s + ": " +graphStructure.get(s));
//        }
        ///////////////////////////////////////////////////

        if(graphStructure.containsKey(v1) && graphStructure.containsKey(v2)){
            if(edgeExist(new Edge(v1, v2)) || v1.equals(v2)){
                return false;
            }
            graphStructure.get(v1).add(v2);
            graphStructure.get(v2).add(v1);
            initEdgeUnspoiltProbability();
            edgeUnspoiltProbability.put(new Edge(v1, v2), unspoiltProbability);
            System.out.println(edgeUnspoiltProbability.size());
            return true;
        }

        ///////////////////////////////////////////////////
//        System.out.println("graphStructure after: ");
//        for(String s: graphStructure.keySet()){
//            System.out.println(s + ": " +graphStructure.get(s));
//        }
//
//        for(Edge e: edgeUnspoiltProbability.keySet()){
//            System.out.println("edge: " + e.getVertex1() + ", " + e.getVertex2() + " " + edgeUnspoiltProbability.get(e));
//        }
//        System.out.println(edgeUnspoiltProbability.size());
        ///////////////////////////////////////////////////
        return false;
    }
    private void initGraphStructure() {
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
    }

    public TestNetworkResult testNetwork(int testSize){
        TestNetworkResult r = new TestNetworkResult();
        UndirectedGraph<String, DefaultEdge> g;
        for(int i = 0; i < testSize; i++){
            g = createGraph();
            runSingleNetworkTest(g);
            if(new ConnectivityInspector(g).isGraphConnected()){
                r.incrementSuccessNumber();
            }
            r.incrementTestNumber();
        }
        return r;
    }

    public boolean edgeExist(Edge e){
        return edgeUnspoiltProbability.containsKey(e);
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

    public int getVertexNumber(){
        return vertexNumber;
    }


    public static void main(String[] args){
        setScreenWidth();
        JGraphAdapterDemo applet = new JGraphAdapterDemo();

//        task 1 a)
//        System.out.println(applet.testNetwork(100000).getPercentResult());

        UndirectedGraph<String, DefaultEdge> g = applet.createGraph();
        g.addEdge("v1", "v20");




        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(applet.createGraphVisualization(g)));
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }


    public UndirectedGraph<String, DefaultEdge> createGraph(){
        UndirectedGraph<String, DefaultEdge> g =
                new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

        for(String v: graphStructure.keySet()){
            g.addVertex(v);
        }

        for (String v1 : graphStructure.keySet()) {
            for (String v : graphStructure.get(v1)) {
                g.addEdge(v1, v);
            }
        }
        return g;
    }


    public JGraph createGraphVisualization(UndirectedGraph<String, DefaultEdge> g){

        JGraphModelAdapter<String, DefaultEdge> jgAdapter = new JGraphModelAdapter<>(g);

        JGraph jgraph = new JGraph(jgAdapter);


        for(String v: g.vertexSet()){
            int vNumber = Integer.parseInt(v.substring(1));
            if(vNumber % 2 == 0){
                positionVertexAt(jgAdapter, v, vNumber * nodeVisualizationDistance, 300);
            }
            else {
                positionVertexAt(jgAdapter, v, vNumber * nodeVisualizationDistance, 400);
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
