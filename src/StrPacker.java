/*import java.io.IOException;
import java.io.OutputStream;*/
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class StrPacker implements Packet{

    private byte packetType;
    private int totalLen;

    private long length;
    private String str;

    public StrPacker(long length, String str){
        this.packetType = PacketType.LONG1STRING;
        totalLen = str.getBytes().length+1+4+8;
        this.length = length;
        this.str = str;
    } // 멤버 값만 입력

    public StrPacker(){
        this.packetType = PacketType.LONG1STRING;
        totalLen = 0;
        this.length = 0;
        this.str = "";
    } // 멤버 값만 입력

    @Override
    public byte[] toBytes() {

        //요청시 STR을 바이트 버퍼를 생성시 입력된 크기에 맞게 할당하여 Byte로 변환한다.
        //ByteBuffer byteBuffer = ByteBuffer.allocate((int)(length+8+1)); //(int)(length)+8+1과의 차이???
        ByteBuffer byteBuffer = ByteBuffer.allocate(totalLen);
        byteBuffer.put(packetType); //1바이트 타입값 입력
        byteBuffer.putInt(totalLen); // 4바이트 입력

        byteBuffer.putLong(length); //스트링 길이값 입력
        byteBuffer.put(str.getBytes()); //문자열 입력
        return byteBuffer.array(); //배열을 반환받을 수 있다. 기본 데이터형의 배열을 리턴
    }

    @Override
    public String toString() {
        return
                "protocol Type: " + packetType +
                "total Length: " + this.totalLen +
                ", length:" + length +
                ", str:" + str +
                '\n';
    }

    public void sendPacket(OutputStream os) throws IOException { //여기에 this.toByte()넣는다.

        //packet을 받고 그안의 tobyte로 변환하여, os로 send, write 하자
        byte[] bytes = this.toBytes();//this.toBytes();
        os.write(bytes);
    }
    public void setPacket(InputStream is) throws IOException {
        byte[] totalLength = new byte[4];
        while (is.read(totalLength) == -1) break; //읽어오기
        this.totalLen = ByteBuffer.wrap(totalLength).getInt();

        byte[] len = new byte[8];
        while (is.read(len) == -1) break; //읽어오기
        long length = ByteBuffer.wrap(len).getLong(0);


        byte[] str = new byte[(int)length];
        while (is.read(str) == -1) break; //읽어오기

        this.str = new String(str);
        this.length = length;
/*

        바이트 버퍼 혹은 바이트로만 읽어올 수 있는 데 추가 공간 할당이 필요하다.
        한번에 total len으로 받아와서  진행하는 방법


*/






/*        byte[] lengthBytes = new byte[8];
        // 덮어 씌울 값, 시작값, 받아올 길이 지정해서 read해오기
        is.read(lengthBytes, 0, 8);
        long length = ByteBuffer.wrap(lengthBytes).getLong(0);

        //길이만큼 스트링을 read로 받아온다. 먼저 바이트 할당
        byte[] strBytes = new byte[(int) length];
        while (is.read(strBytes) == -1) break;

        this.length = ByteBuffer.wrap(lengthBytes).getLong(0);

        byte[] str = ByteBuffer.wrap(lengthBytes).get();

        this.str = new String(str);*/


        // 값이 없을 경우 나가는 코드.
        // 추가로 값을 받아옴, 돌면서 앞의 공백값은 모두 제거하고, 값이 있는 값을 찾으면 길이만큼 덮어씀


    }


/*    public Packet recvPacket(IntputStream is){
        Packet packet = null;


        return packet;}*/


}
