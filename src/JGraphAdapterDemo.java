import java.awt.*;
import java.awt.geom.*;
import java.util.HashMap;

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
//    private JGraphModelAdapter<String, DefaultEdge> jgAdapter;
    private int vertexWidth = 50;
    private int vertexHeight = 50;

    /**
     * An alternative starting point for this demo, to also allow running this applet as an
     * application.
     *
     * @param args ignored.
     */
    public static void main(String[] args){
        setScreenWidth();
        JGraphAdapterDemo applet = new JGraphAdapterDemo();


        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(applet.createGraphVisualization()));
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public ListenableGraph<String, DefaultEdge> createGraph(){
        ListenableGraph<String, DefaultEdge> g =
                new ListenableDirectedMultigraph<>(DefaultEdge.class);

        String[] s = new String[vertexNumber ];

        for(int i = 0; i < vertexNumber; i++){
            s[i] = "v" + (i + 1);
            g.addVertex(s[i]);
        }

        for(int i = 0; i < vertexNumber - 1; i++){
            g.addEdge(s[i], s[i + 1]);
        }
        g.removeEdge(s[5], s[6]);

        return g;
    }


    public JGraph createGraphVisualization(){

        ListenableGraph<String, DefaultEdge> g = createGraph();


        // create a visualization using JGraph, via an adapter
        JGraphModelAdapter<String, DefaultEdge> jgAdapter = new JGraphModelAdapter<>(g);

        JGraph jgraph = new JGraph(jgAdapter);

        int foo = (screenWidth - 200) / (vertexNumber * 2);
        int k = 0;
        int i = 0;
        for(String v: g.vertexSet()){

            if(i % 2 == 0){
                positionVertexAt(jgAdapter, v, k, 200);
            }
            else {
                positionVertexAt(jgAdapter, v, k, 100);
            }
            i ++;
            k += foo;
        }
        return  jgraph;
    }

    private void positionVertexAt(JGraphModelAdapter<String, DefaultEdge> jgAdapter, Object vertex, int x, int y)
    {
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
