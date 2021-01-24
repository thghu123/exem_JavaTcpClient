import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class TcpClientJob implements Job {

    private static final String ADDRESS = "localhost";
    private static final int PORT = 5002;




    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        OutputStream os = null;
        InputStream is = null;
        SocketAddress socketAddress = new InetSocketAddress(ADDRESS, PORT);
        Socket socket = new Socket();

        try {
            socket.connect(socketAddress);
            os = socket.getOutputStream();
            is = socket.getInputStream();
            Protocol protocol = new Protocol();

            Packet sendPacket = null;

            int randNum = (int)(Math.random()*2)+1;
            System.out.println("Random Packet Send");

            if (randNum == 1) {
                sendPacket = new NumPacker(1, 2, 333, 444, 555, 666);
            } else if (randNum == 2) {
                sendPacket = new StrPacker(3, "abc");
            } else{
                sendPacket = new StrPacker(3, "abc");
            }

            System.out.println("발신 패킷: " + sendPacket.toString());
            sendPacket.sendPacket(os);

            Packet recvPacket = protocol.receivePacket(is);
            System.out.println("수신 패킷: " + recvPacket);


        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try{
                if (os != null) os.close();
                if (is != null) is.close();
                if(socket!=null)socket.close();
            }catch(IOException ioe) {ioe.printStackTrace();}

        }
    }



}

