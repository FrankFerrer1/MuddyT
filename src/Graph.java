import java.util.*;

class Graph{
    private int numVertices;
    private Set<Vertex> vertices; //collection of all verices

    public Graph() {
        vertices = new HashSet<>();
    }

    Set<Vertex> getVertices() {
        return vertices;
    }

    boolean addVertex(Vertex vertex){
        return vertices.add(vertex);
    }

    public boolean  isConnected(Graph graph){
        //number of Vertices that are in the graph
        numVertices = graph.size();
        //create new boolean array of the vertices to be visited
        boolean[] visited = new boolean[numVertices];

        visited = DFS(0,graph,visited);
        for(int i = 0 ; i < visited.length; i++){
            if(visited[i] == false){
                return false;
            }
        }
        return true;
    }

    public int size(){
        return vertices.size();
    }
    public boolean[] DFS(int source, Graph graph, boolean[] visited){
        //Mark starting node as visited
        visited[source] = true;
        //Iterate through the vertices in the node
        for(Vertex vertex : graph.getVertices()){
            int tmp = vertex.getId();
            System.out.println(tmp);
            //Iterate through all of the vertices connected to this vertex
            for(Edge edge : vertex.getEdges()){
                //retrieve the Node that is at connected
                Vertex temp = edge.getTo();
                //retrieve ID of node
                int index = temp.getId();
                //If the node has not been visited, mark as visited
                if(visited[index] == false){
                    visited[index] = true;
                }
            }
        }
        return visited;
    }
}