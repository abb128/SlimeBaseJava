package slime.base.proto.handlers;

import slime.base.proto.ClientSessionAttorney;
import slime.base.proto.SByteBuffer;
import slime.base.proto.autogen.SlimeHandshake;
import slime.base.proto.autogen.SlimeHandshakeResponse;

import java.util.UUID;

public class HandshakeHandlerFactory extends BaseHandlerFactory {
    @Override
    public UUID getUUID() { return SlimeHandshake.COMMAND_UUID; }

    @Override
    public BaseHandler create(ClientSessionAttorney attorney){ return new HandshakeHandler(attorney); }

    public class HandshakeHandler extends BaseHandler {
        public HandshakeHandler(ClientSessionAttorney a){
            super(a);
        }

        @Override
        public UUID getCommandUUID(){
            return SlimeHandshake.COMMAND_UUID;
        }

        @Override
        public void acceptPacket(SByteBuffer buff){
            SlimeHandshake handshake = SlimeHandshake.get_s(buff);
            assert handshake.protocol_version == 1;

            System.out.println("Handshake received");
            System.out.println("\tversion: " + String.valueOf(handshake.protocol_version));
            System.out.println("\tfirmware: " + handshake.firmware.toString());
            System.out.println("\thardware: " + handshake.hardware.toString());
            System.out.println("\thw_uid: " + handshake.hw_uid.toString());
            System.out.println("\treliability: " + String.valueOf(handshake.reliability));
            sendResponse();
        }

        @Override
        public void tick(){

        }

        private void sendResponse(){
            SlimeHandshakeResponse response = new SlimeHandshakeResponse();
            attorney().sendPacket(response);
        }
    }
}