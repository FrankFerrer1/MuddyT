/**
 * Class that holds the graph data structure and related operations
 */
import java.util.*;
public class Graph{
    private static HashMap<String,Node> graph = new HashMap<String,Node>();
    public void add(String source, String destination, int weight){

        //Get nodes corresponding to source and destination vertices.
        Node s = getNode(source);
        Node d = getNode(destination);

        //add nodes to adjacent list
        s.addAdjacent(destination,weight);
        d.addAdjacent(source,weight);
    }

    private static Node getNode(String id){
        if(graph.containsKey(id))
            return graph.get(id);
        else{
            Node node = new Node(id);
            graph.put(id, node);
            return node;
        }
    }

    public ArrayList<Edge> findAdjacent(String index){
        Node node = getNode(index);
        return node.getAdjacent();
    }
    public boolean isConnected(String source, String destination){
        Node s = getNode(source);
        Node d = getNode(destination);

        if(s.getAdjacent().contains(d) || d.getAdjacent().contains(s))
            return true;
        return false;
    }
}