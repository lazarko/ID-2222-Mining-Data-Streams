import java.io.File;
import java.io.FileNotFoundException;
import java.security.DigestException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.security.MessageDigest;

public class Driver {
    //Constants
    private static final String filename = "web-Google.txt";
    private static final int b = 4;
    private static HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>(); //hashmap of nodes and their edges.

    public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, DigestException {
        //HyperLogLog hll = new HyperLogLog(b);
        ArrayList<Integer> inputStream = readFile();
        int[] counter = new int[(int)Math.pow(2,b)];
        /*
        for(int i = 0; i < inputStream.size(); i++){
            counter = hll.add(counter, inputStream.get(i));
            if(i % 100000 == 1){
                System.out.println(hll.size(counter));
            }
        }
         */
        new HyperBall(graph, b); //anropar och utför hyperball operationen


    }

    private static ArrayList<Integer> readFile() throws FileNotFoundException {
        ArrayList<Integer> inputFile = new ArrayList<>();
        ArrayList<String[]> edgeList = new ArrayList<>();
        // pass the path to the file as a parameter
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            String[] splitted = s.split("\t");
            edgeList.add(splitted);
            for (String value : splitted) {
                inputFile.add(Integer.parseInt(value));

            }

        }

        graph.put(Integer.parseInt(edgeList.get(0)[0]), new ArrayList<>(Integer.parseInt(edgeList.get(1)[1])));
        for (int i = 1; i < edgeList.size(); i++){

            String[] edge = edgeList.get(i);
            if (!edge[0].equals(edgeList.get(i-1)[0])){
                ArrayList<Integer> connected_nodes = new ArrayList<>();
                connected_nodes.add(Integer.parseInt(edge[1]));
                graph.put(Integer.parseInt(edge[0]), connected_nodes);
            }
            else{
                int node = Integer.parseInt(edge[0]);
                ArrayList<Integer> list = graph.get(node);
                list.add(Integer.parseInt(edge[1]));
                graph.put(node, list);
            }
        }
        //graph.entrySet().forEach(entry -> System.out.println(entry.getKey() + "-->" + entry.getValue()));

        sc.close();
        return inputFile;

    }

}
