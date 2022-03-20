package slime.base.proto.handlers;

import slime.base.proto.ClientSessionAttorney;
import slime.base.proto.handlers.BaseHandler;
import java.nio.ByteBuffer;
import java.util.UUID;

public abstract class BaseHandlerFactory {
    public abstract UUID getUUID();
    public abstract BaseHandler create(ClientSessionAttorney attorney);
}