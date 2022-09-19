import java.io.*;


public class processtown
{
    public static void main(String[] args){
        String argIn = "";
        if(args.length >0) {
            argIn = args[0];
        }
        Graph<Integer> g = new Graph<Integer>();

        // edges are added.
        // Since the graph is bidirectional,
        // so boolean bidirectional is passed as true.
        g.addEdge(0, 1, true);
        g.addEdge(0, 4, true);
        g.addEdge(1, 2, true);
        g.addEdge(1, 3, true);
        g.addEdge(1, 4, true);
        g.addEdge(2, 3, true);
        g.addEdge(3, 4, true);

        // Printing the graph
        System.out.print("Graph:\n"
                + g.toString());

        switch(argIn){
            case "":
                System.out.print("\"Test\"\n");
                System.out.print("3,\"1 First Street\",\"2 First Street\"\n");
                System.out.print("2,\"1 Second Street\",\"1 First Street\"\n");
                System.out.print("3,\"2 First Street\",\"1 Second Street\"\n");
                break;
            case  "a":
                // TODO Show the current town in alternate format.
                break;
            case  "r":
                // TODO Open File
                break;
            case  "s":
                break;
            case  "v":
                break;
            case  "w":
                // TODO Write town data to file whose name is given in optarg.
                break;
            case  "h":
                System.out.print("Syntax: h [-option [parameter]]\n");
                System.out.print("  options:\n");
                System.out.print("       s   show current town in standard format\n");
                System.out.print("       a   show current town in alternate format\n");
                System.out.print("       r   read town data from file identified by parameter\n");
                System.out.print("       w   write current town to file identified by parameter\n");
                System.out.print("       v   show version\n");
                System.out.print("       h   help (this display)\n");
                break;
            default:
                System.out.println("Argument not understood, enter h for options");
        }
    }
}
