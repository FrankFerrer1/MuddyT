import java.io.*;
import java.util.*;


public class processtown
{
    static ArrayList<String> writeList = new ArrayList<String>();
    static ArrayList<String> writePlan = new ArrayList<String>();
    public static void main(String[] args){
        boolean continueMenu = true;
        Scanner choiceIn = new Scanner(System.in);
        String choice;
        File file;
        String fileIn;
        String fileOut;
        String townName;
        int vertexInc = 0;
        Graph graph = null;
        Graph planGraph;

        System.out.println("Please enter a choice");
        System.out.print("Syntax: h [-option [parameter]]\n");
        System.out.print("  options:\n");
        System.out.print("       s   show current town\n");
        System.out.print("       r   read town data from file\n");
        System.out.print("       w   write current town to file\n");
        System.out.print("       g   generate graph\n");
        System.out.print("       gp   generate paving plan\n");
        System.out.print("       l   load paving plan\n");
        System.out.print("       h   help (this display)\n");
        System.out.print("       x   to exit\n");

        while (continueMenu) {
            System.out.print("Please enter a choice: ");
            choice = choiceIn.next();
            switch (choice) {
                case "r":
                    System.out.println("Please enter the file you wish to load");
                    fileIn = choiceIn.next();
                    file = new File(fileIn);
                    System.out.println("Opening " + fileIn + "...");
                    writeList = new ArrayList<String>();
                    try {
                        //generate fresh graph;
                        graph = new Graph();
                        int lineCounter = 0;
                        Scanner fileReader = new Scanner(file);
                        vertexInc = 0;
                        while (fileReader.hasNextLine()) {
                            String data = fileReader.nextLine();
                            if(lineCounter == 0){
                                townName = data;
                                writeList.add(townName);
                                lineCounter++;
                            }else {
                                writeList.add(data);
                                String[] line = data.split(",");
                                int lineWeight = Integer.parseInt(line[0]);
                                String addressOne = line[1];
                                String addressTwo = line[2];
                                System.out.println(line[0] +"," + line[1] +"," + line[2]);

                                String vertex1Find = addressOne;
                                String vertex2Find = addressTwo;
                                Vertex v1 = null;
                                Vertex v2 = null;

                                for(Vertex vertex : graph.getVertices()){
                                    if(vertex.getLabel().equals(vertex1Find)){
                                        v1 = vertex;
                                    }
                                    if(vertex.getLabel().equals(vertex2Find)){
                                        v2 = vertex;
                                    }
                                }
                                if(v1 == null){
                                    v1 = new Vertex(vertexInc, vertex1Find);
                                    graph.addVertex(v1);
                                    vertexInc+=1;
                                }
                                if(v2 == null){
                                    v2 = new Vertex(vertexInc, vertex2Find);
                                    graph.addVertex(v2);
                                    vertexInc+=1;
                                }

                                v1.addEdge(new Edge(v2, lineWeight)); //connect v1 v2
                                v2.addEdge(new Edge(v1, lineWeight));
                            }
                        }
                        fileReader.close();
                    }catch(FileNotFoundException e){
                        System.out.println("File not Found.");
                    }
                    break;
                case "s":
                    if(writeList.isEmpty()){
                        System.out.println("Error: No town data loaded");
                    }else{
                        for(String i : writeList){
                            System.out.println(i);
                        }
                    }
                    break;
                case "g":
                    System.out.println("Generating graph");
                    graph = generateGraph();
                    break;
                case "l":
                    System.out.println("Enter filename of plan you wish to load");
                    System.out.println("NOTE: PLease have graph already loaded in order to verify the plan");
                    fileIn = choiceIn.next();
                    planGraph = readPlan(graph,fileIn);
                    break;
                case "w":
                    if(writeList.isEmpty()){
                        System.out.println("Error: No town data loaded");
                    }else{
                        System.out.println("Enter name of file you wish to save");
                        System.out.println("Note, the program will automatically add a .dat extension");
                        fileOut = choiceIn.next();
                        write(fileOut,writeList);
                    }
                    break;
                case "wp":
                    if(writePlan.isEmpty()){
                        System.out.println("Error: No plan data loaded");
                    }else{
                        System.out.println("Enter name of file you wish to save");
                        System.out.println("Note, the program will automatically add a .dat extension");
                        fileOut = choiceIn.next();
                        write(fileOut,writePlan);
                    }
                    break;
                case "h":
                    System.out.print("Syntax: h [-option [parameter]]\n");
                    System.out.print("  options:\n");
                    System.out.print("       s   show current town\n");
                    System.out.print("       r   read town data from file\n");
                    System.out.print("       w   write current town to file\n");
                    System.out.print("       g   generate graph\n");
                    System.out.print("       l   load paving plan\n");
                    System.out.print("       wp   write current plan to file\n");
                    System.out.print("       h   help (this display)\n");
                    System.out.print("       x   to exit\n");
                    break;
                case "x":
                    System.out.println("Goodbye");
                    continueMenu = false;
                    break;
                default:
                    System.out.println("Argument not understood, enter h for options");
                    break;
            }
        }

    }

    /**
     * Method to write to file
     * @param fileName filename to be written to, will automatically add a .dat for the extension
     * @param writeList ArrayList to be used to write to file
     * @return returns an empty list for re-use purposes
     */
    public static ArrayList<String> write(String fileName, ArrayList<String> writeList){
        if(writeList.isEmpty()){
            System.out.println("Error, nothing to write");
        }else{
            try {
                PrintWriter writer = new PrintWriter(fileName +".dat");
                System.out.println("Writing...");
                for (String i : writeList) {
                    System.out.println(i);
                    writer.println(i);
                }
                writer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        //clear writeList for new write
        writeList.clear();
        return writeList;
    }

    /**
     * Function that generates a graph from user input
     * Will verify that is all verteces are connected before returning
     * @return returns a constructed graph
     */
    public static Graph generateGraph(){
        writeList = new ArrayList<String>();
        Graph graph = new Graph();
        Random rand = new Random();
        writeList.add("Generic Town");
        //set to contain connected vertexes
        Set<Vertex> isConnected = new HashSet<>();
        Vertex gVertex;
        int numBuildings;
        int[] buildings;
        Scanner inPut = new Scanner(System.in);
        System.out.println("How many buildings do you want? ");

        numBuildings = inPut.nextInt();
        buildings = lcm(numBuildings);

        //Insert created data in to graph
        for(int i=0; i < buildings.length;i++){
            gVertex = new Vertex(i,"\"Building " + buildings[i] + "\"");
            graph.addVertex(gVertex);
        }


        while(!graph.isConnected(graph,graph.size())) {
            for (Vertex vertex : graph.getVertices()) {
                Vertex tmp = vertex;
                Vertex tmp2;
                int tmpNum = tmp.getId();
                int tmpNumTwo;
                String buildingOne = tmp.getLabel();
                String buildingTwo;
                if(!isConnected.contains(tmpNum)){
                    isConnected.add(tmp);
                }
                for (Vertex vertexTwo : graph.getVertices()) {
                    tmp2 = vertexTwo;
                    tmpNumTwo = tmp2.getId();
                    buildingTwo = tmp2.getLabel();

                    int test = rand.nextInt(numBuildings - 0) + 0;

                    while (test > numBuildings && test != tmpNum) {
                        test = rand.nextInt(numBuildings - 0) + 0;
                    }
                    if (tmpNumTwo != tmpNum && tmpNumTwo == test) {
                        if(!isConnected.contains(tmpNumTwo)){
                            writeList.add(buildingOne + "," + buildingTwo);
                            System.out.println(buildingOne + "," + buildingTwo);
                            isConnected.add(tmp2);
                            tmp.addEdge(new Edge(tmp2, 1)); //connect v1 v2
                            tmp2.addEdge(new Edge(tmp, 1));
                        }
                    }
                }
            }
        }

        return graph;
    }


    /**
     * method to generate random numer to be used to in generation of town buildings
     * @param numAddress number of addresses to be inputted
     * @return returns a randomdly generated list of buildings for the generator
     *
     */
    public static int[] lcm(int numAddress){
        int multiplier = 75;
        int mod = (1<<8)+1;
        int inc = 74;
        int returnNum = 0;
        int[] buildingARr = new int[numAddress];

        for(int i = 0; i < buildingARr.length; i++) {
            returnNum = (returnNum * multiplier + inc) % mod;
            buildingARr[i] = returnNum;
        }
        return buildingARr;
    }


    public static Graph readGraph(Graph graph, String fileName){
        Graph returnGraph = new Graph();

    }
    /**
     * method to read a planned graph from a file and then add edge values to the plan
     * @param graph graph that the plan will be used for
     * @param fileName filename to be read
     * @return Returns a completed plan graph with edge values retrieved from param graph
     */
    public static Graph readPlan(Graph graph, String fileName){
        //Basic graph constructed from the paving data, will have edge values set to 1 because edge values are not given
        Graph planGraph = new Graph();
        //Return graph with edge values
        Graph returnGraph = new Graph();
        writePlan = new ArrayList<String>();
        String planName;
        File file = new File(fileName);
        System.out.println("Opening " + file + "...");
        int vertexInc = 0;
        int totalCost = 0;

        ArrayList<Vertex> vertexList = new ArrayList<>();
        try {
            //generate fresh graph;
            int lineCounter = 0;
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                if(lineCounter == 0){
                    planName = data;
                    writePlan.add(planName);
                    System.out.println(planName);
                    lineCounter++;
                }else {
                    writePlan.add(data);
                    String[] line = data.split(",");
                    String addressOne = line[0];
                    String addressTwo = line[1];

                    String vertex1Find = addressOne;
                    String vertex2Find = addressTwo;
                    Vertex v1 = null;
                    Vertex v2 = null;

                    for(Vertex vertex : planGraph.getVertices()){
                        if(vertex.getLabel().equals(vertex1Find)){
                            v1 = vertex;
                        }
                        if(vertex.getLabel().equals(vertex2Find)){
                            v2 = vertex;
                        }
                    }
                    if(v1 == null){
                        v1 = new Vertex(vertexInc, vertex1Find);
                        planGraph.addVertex(v1);
                        vertexInc+=1;
                    }
                    if(v2 == null){
                       v2 = new Vertex(vertexInc, vertex2Find);
                    }

                    v1.addEdge(new Edge(v2, 1)); //connect v1 v2
                    vertexList.add(v1);
                }
            }

            for(int i =0; i < vertexList.size();i++){
                Vertex tmp = vertexList.get(i);
                Vertex tmp2 = null;
                for(Edge edge: tmp.getEdges()){
                    tmp2 = edge.getTo();
                }
                for(Vertex vertex2 : graph.getVertices()){
                    Vertex tmpVert2 = null;
                    if(vertex2.getId() == tmp.getId()){
                        tmpVert2 = vertex2;
                        if(tmpVert2 == null){
                            System.out.println("Error, plan does not work");
                            return null;
                        }
                    }
                    for(Edge edge2 : vertex2.getEdges()){
                        if(edge2.getTo().getId() == tmp2.getId()){
                            Edge tmpEdge2 = edge2;
                            if(tmpVert2 != null) {
                                int edgeWeight = tmpEdge2.getWeight();
                                System.out.println(tmpVert2.getLabel() + " to " + tmpEdge2.getTo().getLabel() + " Cost: " + edgeWeight);
                                totalCost += edgeWeight;
                                //Construct new Vertex & Edge to place in return graph;
                                Vertex returnVert = new Vertex(tmpVert2.getId(),tmpVert2.getLabel());
                                returnVert.addEdge(new Edge(tmpEdge2.getTo(),edgeWeight));
                                returnGraph.addVertex(returnVert);

                            }
                        }
                    }
                }
            }

            System.out.println("Total cost: " + totalCost);
            fileReader.close();
        }catch(FileNotFoundException e){
            System.out.println("File not Found.");
        }

        if(verifyPlan(returnGraph)){
            if(verifyMinCost(graph,totalCost)){
                return returnGraph;
            } else{
                System.out.println("Found a more efficient pavement way");
                return createMinCost(graph);
            }
        }else{
            System.out.println("Error, graph is not connected");
            return null;
        }
    }

    /**
     * function that verifies if all nodes are reachable by performing a DFS
     * @param planGraph plan graph that is used to look
     * @return returns a boolean value on whether or not graph is connected
     */
    public static boolean verifyPlan(Graph planGraph){
            //graphsize + 1 because the final edge getTo will never be a vertex that's in the graph
            //for the plan.
            return planGraph.isConnected(planGraph,planGraph.size()+1);
    }

    /**
     * Verifies the minimin cost of the plan
     * @param graph graph to be sed for verifcation
     * @param planCost the plan cost derived from the plan
     * @return returns whether or not it is indeed the minimum cost
     */
    public static boolean verifyMinCost(Graph graph, int planCost){
        int minTotal = 0;
        boolean[] visited = new boolean[graph.size()];
        for(Vertex vertex : graph.getVertices()){
            int minCost = Integer.MAX_VALUE;
            //Iterate through all of the vertices connected to this vertex
            for(Edge edge : vertex.getEdges()){
                //retrieve the Node that is at connected
                Vertex temp = edge.getTo();
                //retrieve ID of node
                int index = temp.getId();
                //If the node has not been visited, mark as visited
                if(visited[index] == false){
                    if(edge.getWeight() < minCost){
                        minCost = edge.getWeight();
                        minTotal += minCost;
                    }
                    visited[index] = true;
                }
            }
        }
        return planCost <= minTotal;
    }

    /**
     * Creates a min cost paving plan for graph
     * @param graph graph to be used to develop minimum cost for
     * @return returns a minimum cost graph
     */
    public static Graph createMinCost(Graph graph){
        String planName;
        Scanner sc = new Scanner(System.in);
        writePlan = new ArrayList<String>();
        Graph returnGraph = new Graph();
        int minTotal = 0;
        boolean[] visited = new boolean[graph.size()];

        if(graph == null){
            System.out.println("ERROR: No graph loaded");
        }
        else{
            System.out.print("Enter plan name: ");
            planName = "\"" + sc.nextLine() + "\"" ;
            writePlan.add(planName);
            for(Vertex vertex : graph.getVertices()){
                visited[vertex.getId()] = true;
                //Iterate through all of the vertices connected to this vertex
                for(Edge edge : vertex.getEdges()){
                    //retrieve the Node that is at connected
                    Vertex temp = edge.getTo();
                    //retrieve ID of node
                    int index = temp.getId();
                    int minCost = Integer.MAX_VALUE;
                    //If the node has not been visited, mark as visited
                    if(visited[index] == false){
                        if(edge.getWeight() < minCost){
                            int weight = edge.getWeight();
                            Vertex tmp = new Vertex(vertex.getId(),vertex.getLabel());
                            tmp.addEdge(new Edge(edge.getTo(),weight));
                            returnGraph.addVertex(tmp);
                            System.out.println(vertex.getLabel() + "," + edge.getTo().getLabel() + "Cost: " + edge.getWeight());
                            writePlan.add(vertex.getLabel() + "," + edge.getTo().getLabel());
                            minCost = edge.getWeight();
                            minTotal += minCost;
                        }
                        System.out.println("Total Cost: " + minTotal);
                        visited[index] = true;
                    }
                }
            }
        }

        return returnGraph;
    }
}
