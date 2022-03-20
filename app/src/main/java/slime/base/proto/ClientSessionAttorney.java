package slime.base.proto;

import slime.base.proto.types.CommandData;
import slime.base.proto.types.BaseType;
import slime.base.proto.types.Handle;
import java.nio.ByteBuffer;
import java.util.UUID;


public class ClientSessionAttorney {
    private ClientSession session;
    public ClientSessionAttorney(ClientSession s){
        this.session = s;
    }

    public boolean isCommandActive(UUID command){
        return false;
    }

    public void sendPacket(CommandData data){
        session.sendPacket(data);
    }

    // TODO: We should have this in som special privileged class
    public void setReadingMode(SByteReadingMode to){
        session.setReadingMode(to);
    }
}