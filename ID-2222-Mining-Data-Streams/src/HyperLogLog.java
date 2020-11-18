public class HyperLogLog {

    private final Long LARGE_PRIME =  948701839L;
    private final Long LARGE_PRIME2 = 6920451961L;
    private int tail_size;



    public HyperLogLog(int l){
        tail_size = l;
    }

    /**
     * TEST HASH FUNKTION (lånat från stackoverflow). TODO: MÅSTE SKRIVAS OM DET MÅSTE VA B = LOG2 (M) hashning
     * @param val
     * @return
     */
    private int hash(int val){
        long scaled = (long) val * LARGE_PRIME;

        // Fill in the lower bits
        long shifted = scaled + LARGE_PRIME2;

        // Add to the lower 32 bits the upper bits which would be lost in
        // the conversion to an int.
        long filled = shifted + ((shifted & 0xFFFFFFFF00000000L) >> 32);

        // Pare it down to 31 bits in this case.  Replace 7 with F if you
        // want negative numbers or leave off the `& mask` part entirely.
        int masked = (int) (filled & 0x7FFFFFFF);
        return masked;

    }

    /**
     * i cannot exceed 255, TODO: MAKE SURE IT CAN
     * Counts the number of leading zeros
     * @return
    private int rho(int i) {

        int lz = 8;
        Integer.numberOfLeadingZeros(i);
        while(i)
        {
            lz--;
            i >>>= 1;
        }
        return lz;
    }
     */


    public void add(int[] counter, int item){
        int index = hash(item);
        //int clz = rho(index);
        int clz = Integer.numberOfLeadingZeros(index); //TODO: JAG TROR DEN FUNKAR
        if(clz > counter[index]) {
            counter[index] = clz;
        }


    }


    public void size( ){

    }


}
