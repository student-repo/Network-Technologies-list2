
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.codehaus.jackson.map.ObjectMapper;
import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgrapht.*;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultEdge;
import org.json.JSONArray;
import org.json.JSONObject;

public class Graph2 {

    private int vertexVisualizationWidth = 50;
    private int vertexVisualizationHeight = 50;
    private int nodeVisualizationDistance = 80;

    private ArrayList<String> vertexes;
    private HashMap<Edge, EdgeProperty> edges;
    private String dataFilePath;
    private double T_max;
    private HashMap<String, HashMap<String, Integer>> packagesIntensityMap;
    private int packagesIntensitySum;
    UndirectedGraph<String, DefaultEdge> g;


    public Graph2(){
        vertexes = new ArrayList<>();
        edges = new HashMap<>();
        dataFilePath = new File("").getAbsolutePath() + "/data.json";
        T_max = (double)getJSONObject().get("T_max");
        packagesIntensityMap = new HashMap<>();
    }

    public void initGraphStructure() throws IOException {
        initVertexes();
        initEdges();
        initPackagesIntensityMap();
        packagesIntensitySum = getSumPackagesIntensityMap();
    }

    private int getSumPackagesIntensityMap(){
        int i = 0;
        for(String v: packagesIntensityMap.keySet()){
            for(String vv: packagesIntensityMap.get(v).keySet()){
                i += packagesIntensityMap.get(v).get(vv);
            }
        }
        return i;
    }

    private void initEdges(){
        JSONArray arr = getJSONArray("edge-capacity");
        double unspoiltProbability = (double)getJSONObject().get("edge-unspoilt-probability");

        for (int i = 0; i < arr.length(); i++) {
            edges.put(new Edge(arr.getJSONObject(i).getString("vertex1"), arr.getJSONObject(i).getString("vertex2")),
                    new EdgeProperty(arr.getJSONObject(i).getInt("capacity"),unspoiltProbability));
        }
    }

    private JSONArray getJSONArray(String field){
        JSONObject obj = null;
        try {
            obj = new JSONObject(readFile(dataFilePath, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj.getJSONArray(field);
    }

    private void initPackagesIntensityMap() throws IOException {
        HashMap<String, HashMap<String, Integer>> r = new HashMap<>();
        JSONArray arr = getJSONArray("packages-intensity-matrix");
        for (int i = 0; i < arr.length(); i++) {
            packagesIntensityMap.put(vertexes.get(i),
                    new ObjectMapper().readValue(arr.getJSONObject(i).get(vertexes.get(i)).toString(), HashMap.class));
        }
    }

    private JSONObject getJSONObject(){
        JSONObject obj = null;
        try {
            obj =  new JSONObject(readFile(dataFilePath, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void initVertexes(){
        JSONArray arr = getJSONArray("packages-intensity-matrix");
        for (int i = 0; i < arr.length(); i++) {
            vertexes.add(arr.getJSONObject(i).keySet().toArray()[0].toString());
        }
    }

    private double sum_e(){
        double s = 0.0;
        for(Edge e: edges.keySet()){
            s += (double)edges.get(e).getCurrentPackages()/((double)edges.get(e).getCapacity()
                    - (double)edges.get(e).getCurrentPackages());
        }
        return (double)s / (double)packagesIntensitySum;
    }

    public UndirectedGraph<String, DefaultEdge> getGraph(){
        return g;
    }

    public void networkTest(int n){
        int testSuccessful = 0;
        int packagesIntensityFail = 0;
        int graphInconnectionFail = 0;
        int timeFail = 0;
        for(int i = 0; i < n; i++){
            NetworkTestResult t= networkSingleTest();
            if(t == NetworkTestResult.PACKAGES_INTENSITY_FAIL){
                packagesIntensityFail++;
            }
            else if(t == NetworkTestResult.GRAPH_INCONNECTION_FAIL){
                graphInconnectionFail++;
            }
            else if(t == NetworkTestResult.TIME_FAIL){
                timeFail++;
            }
            else if(t == NetworkTestResult.SUCCESSFUL){
                testSuccessful++;
            }
        }
        System.out.println("test number: " + n + " successful tests: " +
                testSuccessful + " reliability: " +
                (double)testSuccessful/(double)n * 100  + " %  packages intensity fail: "
                + packagesIntensityFail + " graph inconnection fail: " +
                graphInconnectionFail + " time fail: "
                + timeFail);
    }

    private NetworkTestResult networkSingleTest(){
        initEdges();
        g = createGraph();
        for(String v: packagesIntensityMap.keySet()){
            for(String vv: packagesIntensityMap.get(v).keySet()){
                String currentVertex = v;
                String nextVertex = "";
                    while(!nextVertex.equals(vv)){
                        nextVertex = getNextShortestEdge(currentVertex, g, vv).getVertex2();
                        currentVertex = getNextShortestEdge(currentVertex, g, vv).getVertex1();

                        if(!edges.get(new Edge(currentVertex, nextVertex)).addCurrentPackages(packagesIntensityMap.get(v).get(vv))){
                            return NetworkTestResult.PACKAGES_INTENSITY_FAIL;
                        }
                        if(sum_e() > T_max){
                            return NetworkTestResult.TIME_FAIL;
                        }
                        if(edges.get(new Edge(currentVertex, nextVertex)).getUnspoiltProbability() < ThreadLocalRandom.current().nextDouble()){
                            g.removeEdge(currentVertex, nextVertex);
                            edges.remove(new Edge(currentVertex, nextVertex));
                            if(!new ConnectivityInspector(g).isGraphConnected()){
                                return NetworkTestResult.GRAPH_INCONNECTION_FAIL;
                            }
                        }
                        currentVertex = nextVertex;
                    }
            }
        }
//        System.out.println("T: " + sum_e() + " ");
        return NetworkTestResult.SUCCESSFUL;
    }

    public Edge getNextShortestEdge(String v, UndirectedGraph<String, DefaultEdge> g, String v3){
        GraphPath a = (GraphPath) new KShortestPaths(g, v, 1).getPaths(v3).get(0);
        String currentVertex = getSecondVertexFromEdgeSyntax(a.getEdgeList().get(0).toString());
        String nextVertex = getFirtsVertexFromEdgeSyntax(a.getEdgeList().get(0).toString());
        if(currentVertex.equals(v3)){
            currentVertex = nextVertex;
            nextVertex = v3;
        }
        if(!nextVertex.equals(v3)){
            nextVertex = ((GraphPath) new KShortestPaths(g, nextVertex, 1).getPaths(v3).get(0)).getEdgeList().size() <
                    ((GraphPath) new KShortestPaths(g, currentVertex, 1).getPaths(v3).get(0)).getEdgeList().size()
                    ? nextVertex : currentVertex;
        }
       if(nextVertex.equals(currentVertex)){
            currentVertex = getFirtsVertexFromEdgeSyntax(a.getEdgeList().get(0).toString());
        }
        return new Edge(currentVertex, nextVertex);
    }


    public UndirectedGraph<String, DefaultEdge> createGraph(){
        UndirectedGraph<String, DefaultEdge> g =
                new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

        vertexes.forEach(g::addVertex);

        for(Edge e: edges.keySet()){
            g.addEdge(e.getVertex1(), e.getVertex2());
        }
        return g;
    }

    private String getSecondVertexFromEdgeSyntax(String s){
        return removeLastChar(s.substring(s.lastIndexOf(":") + 1).substring(1));
    }

    private String getFirtsVertexFromEdgeSyntax(String s){
        return removeLastChar(s.split("\\:")[0].toString().substring(1));
    }

    private String removeLastChar(String s){
        return s.substring(0, s.length() -1);
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
                positionVertexAt(jgAdapter, v, vNumber * nodeVisualizationDistance, 450);
            }
        }
        return  jgraph;
    }

    private void positionVertexAt(JGraphModelAdapter<String, DefaultEdge> jgAdapter, Object vertex, int x, int y){
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();

        Rectangle2D newBounds = new Rectangle2D.Double(x, y, vertexVisualizationWidth, vertexVisualizationHeight);

        GraphConstants.setBounds(attr, newBounds);

        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
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

    private String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
