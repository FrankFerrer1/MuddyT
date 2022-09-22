import java.io.*;
import java.util.*;


public class processtown
{
    public static void main(String[] args){
        Scanner choiceIn = new Scanner(System.in);
        String choice;
        File file;
        String fileIn;
        String townName;
        boolean continueMenu = true;

        Graph graph = new Graph();

        graph.add("1 First Street","2 First Street",3);
        graph.add("1 Second Street","1 First Street",2);
        graph.add("2 First Street","1 Second Street",3);

        ArrayList<Edge> adjacent = graph.findAdjacent("1 Second Street");
        System.out.print("Adjacent of 1 are: ");
        for(Edge i: adjacent)
            System.out.print(i.getDestination() + " weight:" + i.getWeight() + "\n");

        System.out.println("Please enter a choice");
        System.out.print("Syntax: h [-option [parameter]]\n");
        System.out.print("  options:\n");
        System.out.print("       s   show current town in standard format\n");
        System.out.print("       a   show current town in alternate format\n");
        System.out.print("       r   read town data from file\n");
        System.out.print("       w   write current town to file\n");
        System.out.print("       v   show version\n");
        System.out.print("       h   help (this display)\n");
        System.out.print("       x   to exit\n");

        while (continueMenu) {
            System.out.print("Please enter a choice: ");
            choice = choiceIn.next();
            switch (choice) {
                case "a":
                    System.out.println("you pressed a");
                    // TODO Show the current town in alternate format.
                    break;
                case "r":
                    System.out.println("Please enter the file you wish to load");
                    fileIn = choiceIn.next();
                    file = new File(fileIn);
                    System.out.println("Opening " + fileIn + "...");
                    try {
                        int lineCounter = 0;
                        Scanner fileReader = new Scanner(file);
                        while (fileReader.hasNextLine()) {
                            String data = fileReader.nextLine();
                            if(lineCounter == 0){
                                townName = data;
                                System.out.println("TOWN NAME: " +  townName);
                                lineCounter++;
                            }else {
                                String[] line = data.split(",");
                                int lineWeight = Integer.parseInt(line[0]);
                                String roadOne = line[1];
                                String roadTwo = line[2];
                                System.out.println("Source: " + roadOne +", Destination: " + roadTwo +", weight = " + lineWeight);
                            }
                        }
                    }catch(FileNotFoundException e){
                        System.out.println("File not Found.");
                        break;
                    }
                    break;
                case "s":
                    System.out.println("you pressed s");
                    break;
                case "v":
                    System.out.println("you pressed v");
                    break;
                case "w":
                    System.out.println("you pressed w");
                    // TODO Write town data to file whose name is given in optarg.
                    break;
                case "h":
                    System.out.print("Syntax: h [-option [parameter]]\n");
                    System.out.print("  options:\n");
                    System.out.print("       s   show current town in standard format\n");
                    System.out.print("       a   show current town in alternate format\n");
                    System.out.print("       r   read town data from file identified by parameter\n");
                    System.out.print("       w   write current town to file identified by parameter\n");
                    System.out.print("       v   show version\n");
                    System.out.print("       h   help (this display)\n");
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
}
