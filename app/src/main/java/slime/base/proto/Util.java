package slime.base.proto;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Util {
    public static UUID getUUID(ByteBuffer buff, SByteReadingMode mode){
        long first = buff.getLong();
        long second = buff.getLong();

        if(mode.isUuidMSBFirst()){
            return new UUID(first, second);
        }else{
            return new UUID(second, first);
        }
    }

    public static void putUUID(UUID u, ByteBuffer buff, SByteReadingMode mode){
        if(mode.isUuidMSBFirst()){
            buff.putLong(u.getMostSignificantBits());
            buff.putLong(u.getLeastSignificantBits());
        }else{
            buff.putLong(u.getLeastSignificantBits());
            buff.putLong(u.getMostSignificantBits());
        }
    }
}