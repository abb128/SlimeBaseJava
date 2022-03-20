package slime.base.proto;

import slime.base.proto.types.Handle;

import java.nio.ByteBuffer;
import java.util.UUID;

public class SByteBuffer {
    private ByteBuffer inner;
    private SByteReadingMode readingMode;

    public SByteBuffer(ByteBuffer b, SByteReadingMode r){
        this.inner = b;

        this.readingMode = r;

        if(this.readingMode == null){
            // Default to big endian
            // TODO: Temporary
            System.out.println("SByteBuffer initialized without reading mode, defaulting to big endian");
            this.readingMode = SByteReadingMode.ALL_BE;
        }
    }

    public SByteBuffer(byte[] bytes, int offset, int len, SByteReadingMode r){
        this(ByteBuffer.wrap(bytes, offset, len), r);
    }


    public SByteReadingMode getReadingMode(){
        return readingMode;
    }

    public void setReadingMode(SByteReadingMode to){
        readingMode = to;
    }



    public SByteBuffer duplicate(){
        return new SByteBuffer(inner.duplicate(), readingMode);
    }

    private void setupByteOrder(){
        inner.order(readingMode.getNumOrder());
    }

    private void setupShortOrder(){
        setupByteOrder();
    }

    private void setupIntOrder(){
        setupByteOrder();
    }

    private void setupLongOrder(){
        setupByteOrder();
    }

    private void setupHandleOrder(){
        setupByteOrder();
    }

    private void setupUUIDOrder(){
        inner.order(readingMode.getUuidOrder());
    }


    public Handle getHandle() {
        setupHandleOrder();
        return Handle.get_s(this);
    }

    public void putHandle(Handle h) {
        setupHandleOrder();
        h.put(this);
    }


    public UUID getUUID() {
        setupUUIDOrder();
        return Util.getUUID(inner, readingMode);
    }

    public void putUUID(UUID uid) {
        setupUUIDOrder();
        Util.putUUID(uid, inner, readingMode);
    }


    public byte getI8() {
        setupByteOrder();
        return inner.get();
    }

    public short getU8() {
        setupByteOrder();
        return (short)(inner.get() & 0xFF);
    }

    public void putI8(byte b) {
        setupByteOrder();
        inner.put(b);
    }

    public void putU8(short b) {
        setupByteOrder();
        inner.put((byte)(b & 0xFF));
    }

    public short getI16() {
        setupShortOrder();
        return inner.getShort();
    }

    public int getU16() {
        setupShortOrder();
        return inner.getShort() & 0xFFFF;
    }

    public void putI16(short s) {
        setupShortOrder();
        inner.putShort(s);
    }

    public void putU16(int s) {
        setupShortOrder();
        inner.putShort((short)(s & 0xFFFF));
    }

    public int getI32() {
        setupIntOrder();
        return inner.getInt();
    }

    public long getU32() {
        setupIntOrder();
        return (long)(inner.getInt() & 0xFFFF_FFFFL);
    }

    public void putI32(int i) {
        setupIntOrder();
        inner.putInt(i);
    }

    public void putU32(long i) {
        setupIntOrder();
        inner.putInt((int)(i & 0xFFFF_FFFFL));
    }

    public long getI64() {
        setupLongOrder();
        return inner.getLong();
    }

    public void putI64(long l) {
        setupLongOrder();
        inner.putLong(l);
    }

    public void mark(){ inner.mark(); }
    public void reset(){ inner.reset(); }


    public Handle peekHandle(){
        mark(); Handle v = getHandle(); reset();
        return v;
    }

    public UUID peekUUID(){
        mark(); UUID v = getUUID(); reset();
        return v;
    }

    public short peekU8(){
        mark(); short v = getU8(); reset();
        return v;
    }
    public byte peekI8(){
        mark(); byte v = getI8(); reset();
        return v;
    }

    public int peekU16(){
        mark(); int v = getU16(); reset();
        return v;
    }
    public short peekI16(){
        mark(); short v = getI16(); reset();
        return v;
    }

    public long peekU32(){
        mark(); long v = getU32(); reset();
        return v;
    }
    public int peekI32(){
        mark(); int v = getI32(); reset();
        return v;
    }

    public long peekI64(){
        mark(); long v = getI64(); reset();
        return v;
    }

}