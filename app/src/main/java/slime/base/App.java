package slime.base;

import slime.base.UdpServer;
import slime.base.proto.ClientSession;
import slime.base.proto.HandlerMapper;

import java.nio.ByteBuffer;
import java.net.*;
import java.io.*;

public class App {
    public static void main(String[] args){
        HandlerMapper.init();

        UdpServer udp = new UdpServer();
        if(!udp.startListening(6970)) return;
        
        int MAX_PACKET_SIZE = 256;
        byte[] buff = new byte[MAX_PACKET_SIZE];
        DatagramPacket req = new DatagramPacket(buff, MAX_PACKET_SIZE);
        
        ClientSession sess = new ClientSession();
        SocketAddress client_addr = null;
        for(;;){
            // Receive
            if(udp.receive(req)){
                sess.acceptPacket(req.getData(), req.getLength());
                client_addr = req.getSocketAddress();
            }

            // Tick
            sess.tick();

            // Send
            if(client_addr != null){
                byte[] bytes = null;
                while((bytes = sess.popOutgoingOrNull()) != null){
                    DatagramPacket out = new DatagramPacket(bytes, 0, bytes.length, client_addr);
                    udp.send(out);
                }
            }
        }
    }
}