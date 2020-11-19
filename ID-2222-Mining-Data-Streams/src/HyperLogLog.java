public class HyperLogLog {

    private int[] counter;
    private int tail;



    public HyperLogLog(int m){
        tail = m;
        counter = new int[m];

    }



    private String truncate(int item){
        int hash = Integer.hashCode(item);
        return String.format("%"+tail+"s", Integer.toBinaryString(hash)).replace(' ', '0');
    }

    public int[] add(int item){
        String hashed = truncate(item);

        int index = 0;
        for(int i = 0; i < hashed.length(); i++){
            if(hashed.charAt(hashed.length()-1-i) == '1'){
                index = i;
            }
        }
        int leading = (index + 1);
        if(leading > counter[index]) {
            counter[index] = leading;
        }
        return counter;
    }


    public double size(double alpha) {
        float sum = 0;
        for (int i : counter) {
            sum += Math.pow(2, -i);
        }
        return  Math.pow(sum, -1)*alpha*Math.pow(counter.length, 2);
    }


}
