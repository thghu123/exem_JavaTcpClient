import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    //포트번호는 수정하지 못하도록
    private static final int PORT = 50000;
    //private static final String ADDRESS = "localhost";

    public static void main(String[] args) throws IOException {
        //서버소켓 만들고, 클라이언트 소켓 객체 선언만
        ServerSocket serverSocket = null;
        Socket socket = null;

        //입출력 스트림 생성
        InputStream is = null;
        OutputStream os = null;


        try {
            //서버용 소켓 생성 후 bind로 서버 소켓 등록
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(5002));

            //서버 객체를 연결하여, 클라이언트 소켓에 담기.
            System.out.println("[TCP 클라이언트 요청 기다림]");
            socket = serverSocket.accept();

            //연결 위치 출력
            InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress(); //반환타입 : IP주소 + 포트번호
            System.out.println("[TCP 연결 수락] :" + isa.getHostName()); //isa에서 IP 얻어온다. socket.getInetAddress().getHostAddress()도 가능

            //송수신을 위한 INPUT/OUT 스트림
            is = socket.getInputStream();
            os = socket.getOutputStream();

            //분기를 통해 받아온 값을 초기화해 Packet에 저장해 오고, 이를 다시 에코하기 위한 객체 선언
            Protocol protocol = new Protocol();

            while (true) {
                //수신값 받아오기
                Packet recvPacket = protocol.receivePacket(is);

                //받아온 값이 없으면 while문을 나가 접속 종료
                if (recvPacket == null) break;

                //받아온 값 출력
                System.out.println(recvPacket);

                //받아온 값을 다시 전송
                protocol.sendPacket(os, recvPacket);
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


