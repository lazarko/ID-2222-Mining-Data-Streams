import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HyperBall{
    public int[][] counters;
    private HashMap<Integer, int[]> nodeAndCounter = new HashMap<>();
    private boolean[] did_not_change;
    public HyperBall(HashMap<Integer, ArrayList<Integer>> graph, int b_val) throws NoSuchAlgorithmException {
        did_not_change = new boolean[graph.size()];
        //Arrays.fill(change_log, true );
        int counter_size = (int) Math.pow(2, b_val);
        counters = new int[graph.size()][counter_size];
        HyperLogLog hll = new HyperLogLog(4);
        for (int v:graph.keySet()) { //pseudocode line 8-10
            counters[v] = hll.add(counters[v], v);
        }
        int t = 0;
        while (t<10){

            for (int v: graph.keySet()) { //pseudocode line 12-end
                int[] a = counters[v];
                for (int w: graph.get(v)){
                    a = union(counters[w], a);
                }
                //WRITE (V,A) TO DISK?? SKRIVA TILL FIL ELLER BEHÅLLA I MINNE__
                //DO SOMETHING WITH A AND C[V] - GÖRA VAD? RÄKNA UT CENTRALITY??
                if (Arrays.equals(counters[v], a)){ // OM A INTE FÖRÄNDRADES AV OPERATIONEN UNION
                    did_not_change[v] = false;
                }
                nodeAndCounter.put(v, a); // BEHÖVER VI DENNA??

                //DO SOMETHING WITH A AND C[V]
            //READ THE PAIRS (V,A) AND UPDATE THE ARRAY COUNTERS
            //TERMINATE IF NO COUNTER CHANGED.
            }
            for (boolean val: did_not_change){ //OM INGEN COUNTER ÄNDRADES I SENASTE ITERATIONEN SÅ BRYTER VI...
                if (val == false);
                break;
            }
            t++;
        }

    }

    public int[] union(int[] counter1, int[] counter2){ //Pseudocode line 2-6
        for (int i:counter1) {
            counter1[i] = Math.max(counter1[i], counter2[i]);
        }
        return counter1;
    }






}