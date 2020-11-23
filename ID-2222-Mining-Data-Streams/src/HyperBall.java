import java.lang.reflect.Array;
import java.security.DigestException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HyperBall{
    public HashMap<Integer, int[]> counters;
    private HashMap<Integer, int[]> nodeAndCounter = new HashMap<>();
    private ArrayList<Boolean> did_not_change;
    private HashMap<Integer, Double> centralities;

    public HyperBall(HashMap<Integer, ArrayList<Integer>> graph, int b_val) throws NoSuchAlgorithmException, DigestException {
        did_not_change = new ArrayList<>();
        //Arrays.fill(did_not_change, false); //sätt default av did not change till false
        int counter_size = (int) Math.pow(2, b_val);
        //counters = new int[graph.size()][counter_size];
        HyperLogLog hll = new HyperLogLog(4);
        for (int v : graph.keySet()) { //pseudocode line 8-10
            int[] new_counter = new int[counter_size];
            int[] counter = hll.add(new_counter, v);
            counters.put(v, counter); //gets changed
            nodeAndCounter.put(v, counter); //keeps track of counter from past iteration
            centralities.put(v, 0.0); //initialize centrality counter
        }
        int t = 0;
        //boolean has_changed = true;

        do {
            for (int v : graph.keySet()) {
                int[] a = counters.get(v);
                for (int w : graph.get(v)) {
                    a = union(counters.get(w), a);
                }
                double ball_a = hll.size(a); //size of coreachable set of x
                double ball_cv = hll.size(counters.get(v));
                if (t > 0) {
                    double harmonic_centrality = (1 / t) * (ball_a - ball_cv);
                    centralities.put(v, centralities.get(v) + harmonic_centrality);
                    //iterative sum of centrality of each node at t
                }
                if (Arrays.equals(counters.get(v), nodeAndCounter.get(v))){
                    did_not_change.add(true);
                }
                else{
                    did_not_change.add(false);
                }
                nodeAndCounter.put(v, a);
            }
            t++;
        }while(did_not_change.contains(false));
        //returnera någon centrality
    }


        /**
        while (Arrays.asList(did_not_change).contains(false)){

            for (int v: graph.keySet()) { //pseudocode line 12-end
                int[] a = Arrays.copyOf(counters[v], counters[v].length);
                //gör deep copy av counters[v] för att kunna kolla om a förändrades av union
                for (int w: graph.get(v)){
                    a = union(counters[w], a);
                }
                //WRITE (V,A) TO DISK?? SKRIVA TILL FIL ELLER BEHÅLLA I MINNE__
                //DO SOMETHING WITH A AND C[V] - GÖRA VAD? RÄKNA UT CENTRALITY??
                if (Arrays.equals(counters[v], a)){ // OM A INTE FÖRÄNDRADES AV OPERATIONEN UNION
                    did_not_change[v] = true;
                }
                nodeAndCounter.put(v, a); // BEHÖVER VI DENNA??
                counters[v] = a; //Uppdatera counters inför nästa loop
                // ev skriva nodeAndCounter till fil

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

    }*/

    public int[] union(int[] counter1, int[] counter2){ //Pseudocode line 2-6
        for (int i:counter1) {
            counter1[i] = Math.max(counter1[i], counter2[i]);
        }
        return counter1;
    }






}