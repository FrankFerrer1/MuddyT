import scala.Int;
import scala.math.Equiv;

import java.io.*;
import java.util.*;


public class processtown
{
    public static void main(String[] args){
        int vertexInc = 0;
        Graph graph = new Graph();
        String townName = "";
        //arraylist to store the lines to write to
        ArrayList<String> writeList = new ArrayList<String>();


        File file = new File("MiniTown.dat");
        System.out.println("Opening " + file + "...");
        try {
            int lineCounter = 0;
            Scanner fileReader = new Scanner(file);
            vertexInc = 0;
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                if(lineCounter == 0){
                    townName = data;
                    System.out.println("TOWN NAME: " +  townName);
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
}
