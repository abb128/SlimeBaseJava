package slime.base.proto;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class SByteReadingMode {
    private boolean uuidMSBFirst;
    private ByteOrder uuidOrder;
    private ByteOrder numOrder;

    public SByteReadingMode(ByteOrder uuidOrder, ByteOrder numOrder, boolean uuidMSBFirst){
        this.uuidOrder = uuidOrder;
        this.numOrder = numOrder;
        this.uuidMSBFirst = uuidMSBFirst;
    }

    private SByteReadingMode(int x){
        this(
                ((x & 0b001) != 0) ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN,
                ((x & 0b010) != 0) ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN,
                ((x & 0b100) != 0)
        );
    }

    public ByteOrder getUuidOrder() {
        return uuidOrder;
    }
    public ByteOrder getNumOrder() {
        return numOrder;
    }

    public boolean isUuidMSBFirst() {
        return uuidMSBFirst;
    }

    public static final SByteReadingMode ALL_BE = new SByteReadingMode(ByteOrder.BIG_ENDIAN, ByteOrder.BIG_ENDIAN, false);
    public static final SByteReadingMode ALL_LE = new SByteReadingMode(ByteOrder.LITTLE_ENDIAN, ByteOrder.LITTLE_ENDIAN, false);

    @Override
    public String toString(){
        return String.format("SByteReadingMode(numOrder=%s, uuidOrder=%s, uuidMSBFirst=%s)", numOrder.toString(), uuidOrder.toString(), String.valueOf(uuidMSBFirst));
    }

    public static List<SByteReadingMode> allPossibleModes(){
        List<SByteReadingMode> list = new ArrayList<SByteReadingMode>();
        for(int i=0b000; i<0b1000; i++){
            list.add(new SByteReadingMode(i));
        }

        return list;
    }
}
