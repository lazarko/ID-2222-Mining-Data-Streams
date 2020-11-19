
public class HyperBall{
    public int[][] counters;
    public HyperBall(ArrayList<Integer> stream){
        HyperLogLog hll = new HyperLogLog(32);
        for (int v:stream) { //pseudocode line 8-10
            counter = hll.add(counters[v], v)
        }
        int t = 0;
        while (t < 10){
            for (int v:stream) { //pseudocode line 12-end
                int[] a = counters[v]
                for (int w = 0; w < counters.length; w++){
                    a = union(counters[w], a);
                }
                //WRITE (V,A) TO DISK
                //DO SOMETHING WITH A AND C[V]
            //READ THE PAIRS (V,A) AND UPDATE THE ARRAY COUNTERS
            //TERMINATE IF NO COUNTER CHANGED.
            }
            t++
        }

    }

    public void union(int[] counter1, int[] counter2){ //Pseudocode line 2-6
        for (int i:counter1) {
            counter1 = Math.max(counter1[i], counter2[i])
        }
        return counter1;
    }





}