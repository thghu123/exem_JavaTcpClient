import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    public static void main(String[] args) {
        InputStream is = null;
        ServerSocket serverSocket = null; //외부 선언하여 try catch에 활용

        try {

            serverSocket = new ServerSocket(); // 서버용 소켓
            serverSocket.bind(new InetSocketAddress("localhost", 5002));//TCP 소켓 연결 대기

                System.out.println( "[TCP 클라이언트 요청 기다림]");

                Socket socket = serverSocket.accept(); //연결

                InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress(); //반환타입 : IP주소 + 포트번호

                System.out.println("[TCP 연결 수락] " + isa.getHostName());

                //byte[] bytes = null;
                is = socket.getInputStream();
                //byte[] bytes = new byte[1024]; //잘려서 가져오게된다.
                UnPacker unPacker = new UnPacker();

            while(true) {

                //여기서 일단 Packer에 넘겨주어서 처리
                Packet recvPacket = unPacker.receive(is); //내부 Num Str Packet 분기 생성

                if (recvPacket == null) break;
                System.out.println(recvPacket); //toString으로 출력
                }

            } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally{
            try {
                if(is != null) is.close();

                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }



        }
}




