public class HyperLogLog {

    private int[] counter;
    private int tail;
    private double alpha;
    private int b;



    public HyperLogLog(int b_val){
        b = b_val;
        int m = (int) Math.pow(2, b);

        tail = m;
        counter = new int[m];
        if(m == 16){
            alpha = 0.673;
        }else if(m == 32){
            alpha = 0.697;
        }else if(m == 64){
            alpha = 0.709;
        }else if(m >= 128){
            alpha = 0.7213/(1+ 1.079/m);
        }
    }



    private String truncate(int item){
        int hash = Integer.hashCode(item);
        // HASHA TILL BINARY OCH SE TILL ATT STRÄNGEN ÄR LÄNGD TAIL (FN 16 BITS)
        return String.format("%"+tail+"s", Integer.toBinaryString(hash)).replace(' ', '0');
    }

    public int[] add(int item){
        String hashed = truncate(item);

        // ADDRESS BITS: FIRST B (4) BITS
        String address_bits = hashed.substring(0,b-1);
        int index = Integer.parseInt(address_bits, 2) + 1;
        //REMAINING BITS TO GET LEADING ZEROS
        String remaining_bits = hashed.substring(b);
        int leading = remaining_bits.indexOf("1") + 1;

        counter[index] = Math.max(index, leading);
        return counter;
    }


    public double size() {
        float sum = 0;
        for (int i : counter) {
            sum += Math.pow(2, -i);
        }
        double e =  Math.pow(sum, -1)*alpha*Math.pow(counter.length, 2);
        if(e <= (5/2)*tail ){
            int cnt = 0;
            for(int i = 0; i < counter.length; i++){
                if(counter[i] == 0){
                    cnt++;
                }
            }
            if(cnt != 0){
                double tmp = tail/cnt;
                System.out.println("HELLO" + tmp);
                return tail*Math.log(tmp)/Math.log(2);
            }else{
                return e;
            }

        }else if(e <= (1/30) * Math.pow(2,32)){
            return e;
        }else if(e > (1/30) * Math.pow(2,32)){ //Knas här nere. Potentiellt fel här nere
            return -Math.pow(2, 32)*(Math.log(1 - (e/Math.pow(2, 32))) / Math.log(2));
        }
        return  Math.pow(sum, -1)*alpha*Math.pow(counter.length, 2);
    }


}
