package slime.base.proto.types;

import slime.base.proto.SByteBuffer;

import java.nio.ByteBuffer;

public interface BaseType {
    public void get(SByteBuffer from);
    public void put(SByteBuffer to);
}