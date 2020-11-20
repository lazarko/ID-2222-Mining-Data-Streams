import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Driver {
    //Constants
    private static final String filename = "web-Google.txt";
    private static final int b = 4;
    public static void main(String[] args) throws FileNotFoundException {
        HyperLogLog hll = new HyperLogLog(b);
        ArrayList<Integer> inputStream = readFile();
        for(int i = 0; i < inputStream.size(); i++){
            //System.out.println(Arrays.toString(hll.add(inputStream.get(i))));
            hll.add(inputStream.get(i));
            //if((i % 1000) == 1){
                //System.out.println(hll.size());
            //}
        }
        System.out.println(hll.size());


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
        HashMap<Integer, ArrayList<Integer>> adjacency_list = new HashMap<>();
        adjacency_list.put(Integer.parseInt(edgeList.get(0)[0]), new ArrayList<>(Integer.parseInt(edgeList.get(1)[1])));
        for (int i = 1; i < edgeList.size(); i++){

            String[] edge = edgeList.get(i);
            if (!edge[0].equals(edgeList.get(i-1)[0])){
                ArrayList<Integer> connected_nodes = new ArrayList<>();
                connected_nodes.add(Integer.parseInt(edge[1]));
                adjacency_list.put(Integer.parseInt(edge[0]), connected_nodes);
            }
            else{
                int node = Integer.parseInt(edge[0]);
                ArrayList<Integer> list = adjacency_list.get(node);
                list.add(Integer.parseInt(edge[1]));
                adjacency_list.put(node, list);
            }
        }
        //adjacency_list.entrySet().forEach(entry -> System.out.println(entry.getKey() + "-->" + entry.getValue()));


        sc.close();
        return inputFile;

    }

}
