/*import java.io.IOException;
import java.io.OutputStream;*/
import java.nio.ByteBuffer;

public class StrPacker implements Packet{

    private byte packetType;
    private long length;
    private String str;

    public StrPacker(long length, String str){
        //this.packetType = PacketType.LONG1STRING;
        this.packetType = 1;
        this.length = length;
        this.str = str;
    } // 멤버 값만 입력


    @Override
    public byte[] toBytes() {
        //요청시 STR을 바이트 버퍼를 생성시 입력된 크기에 맞게 할당하여 Byte로 변환한다.
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)(length+8+1)); //(int)(length)+8+1과의 차이???
        byteBuffer.put(packetType); //1바이트 타입값 입력
        byteBuffer.putLong(length); //스트링 길이값 입력
        byteBuffer.put(str.getBytes()); //문자열 입력
        return byteBuffer.array(); //배열을 반환받을 수 있다. 기본 데이터형의 배열을 리턴
    }

    @Override
    public String toString() {
        return
                "protocol Type: " + packetType +
                ", length:" + length +
                ", str:" + str +
                '\n';
    }
}
    /*
    @Override
    public void send(OutputStream os, Packet packet) throws IOException {
        byte[] bytes = packet.toBytes();
        os.write(bytes);

    }*/
