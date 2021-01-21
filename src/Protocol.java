import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Protocol {

    public Packet receivePacket(InputStream is) throws IOException { //패킷 받는 메서드. 받는 기능만 있을 것
        Packet packet = null; //다형성으로 오버라이드된 객체 사용 가능
        int packetType = is.read(); //예외처리

        switch(packetType) {
            case PacketType.INT2LONG4:
                byte[] bytes = new byte[40];
                while (is.read(bytes) == -1) break; //이게 if문과 같은 건지, while을 탈출하면 뒤가 진행되는지

                //값 멤버변수에 넣어주기. 40중 int형의 데이터를 0의 메모리 주소부터 크기만큼 bytes에 덮어씀.
                int iVal1 = ByteBuffer.wrap(bytes).getInt(0);
                int iVal2 = ByteBuffer.wrap(bytes).getInt(4);
                long lVal1 = ByteBuffer.wrap(bytes).getLong(8);
                long lVal2 = ByteBuffer.wrap(bytes).getLong(16);
                long lVal3 = ByteBuffer.wrap(bytes).getLong(24);
                long lVal4 = ByteBuffer.wrap(bytes).getLong(32);

                //넘버 패킷 생성자로 초기화하고 리턴
                packet = new NumPacker(iVal1, iVal2, lVal1, lVal2, lVal3, lVal4);
                break;

            case PacketType.LONG1STRING:
                // long먼저 할당 받은 뒤 len 받고 그 길이만큼 할당, 한번에 읽어오는 법?
                byte[] lengthBytes = new byte[8];
                // 덮어 씌울 값, 시작값, 받아올 길이 지정해서 read해오기
                is.read(lengthBytes, 0, 8);
                long length = ByteBuffer.wrap(lengthBytes).getLong(0);

                //길이만큼 스트링을 read로 받아온다. 먼저 바이트 할당
                byte[] strBytes = new byte[(int) length];

                // 값이 없을 경우 나가는 코드.
                // 추가로 값을 받아옴, 돌면서 앞의 공백값은 모두 제거하고, 값이 있는 값을 찾으면 길이만큼 덮어씀
                while (is.read(strBytes) == -1) break;

                //값을 넣어 StrPacket으로 초기화하고 패킷 넘겨줌
                packet = new StrPacker(length, new String(strBytes));

                //바이트 형을 String으로 변환 시 toString의 경우 non-primiritive 타입이 가지는 필드, 메서드 없어
                //byte 안의 요소를 다루는 것이 아닌 배열 자체를 취급합니다. 범위 초과 유도, 문자열 변환에는 new String()을 사용

                break;

            default:
                break;
        }

        //패킷 반환
         return packet;

    }

        public void sendPacket(OutputStream os, Packet packet) throws IOException {
        //packet을 받고 그안의 tobyte로 변환하여, os로 send, write 하자
            byte[] bytes = packet.toBytes();
            os.write(bytes);

        }

    }


