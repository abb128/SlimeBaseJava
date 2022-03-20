package slime.base.proto.types;

import slime.base.proto.SByteBuffer;
import slime.base.proto.types.BaseType;
import java.nio.ByteBuffer;

public class Handle implements BaseType {
    private short value;
    
    public Handle(){ value = 0; }
    
    @Override
    public int hashCode(){ return (int)value; }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof Handle)) return false;
        Handle other = (Handle) obj;
        return value == other.value;
    }
    
    @Override
    public String toString(){
        return "Handle(" + String.valueOf(value) + ")";
    }
    
    public static Handle fromByte(byte v){
        assert (v & 128) == 0;
        
        Handle h = new Handle();
        h.value = v;
        
        return h;
    }


    @Override
    public void put(SByteBuffer to){
        to.putU8(this.value);
    }
    
    @Override
    public void get(SByteBuffer from){
        short v = from.getU8();
        assert (v & 128) == 0;
        this.value = v;
    }

    public static Handle get_s(SByteBuffer from){
        Handle h = new Handle();
        h.get(from);
        return h;
    }
}