package slime.base;

import java.net.*;
import java.io.*;

public class UdpServer {
    private DatagramSocket ss;
    public UdpServer(){}
    
    
    public boolean startListening(int port){
        try {
            ss = new DatagramSocket(port);
            ss.setSoTimeout(100);
            System.out.println("Now listening on " + String.valueOf(port));
            return true;
        } catch(Exception e){
            System.out.println("Failed to listen on port " + String.valueOf(port));
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean receive(DatagramPacket to){
        try {
            ss.receive(to);
            return true;
        } catch(IOException e){
            //e.printStackTrace(); // socket timeout exception
            return false;
        }
    }

    public boolean send(DatagramPacket pkt){
        try {
            ss.send(pkt);
            return true;
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
