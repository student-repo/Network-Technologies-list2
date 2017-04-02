import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.HashMap;

/**
 * Created by ubuntu-master on 29.03.17.
 */


public class Edge implements Comparable{
    private String vertex1;
    private String vertex2;


    public Edge(String vertex1, String vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }


    public static void main(String[] s){
        Edge e = new Edge("v1", "v2");
        Edge e2 = new Edge("v1", "v1");
        HashMap<Edge, Double> edgeUnspoiltProbability = new HashMap<>();
        edgeUnspoiltProbability.put(e, 0.07);
        edgeUnspoiltProbability.put(e2, 0.07);
//        if(e.compareTo(e2) == 1){
//            System.out.println("the same");
//        }
        System.out.println(edgeUnspoiltProbability);
        System.out.println(e.equals(e2));
        System.out.println(e.hashCode());
        System.out.println(e2.hashCode());
    }

    @Override
    public int hashCode() {
        try{
            int v1 = Integer.parseInt(getVertex1().substring(1));
            int v2 = Integer.parseInt(getVertex2().substring(1));
            if(v1 > v2){
                return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                        append(v1).
                        append(v2).
                        toHashCode();
            }
            else{
                return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                        append(v2).
                        append(v1).
                        toHashCode();
            }


        }
        catch (Exception e){

        }
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers

                        toHashCode();
    }


    @Override
    public int compareTo(Object o) {
        try{
            Edge e1 = (Edge)o;
            if((e1.getVertex1().equals(getVertex1()) && e1.getVertex2().equals(getVertex2())) ||
                    (e1.getVertex2().equals(getVertex1()) && e1.getVertex1().equals(getVertex2()))){
                return 1;
            }
        }
        catch (Exception e){
            ;
        }

        return 0;
    }


    @Override
    public boolean equals(Object o){
        try{
            Edge e1 = (Edge)o;
            return (e1.getVertex1().equals(getVertex1()) && e1.getVertex2().equals(getVertex2())) ||
                    (e1.getVertex2().equals(getVertex1()) && e1.getVertex1().equals(getVertex2()));
        }
        catch (Exception e){
            return false;
        }

    }

    public String getVertex1() {
        return vertex1;
    }

    public String getVertex2() {
        return vertex2;
    }

}
