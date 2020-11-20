public class HyperLogLog {

    private int[] counter;
    private int tail;
    private double alpha = 0.673;
    private int b;



    public HyperLogLog(int b_val){
        b = b_val;
        int m = (int) Math.pow(2, b);

        tail = m;
        counter = new int[m];

    }



    private String truncate(int item){
        int hash = Integer.hashCode(item);
        // HASHA TILL BINARY OCH SE TILL ATT STRÄNGEN ÄR LÄNGD TAIL (FN 16 BITS)
        return String.format("%"+tail+"s", Integer.toBinaryString(hash)).replace(' ', '0');
    }

    public int[] add(int item){
        //String hashed = truncate(item);
        String hashed = truncate(item);
        System.out.println("bin string " + hashed + " size " + hashed.length() );
        // ADDRESS BITS: FIRST B (4) BITS
        String address_bits = hashed.substring(0,b);
        int index = Integer.parseInt(address_bits, 2) + 1;
        //REMAINING BITS TO GET LEADING ZEROS
        String remaining_bits = hashed.substring(b);
        int leading = remaining_bits.indexOf("1") + 1;
        //IF THERE IS NO 1 IN THE STREAM, NUMBER OF LEADING ZEROS IS FULL LENGTH OF STREAM
        if (leading == 0){
            leading = remaining_bits.length() + 1;
        }
        //int index = 0;
        for(int i = 0; i < hashed.length(); i++){
            if(hashed.charAt(hashed.length()-1-i) == '1'){
                index = i;
            }
        }
        //int leading = (index + 1);
        //int leading = hashed.indexOf("1") + 1;
        System.out.println(leading);
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
