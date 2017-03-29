/**
 * Created by ubuntu-master on 28.03.17.
 */

import java.net.*;

import org.jgraph.JGraph;
import org.jgrapht.*;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.*;

import javax.swing.*;

/***
 ** A simple introduction to using JGraphT.
 **
 ** @author Barak Naveh
 ** @since Jul 27, 2003
 **/
public final class HelloJGraphT
{

    //~ Constructors ———————————————————

    private HelloJGraphT()
    {
    } // ensure non-instantiability.

    //~ Methods ———————————————————

    /***
     ** The starting point for the demo.
     **
     ** @param args ignored.
     */
    public static void main(String [] args)
    {
        UndirectedGraph<String, DefaultEdge> stringGraph = createStringGraph();

        // note undirected edges are printed as: {<v1>,<v2>}
        System.out.println(stringGraph.toString());

        JGraph jgraph = new JGraph( new JGraphModelAdapter( stringGraph ) );

        JFrame frame = new JFrame();
        frame.getContentPane().add(jgraph);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);


    }

    /***
     ** Creates a toy directed graph based on URL objects that represents link
     ** structure.
     **
     ** @return a graph based on URL objects.
     **/

    /***
     ** Craete a toy graph based on String objects.
     **
     ** @return a graph based on String objects.
     */
    private static UndirectedGraph<String, DefaultEdge> createStringGraph()
    {
        UndirectedGraph<String, DefaultEdge> g =
                new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        // add edges to create a circuit
        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v4);
        g.addEdge(v4, v1);



        return g;
    }
}
