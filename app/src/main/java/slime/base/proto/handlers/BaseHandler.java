package slime.base.proto.handlers;

import slime.base.proto.ClientSessionAttorney;
import slime.base.proto.SByteBuffer;

import java.util.UUID;

public abstract class BaseHandler {
    private ClientSessionAttorney attorney_v;
    protected BaseHandler(ClientSessionAttorney a){
        this.attorney_v = a;
    }

    protected ClientSessionAttorney attorney(){
        return attorney_v;
    }

    public abstract UUID getCommandUUID();

    public abstract void acceptPacket(SByteBuffer buff);
    public abstract void tick();
}