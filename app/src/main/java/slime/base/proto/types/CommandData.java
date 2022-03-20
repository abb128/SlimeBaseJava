package slime.base.proto.types;

import java.nio.ByteBuffer;
import slime.base.proto.types.BaseType;
import java.util.UUID;

public interface CommandData extends BaseType {
    public UUID getCommandUUID();
}