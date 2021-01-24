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

public class TcpClient_ver1  {

    private static final String ADDRESS = "localhost";
    private static final int PORT = 5002;

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(ADDRESS, PORT);

        Scanner sc = null;
        OutputStream os = null;
        InputStream is = null;

        try {
            socket.connect(socketAddress);

            is = socket.getInputStream();
            os = socket.getOutputStream();

            Protocol protocol = new Protocol();

            while (true) {
                Packet sendPacket;

                //여기서 부터 마구잡이로 보낸다.

                System.out.println("1: NumPacket, 2:StrPacket, 3: exit");
                sc = new Scanner(System.in);
                int input = sc.nextInt();

                if (input == 1) {
                    sendPacket = new NumPacker(1, 2, 333, 444, 555, 666);
                } else if (input == 2) {
                    sendPacket = new StrPacker(3, "abc");
                } else if (input == 3) break;
                else continue;

                System.out.println("발신 패킷: " + sendPacket.toString());
                sendPacket.sendPacket(os);

                Packet recvPacket = protocol.receivePacket(is);
                System.out.println("수신 패킷: " + recvPacket);

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try{
                if (sc != null ) sc.close();
                if (os != null) os.close();
                if (is != null) is.close();
                if(socket!=null)socket.close();
            }catch(IOException ioe) {ioe.printStackTrace();}

        }
    }
}
