// This file is automatically generated, please do not touch

package slime.base.proto.autogen;


import slime.base.proto.SByteBuffer;
import slime.base.proto.types.*;
import slime.base.proto.autogen.*;
import slime.base.proto.Util;
import java.util.UUID;

/**  Command descriptors are sent by the tracker following the handshake. */
public class SlimeCommandDescriptor implements CommandData {
	public static final UUID COMMAND_UUID = UUID.fromString("00000005-445e-4484-bc3d-f0c696a3080c");
	public static final long FLAGS = 1;

	/**  An identifier for the command, used by the server to look up and find the correct handler for this command type. This is similar to the idea of a packet type in the old protocol, except there are now 128-bits and a simple process to generate new ones. */
	public UUID uuid;

	/**  Responsibilities associated with the command. */
	public long flags;

	/**  The handle value that is being assigned, and will be used from now on to represent this command by either side. */
	public Handle assigned_handle;

	@Override
	public UUID getCommandUUID(){
		return SlimeCommandDescriptor.COMMAND_UUID;
	}

	@Override
	public void get(SByteBuffer buf){
		uuid = buf.getUUID();
		flags = buf.getU32();
		assigned_handle = buf.getHandle();
	}

	@Override
	public void put(SByteBuffer buf){
		buf.putUUID(uuid);
		buf.putU32(flags);
		buf.putHandle(assigned_handle);
	}

	public static SlimeCommandDescriptor get_s(SByteBuffer buf){
		SlimeCommandDescriptor f = new SlimeCommandDescriptor();
		f.get(buf);
		return f;
	}
}