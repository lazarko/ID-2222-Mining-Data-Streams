import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HyperLogLog {

    //private int[] counter;
    private int tail;
    private double alpha;
    private int b;



    public HyperLogLog(int b_val){
        b = b_val;
        int m = (int) Math.pow(2, b);

        tail = m;
        //counter = new int[m];
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
        //System.out.println("hashCode" + hash);
        // HASHA TILL BINARY OCH SE TILL ATT STRÄNGEN ÄR LÄNGD TAIL (FN 16 BITS)
        String binary = Integer.toBinaryString(hash);
        return binary;
        //System.out.println("pre formatt binary" + binary);
        //return String.format("%"+tail+"s", Integer.toBinaryString(hash)).replace(' ', '0');
    }
    private String hash(int x) throws NoSuchAlgorithmException {
        int hash = 3*x + 5 % 31;
        //int hash = Integer.hashCode(x);

        String binary = Integer.toBinaryString(hash);
        //System.out.println("binary " + binary);
        if (binary.length() < tail){
            int len = tail - binary.length();
            String pad = String.format("%0"+len+"d",0);
            String result = pad + binary;
            return result;
        }
        //return String.format("%"+tail+"s",Integer.toBinaryString(hash)).replace(' ', '0');
        return binary;
    }

    public int[] add(int[] counter, int item) throws NoSuchAlgorithmException {
        //System.out.println("item: " + item);
        //MessageDigest md = MessageDigest.getInstance("SHA-1");
        //byte[] byte_array = md.digest(Integer.toString(item).getBytes());
        //String hashed = "";
        /*for (int i =0; i<byte_array.length; i++){
            String bin = Integer.toBinaryString(byte_array[i]);
            hashed += bin;
        }*/
        // ADDRESS BITS: FIRST B BITS
        /*if (hashed.length() < b){
            hashed = String.format("%"+tail+"s", hashed).replace(' ', '0');
        }*/
        String hashed = hash(item);
        System.out.println("hashed " + hashed + " size: " + hashed.length());
        String address_bits = hashed.substring(0,b);

        int index = Integer.parseInt(address_bits, 2);
        System.out.println("address: " + index);
        //REMAINING BITS TO GET LEADING ZEROS
        String remaining_bits = hashed.substring(b);
        int leading = remaining_bits.indexOf("1") + 1;

        System.out.println("leading: " + leading);
        counter[index] = Math.max(counter[index], leading);

        return counter;
    }


    public double size(int[] counter) {
        for (int count:counter) {
            System.out.println(count);
        }
        float sum = 0;
        for (int i : counter) {
            sum += Math.pow(2, -i);
        }
        double e =  Math.pow(sum, -1)*alpha*Math.pow(counter.length, 2);
        System.out.println("e: " + e);
        if(e <= (5.0/2.0)*tail ){
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

        }else if(e <= (1.0/30.0) * Math.pow(2,32)){
            return e;
        }else if(e > (1.0/30.0) * Math.pow(2,32)){ //Knas här nere. Potentiellt fel här nere
            return -Math.pow(2, 32)*(Math.log(1 - (e/Math.pow(2, 32))) / Math.log(2));
        }
        return  Math.pow(sum, -1)*alpha*Math.pow(counter.length, 2);
    }


}
