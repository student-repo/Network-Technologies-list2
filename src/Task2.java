import javax.swing.*;
import java.io.IOException;

/**
 * Created by ubuntu-master on 01.04.17.
 */
public class Task2 {
    public static void main(String[] args) throws IOException {
        Graph2 g = new Graph2();
        g.initGraphStructure();
        System.out.println(g.networkSingleTest());


        JFrame frame = new JFrame();
//        frame.getContentPane().add(new JScrollPane(g.createGraphVisualization(g.createGraph())));
        frame.getContentPane().add(new JScrollPane(g.createGraphVisualization(g.getGraph())));
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
