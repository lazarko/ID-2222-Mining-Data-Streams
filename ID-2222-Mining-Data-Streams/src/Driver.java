import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    //Constants
    private static final String filename = "web-Google.txt";
    private static final int b = 10;
    public static void main(String[] args) throws FileNotFoundException {
        HyperLogLog hll = new HyperLogLog(b);
        ArrayList<Integer> inputStream = readFile();

        for(int i = 0; i < inputStream.size(); i++){
            //System.out.println(Arrays.toString(hll.add(inputStream.get(i))));
            hll.add(inputStream.get(i));
            if((i % 1000) == 1){
                System.out.println(hll.size());
            }
        }


    }

    private static ArrayList<Integer> readFile() throws FileNotFoundException {
        ArrayList<Integer> inputFile = new ArrayList<>();
        // pass the path to the file as a parameter
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            String[] splitted = s.split("\t");
            for (String value : splitted) {
                inputFile.add(Integer.parseInt(value));
            }
        }
        sc.close();
        return inputFile;

    }

}
