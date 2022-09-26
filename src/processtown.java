import java.io.*;
import java.util.*;


public class processtown
{
    static ArrayList<String> writeList = new ArrayList<String>();
    public static void main(String[] args){
        int vertexInc = 0;
        Graph graph = new Graph();
        Graph planGraph;
        String townName = "";
        //arraylist to store the lines to write to
        File file = new File("MiniTown.dat");
        System.out.println("Opening " + file + "...");
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

        System.out.println("\n");
        System.out.println("Is this graph connected? " + graph.isConnected(graph));

        writeList = write("Poopie",writeList);

        // graph = generateGraph();


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
                System.out.println("Writing");
                for (String i : writeList) {
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
        Graph graph = new Graph();
        Random rand = new Random();
        writeList.add("Generic Town");
        //set to contain connected vertexes
        Set<Vertex> isConnected = new HashSet<>();
        Vertex gVertex;
        int numBuildings;
        int[] buildings;
        int maxEdges = 0;
        int edgeInc = 0;
        Scanner inPut = new Scanner(System.in);
        System.out.println("How many buildings do you want? ");

        numBuildings = inPut.nextInt();
        buildings = lcm(numBuildings);
        maxEdges = (numBuildings)*(numBuildings-1)/2;

        //Insert created data in to graph
        for(int i=0; i < buildings.length;i++){
            gVertex = new Vertex(i,"\"Building " + buildings[i] + "\"");
            graph.addVertex(gVertex);
        }

        for(Vertex vertex: graph.getVertices()){
            System.out.println(vertex.getLabel() + ":" + vertex.getId());
        }
        System.out.println(graph.size());

        System.out.println(graph.isConnected(graph));

        while(!graph.isConnected(graph)) {
            for (Vertex vertex : graph.getVertices()) {
                Vertex tmp = vertex;
                Vertex tmp2;
                int tmpNum = tmp.getId();
                int tmpNumTwo;
                String buildingOne = tmp.getLabel();
                String buildingTwo;
                System.out.println("Starting loop");
                if(!isConnected.contains(tmpNum)){
                    System.out.println("poopie");
                    isConnected.add(tmp);
                }
                for (Vertex vertexTwo : graph.getVertices()) {
                    tmp2 = vertexTwo;
                    tmpNumTwo = tmp2.getId();
                    buildingTwo = tmp2.getLabel();

                    int test = rand.nextInt(numBuildings - 0) + 0;

                    while (test > numBuildings && test != tmpNum) {
                        System.out.println(test);
                        test = rand.nextInt(numBuildings - 0) + 0;
                    }
                    if (tmpNumTwo != tmpNum && tmpNumTwo == test) {
                        System.out.println("You entered the end of loop");
                        if(!isConnected.contains(tmpNumTwo)){
                            System.out.println(buildingOne + "," + buildingTwo);
                            isConnected.add(tmp2);
                            tmp.addEdge(new Edge(tmp2, 1)); //connect v1 v2
                            tmp2.addEdge(new Edge(tmp, 1));
                        }
                    }
                }
            }
        }

        System.out.println(graph.isConnected(graph));


        for(Vertex vertex : graph.getVertices()){
            System.out.println(vertex.getLabel());
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

    public static Graph readPlan(){
        String planName;
        String plan;
        File file = new File("MiniTown.dat");
        System.out.println("Opening " + file + "...");
        try {
            writeList = new ArrayList<String>();
            //generate fresh graph;
            graph = new Graph();
            int lineCounter = 0;
            Scanner fileReader = new Scanner(file);
            vertexInc = 0;
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                if(lineCounter == 0){
                    planName = data;
                    writeList.add(planName);
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

        return graph;
    }
}
