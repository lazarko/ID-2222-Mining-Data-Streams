import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    //Constants
    private static final String filename = "web-Google.txt";

    public static void main(String[] args) throws FileNotFoundException {
       // ArrayList<Integer> inputStream = readFile();
        System.out.println(     Integer.numberOfLeadingZeros(203050));
        System.out.println(     Integer.numberOfLeadingZeros(0000000101010101));


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
