package slime.base.proto;

import slime.base.proto.types.CommandData;
import slime.base.proto.types.Handle;
import slime.base.proto.handlers.BaseHandler;
import slime.base.proto.HandlerMapper;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

import slime.base.proto.autogen.*;

public class ClientSession {
    private Queue<byte[]> outgoing;
    private ClientCommandMapper mapper;
    private SByteReadingMode readingMode;

    public ClientSession(){
        this.outgoing = new LinkedList<>();
        this.mapper = new ClientCommandMapper(this);
    }

    public byte[] popOutgoingOrNull(){
        return outgoing.poll();
    }

    private int packet_number = 0;
    public void sendPacket(CommandData data){
        UUID uid = data.getCommandUUID();
        Handle h = mapper.getHandleFromUUID(uid);
        if(h == null){
            System.out.println("Failed to send command because it is not registered: " + data.toString());
            System.out.println("\tUUID: " + uid.toString());
            return;
        }

        System.out.println("Sending command: " + data.toString());
        // TODO: Define max length
        byte[] bytes = new byte[128];
        SByteBuffer buff = new SByteBuffer(bytes, 0, 128, readingMode);

        buff.putHandle(h);
        System.out.println("\thandle: " + h.toString());
        if(mapper.hasPacketNumber(h)){
            packet_number += 1;
            if(packet_number >= 65536) packet_number = 0;
            buff.putU16(packet_number);
            System.out.println("\tpacket_number: " + String.valueOf(packet_number));
        }

        System.out.println("\tput");
        data.put(buff);

        outgoing.add(bytes);
    }

    public void tick(){

    }

    public void acceptPacket(byte[] data, int length){
        SByteBuffer buf = new SByteBuffer(data, 0, length, readingMode);

        Handle handle = buf.getHandle();

        if(!mapper.handleCommand(handle, buf)){
            System.out.println("Received unknown packet type: " + handle.toString());
        }
    }

    public void setReadingMode(SByteReadingMode to){
        readingMode = to;
    }

    public class ClientCommandMapper {
        private Map<Handle, BaseHandler> handlers;
        private Map<Handle, UUID> h2uid;
        private Map<UUID, Handle> uid2h;
        private Map<Handle, Boolean> has_packetno;

        private ClientSession sess;

        ClientCommandMapper(ClientSession s){
            this.handlers = new HashMap<Handle, BaseHandler>();
            this.has_packetno = new HashMap<Handle, Boolean>();
            this.h2uid = new HashMap<Handle, UUID>();
            this.uid2h = new HashMap<UUID, Handle>();
            this.sess = s;

            insertDefaults();
        }

        public Handle getHandleFromUUID(UUID uid){
            return uid2h.get(uid);
        }

        public boolean hasPacketNumber(Handle h){
            Boolean v = has_packetno.get(h);
            return (v != null) && v.booleanValue();
        }

        public boolean handleCommand(Handle h, SByteBuffer buf) {
            int packet_number = 0;
            Boolean hasno = this.has_packetno.get(h);
            if(hasno != null && hasno == true){
                packet_number = buf.getU16();
            }

            BaseHandler handler = this.handlers.get(h);
            if(handler != null){
                handler.acceptPacket(buf);
                return true;
            }

            return false;
        }

        public void declareHandle(Handle handle, UUID uid){
            this.h2uid.put(handle, uid);
            this.uid2h.put(uid, handle);

        }

        public boolean describeCommand(Handle h, UUID uid, int flags){
            boolean handlerRequired = (flags & Consts.SLIME_COMMAND_CLIENT_SENDS) != 0;
            boolean serverTypeRequired = (flags & Consts.SLIME_COMMAND_SERVER_SENDS) != 0;

            assert handlerRequired || serverTypeRequired;

            boolean valid = false;

            if(handlerRequired) {
                BaseHandler handler = HandlerMapper.createHandlerOrNull(uid, new ClientSessionAttorney(this.sess));
                valid = handler != null;
                if(!valid) return false;

                this.handlers.put(h, handler);
            }
            if(serverTypeRequired){
                // TODO
                valid = true;
            }

            if(!valid) return false;

            declareHandle(h, uid);

            // todo this should check Reliability bit from handshake
            this.has_packetno.put(h, (flags & (4 | 8)) != 0);
            return true;
        }


        private void insertDefaults(){
            boolean v = true;
            // TODO: autogen this hopefully
            v = v&&describeCommand(Handle.fromByte((byte)Consts.SLIME_HANDLE_HANDSHAKE_INIT), SlimeHandshakeInit.COMMAND_UUID, Consts.SLIME_COMMAND_CLIENT_SENDS);
            v = v&&describeCommand(Handle.fromByte((byte)Consts.SLIME_HANDLE_HANDSHAKE), SlimeHandshake.COMMAND_UUID, Consts.SLIME_COMMAND_CLIENT_SENDS);
            v = v&&describeCommand(Handle.fromByte((byte)Consts.SLIME_HANDLE_HANDSHAKE_R), SlimeHandshakeResponse.COMMAND_UUID, Consts.SLIME_COMMAND_SERVER_SENDS);
            v = v&&describeCommand(Handle.fromByte((byte)Consts.SLIME_HANDLE_HANDSHAKE_INIT_R), SlimeHandshakeInitResponse.COMMAND_UUID, Consts.SLIME_COMMAND_SERVER_SENDS);

            if(!v){
                System.out.println("Failed to describe commands! This shouldn't happen");
                assert false;
            }
        }
    }
}