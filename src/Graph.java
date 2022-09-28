import java.util.*;

class Graph{
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

    /**
     * function to see if a graph is connected
     * @param graph graph to be used to traverse through
     * @praam numVertices # of vertices in graph
     * @return return true if everything is ocnnected, false if not
     */
    public boolean  isConnected(Graph graph, int numVertices){
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

    /**
     * Helper functions for is connected, performs a DFS to see if all the vertices can be visited
     * @param source initial vertex that is marked as visited
     * @param graph the graph to traverse through
     * @param visited boolean array to see if the correct number of nodes have been visited, size
     *                matches the graph.size (# of vertices)
     * @return returns the boolean node to be iterated through
     */
    public boolean[] DFS(int source, Graph graph, boolean[] visited){
        //Mark starting node as visited
        visited[source] = true;
        //Iterate through the vertices in the node
        for(Vertex vertex : graph.getVertices()){
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