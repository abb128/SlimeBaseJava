// This file is automatically generated, please do not touch

package slime.base.proto.autogen;


import slime.base.proto.SByteBuffer;
import slime.base.proto.types.*;
import slime.base.proto.autogen.*;
import slime.base.proto.Util;
import java.util.UUID;

/**  Sent by the server in response to the client handshake */
public class SlimeHandshakeResponse implements CommandData {
	public static final UUID COMMAND_UUID = UUID.fromString("00000004-445e-4484-bc3d-f0c696a3080c");
	public static final long FLAGS = 2;

	@Override
	public UUID getCommandUUID(){
		return SlimeHandshakeResponse.COMMAND_UUID;
	}

	@Override
	public void get(SByteBuffer buf){
	}

	@Override
	public void put(SByteBuffer buf){
	}

	public static SlimeHandshakeResponse get_s(SByteBuffer buf){
		SlimeHandshakeResponse f = new SlimeHandshakeResponse();
		f.get(buf);
		return f;
	}
}