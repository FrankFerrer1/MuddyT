import java.util.*;
public class Node {
    private String address;
    private ArrayList<Edge> adjacent;
    Node(String address){
        this.address = address;
        adjacent = new ArrayList<Edge>();
    }

    public String getaddress() {
        return address;
    }

    public ArrayList<Edge> getAdjacent() {
        return adjacent;
    }

    public void addAdjacent(String endVertex, int weight){
        adjacent.add(new Edge(endVertex,weight));
    }
}
