import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

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


    private String hash(int item) throws NoSuchAlgorithmException {
        String itemString = Integer.toString(item);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
        byte[] messageDigest = md.digest(itemString.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(2);
        return hashtext.substring(68, 100);

    }


    public int[] add(int[] counter, int item) throws NoSuchAlgorithmException {

        String hashed = hash(item);
        String address_bits = hashed.substring(0, b);

        int index = Integer.parseInt(address_bits, 2);
        //REMAINING BITS TO GET LEADING ZEROS
        String remaining_bits = hashed.substring(b);
        int leading = remaining_bits.indexOf("1") + 1;

        counter[index] = Math.max(counter[index], leading);

        return counter;
    }


    public double size(int[] counter) {
        System.out.println();
        float sum = 0;
        for (int i : counter) {
            sum += Math.pow(2, -i);
        }
        double e =  Math.pow(sum, -1)*alpha*Math.pow(counter.length, 2);
        //System.out.println("e: " + e);
        if(e <= (5.0/2.0)*tail){
            int cnt = 0;
            for(int i = 0; i < counter.length; i++){
                if(counter[i] == 0){
                    cnt++;
                }
            }
            if(cnt != 0){
                double tmp = tail/cnt;
               // System.out.println("HELLO" + tmp);
                return tail*Math.log(tmp)/Math.log(2);
            }else{
                return e;
            }

        }else if(e <= (1.0/30.0) * Math.pow(2.0, 32.0)){
            return e;
        }else if(e > (1.0/30.0) * Math.pow(2.0,32.0)){ //Knas här nere. Potentiellt fel här nere
            double log2 = Math.pow(2, 32) / Math.log(2);
            return -Math.pow(2.0, 32.0)*Math.log(1 - (e/log2));
        }
        return  Math.pow(sum, -1)*alpha*Math.pow(counter.length, 2);
    }


}
