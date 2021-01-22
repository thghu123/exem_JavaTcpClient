/* 목적 : 데이터를 Byte로 변환하고, 프로토콜을 통해 메세지를 구분하는 예제*/

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class TcpClient {

    //은닉, 유지보수
    private static final String ADDRESS = "localhost";
    private static final int PORT = 5002;

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(ADDRESS, PORT);

        Scanner sc = null;
        OutputStream os = null;
        InputStream is = null;
        //catch문에서 조건문으로 이용하기 위함

        try {
            //socket 연결, 예외처리
            socket.connect(socketAddress);
            System.out.println("[TCP 통신 연결 요청]");

            //is와 os 받아오기, 송수신 에코를 위함
            is = socket.getInputStream();
            os = socket.getOutputStream();

            System.out.println("[TCP 연결 성공]");

            //Packet별로 분기하여 값에 입력하고, 초기화하기 위한 Protocol 선언
            Protocol protocol = new Protocol();

            while (true) {
                //값을 넣고 전달할 Packet 생성
                Packet sendPacket;

                System.out.println("1: NumPacket, 2:StrPacket, 3: exit");
                sc = new Scanner(System.in);
                int input = sc.nextInt();

                //분기로 packet 다형성으로 초기화
                if (input == 1) {
                    sendPacket = new NumPacker(1, 2, 333, 444, 555, 666);
                } else if (input == 2) {
                    sendPacket = new StrPacker(3, "abc");
                } else if (input == 3) break;
                else continue;

                //읽어온 패킷 화면에 보여주고 다시 보낸다
                System.out.println("발신 패킷: " + sendPacket.toString());//왜 toString()??
                sendPacket.sendPacket(os);

                //protocol안의 send를 이용

                //에코해서 돌아온 값을 다시 받아 띄워준다
                Packet recvPacket = protocol.receivePacket(is);
                System.out.println("수신 패킷: " + recvPacket);

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            //Scanner 닫아준다
            if (sc != null ) sc.close();
            if (os != null) os.close();
            if (is != null) is.close();
            //널값인지 확인하고 os와 io도 닫아준다
            try{
                socket.close();
            }catch(IOException ioe) {ioe.printStackTrace();}



        }

    }
}