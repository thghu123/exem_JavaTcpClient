import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    private static final int PORT = 5002;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;

        InputStream is = null;
        OutputStream os = null;


        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(PORT));

            System.out.println("[TCP 클라이언트 요청 기다림]");
            socket = serverSocket.accept();

            InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress(); //반환타입 : IP주소 + 포트번호
            System.out.println("[TCP 연결 수락] :" + isa.getHostName()); //isa에서 IP 얻어온다. socket.getInetAddress().getHostAddress()도 가능

            is = socket.getInputStream();
            os = socket.getOutputStream();

            Protocol protocol = new Protocol();

            while (true) {
                Packet recvPacket = protocol.receivePacket(is);
                if (recvPacket == null) break;
                System.out.println(recvPacket);
                recvPacket.sendPacket(os);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (is != null) is.close();
            if (os != null) os.close();

            try{
                socket.close();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
    }
}


