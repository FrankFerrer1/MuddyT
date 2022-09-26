import java.util.*;

class Vertex {

    private int id;
    private String label;
    private Set<Edge> edges; //collection of edges to neighbors

    public Vertex(int id, String label) {
        this.id = id;
        this.label = label;
        edges = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    String getLabel() {
        return label;
    }

    boolean addEdge(Edge edge){
        return edges.add(edge);
    }

    List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    public String toString(){
        return this.getLabel();
    }
}