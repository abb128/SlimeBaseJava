// This file is automatically generated, please do not touch

package slime.base.proto.autogen;


import slime.base.proto.SByteBuffer;
import slime.base.proto.types.*;
import slime.base.proto.autogen.*;
import slime.base.proto.Util;
import java.util.UUID;

/**  */
public class SlimeResetRequest implements CommandData {
	public static final UUID COMMAND_UUID = UUID.fromString("0000000a-445e-4484-bc3d-f0c696a3080c");
	public static final long FLAGS = 2;

	/**  */
	public long type;

	@Override
	public UUID getCommandUUID(){
		return SlimeResetRequest.COMMAND_UUID;
	}

	@Override
	public void get(SByteBuffer buf){
		type = buf.getU32();
	}

	@Override
	public void put(SByteBuffer buf){
		buf.putU32(type);
	}

	public static SlimeResetRequest get_s(SByteBuffer buf){
		SlimeResetRequest f = new SlimeResetRequest();
		f.get(buf);
		return f;
	}
}