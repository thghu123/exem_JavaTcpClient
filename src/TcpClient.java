/* 목적 : 데이터를 Byte로 변환하고, 프로토콜을 통해 메세지를 구분하는 예제*/

import java.io.IOException;

import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.net.Socket;
import java.util.Scanner;

public class TcpClient {

    public static void main(String[] args) {

        String str = "string"; //len = 6

        Scanner sc = null;
        Socket socket = null;

        try {
            
            socket = new Socket();

            System.out.println( "[TCP 통신 연결 요청]");

            socket.connect(new InetSocketAddress("localhost", 5002));

            System.out.println( "[TCP 연결 성공]");

            OutputStream os = socket.getOutputStream();

            UnPacker unPacker = new UnPacker();

            // ===4byte Int 2개, 8 byte Long 4개===
            while(true){

                PacketPacker msg = new PacketPacker();
                Packet packet;

                System.out.println("1: NumPacket, 2:StrPacket, 3: exit");
                sc = new Scanner(System.in);
                int input = sc.nextInt();

                if(input == 1){
                    packet = new NumPacker(1,2,3,4,5,6);

                }else if(input == 2){
                    packet = new StrPacker(str.length(),str);

                }else if(input == 3) break;
                else continue;

                //send 부 수정
                packet.send(os, packet);
                os.flush();
                System.out.println( "[데이터 전송 완료]");
            }

            os.close();

        } catch(Exception e) {}

        if(!socket.isClosed()) {

            try {

                socket.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }



}