package slime.base.proto.handlers;

import slime.base.proto.*;
import slime.base.proto.autogen.*;
import slime.base.proto.handlers.BaseHandler;
import slime.base.proto.handlers.BaseHandlerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class HandshakeInitHandlerFactory extends BaseHandlerFactory {
    @Override
    public UUID getUUID() { return SlimeHandshakeInit.COMMAND_UUID; }

    @Override
    public BaseHandler create(ClientSessionAttorney attorney){ return new HandshakeInitHandler(attorney); }
    
    public class HandshakeInitHandler extends BaseHandler {


        public HandshakeInitHandler(ClientSessionAttorney a){
            super(a);
        }

        @Override
        public UUID getCommandUUID(){
            return SlimeHandshakeInit.COMMAND_UUID;
        }

        @Override
        public void acceptPacket(SByteBuffer base){
            for(SByteReadingMode m : SByteReadingMode.allPossibleModes()) {
                base.mark();
                base.setReadingMode(m);
                SlimeHandshakeInit data = SlimeHandshakeInit.get_s(base);
                if(data.magic_uuid.equals(Consts.SLIME_HANDSHAKE_INIT_UUID)
                        && (data.magic_int == Consts.SLIME_HANDSHAKE_INIT_INT)
                        && (data.magic_short == Consts.SLIME_HANDSHAKE_INIT_SHORT)
                        && (data.magic_byte == Consts.SLIME_HANDSHAKE_INIT_BYTE)
                ){
                    setDetectedReadingMode(m);
                    return;
                }

                base.reset();
            }

            System.out.println("### Failed to detect reading mode!!!!");
            assert false; //temporary
        }

        @Override
        public void tick(){

        }


        private void setDetectedReadingMode(SByteReadingMode to){
            System.out.println("Reading mode: " + to.toString());
            attorney().setReadingMode(to);
        }
    }
}