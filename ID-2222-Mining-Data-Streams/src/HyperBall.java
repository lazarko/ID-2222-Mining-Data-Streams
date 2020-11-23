import java.lang.reflect.Array;
import java.security.DigestException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HyperBall{

    private HashMap<Integer, int[]> nodeAndCounter = new HashMap<>();




    private int sumUp(int[] arr){
        int cnt = 0;
        for(int i = 0; i < arr.length; i++){
            cnt += arr[i];
        }
        return cnt;
    }

    public HyperBall(HashMap<Integer, ArrayList<Integer>> graph, int b_val) throws NoSuchAlgorithmException {
        boolean[] did_not_change = new boolean[graph.size()];
        Arrays.fill(did_not_change, false); //sätt default av did not change till false
        int counter_size = (int) Math.pow(2, b_val);
        int[][] counters = new int[graph.size()][counter_size];
        HyperLogLog hll = new HyperLogLog(b_val);


        for(int i = 0; i < graph.size(); i++){
            counters[i] = hll.add(counters[i], i);
            nodeAndCounter.put(i , counters[i]);

        }



        int t = 0;
        boolean has_changed = true;

        while(true){
            for(int v : graph.keySet()){
                int[] a = counters[v];
                for (int w : graph.get(v)){
                    a = union(counters[w], a);
                }
                nodeAndCounter.put(v, a);


            }
            


            t++;
        }


        /**
        while (Arrays.asList(did_not_change).contains(false)){
            for (int v: graph.keySet()) { //pseudocode line 12-end
                int[] a = Arrays.copyOf(counters[v], counters[v].length);
                //gör deep copy av counters[v] för att kunna kolla om a förändrades av union
                for (int w : graph.get(v)){
                    a = union(counters[w], a);
                }
                //WRITE (V,A) TO DISK?? SKRIVA TILL FIL ELLER BEHÅLLA I MINNE__
                //DO SOMETHING WITH A AND C[V] - GÖRA VAD? RÄKNA UT CENTRALITY??
                if (Arrays.equals(counters[v], a)){ // OM A INTE FÖRÄNDRADES AV OPERATIONEN UNION
                    did_not_change[v] = true;
                }
                System.out.println("V value: " + v + "Array: " + Arrays.toString(a));
                nodeAndCounter.put(v, a); // BEHÖVER VI DENNA??
                counters[v] = a; //Uppdatera counters inför nästa loop


                //DO SOMETHING WITH A AND C[V]
            //READ THE PAIRS (V,A) AND UPDATE THE ARRAY COUNTERS
            //TERMINATE IF NO COUNTER CHANGED.
            }
            System.out.println(hll.size(counters[t]));
            for (boolean val: did_not_change){ //OM INGEN COUNTER ÄNDRADES I SENASTE ITERATIONEN SÅ BRYTER VI...
                if (!val)
                    break;
            }
            t++;
        }
         */

    }


    public int[] union(int[] counter1, int[] counter2){ //Pseudocode line 2-6
        for (int i:counter1) {
            counter1[i] = Math.max(counter1[i], counter2[i]);
        }
        return counter1;
    }






}