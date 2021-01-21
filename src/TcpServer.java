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
                byte[] bytes = new byte[1024]; //잘려서 가져오게된다.
                UnPacker unPacker = new UnPacker();

            while(true) {

                //여기서 일단 Packer에 넘겨주어서 처리
                unPacker.show(is);

                int readByteCount = is.read(bytes); //bytes에 읽어온 값 입력, int형인 이유는 -1 반환을 위함
                //성공 시 읽은 바이트 수 반환, 오류시 -1 반환

                PacketPacker msg = new PacketPacker(bytes); //할당 후 데이터 값 입력
            
                System.out.println("[읽어온 바이트 수: ]"+readByteCount);

                byte protocol = msg.getPacketType();

                //System.out.println(protocol);
                if(protocol == PacketType.NOTHING){
                    System.out.println("프로토콜 종료");
                    break;
                }

                switch(protocol){
                    case PacketType.INT2LONG4:{
                        System.out.println("PACKET TYPE : INT2LONG4");
                        msg.printINT2LONG4();

                        break;

                    }
                    case PacketType.LONG1STRING:{
                        System.out.println("PACKET TYPE : LONG1STRING");
                        System.out.println(msg.getString());

                        break;
                    }

                }
            }
                is.close();

                socket.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        if(!serverSocket.isClosed()) {

            try {

                serverSocket.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }



}

