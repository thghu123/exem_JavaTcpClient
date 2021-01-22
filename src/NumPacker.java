/*import java.io.IOException;
import java.io.OutputStream;*/
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class NumPacker implements Packet {

    private byte    packetType;
    private int     totalLen; //길이 및 형변환 이슈

    private int     iVal1,
                    iVal2;
    private long    lVal1,
                    lVal2,
                    lVal3,
                    lVal4;

    //생성자 추가
    public NumPacker(){
        this.packetType = PacketType.INT2LONG4;
        this.totalLen = 45;
        this.iVal1 = 0;
        this.iVal2 = 0;
        this.lVal1 = 0;
        this.lVal2 = 0;
        this.lVal3 = 0;
        this.lVal4 = 0;
    } // 멤버 값만 입력

    public NumPacker(int iVal1,int iVal2, long lVal1, long lVal2, long lVal3,long lVal4){
        this.packetType = PacketType.INT2LONG4;
        this.totalLen = 45;
        this.iVal1 = iVal1;
        this.iVal2 = iVal2;
        this.lVal1 = lVal1;
        this.lVal2 = lVal2;
        this.lVal3 = lVal3;
        this.lVal4 = lVal4;
    } // 멤버 값만 입력

    @Override
    public byte[] toBytes() {
        //요청할 경우에 길이에 맞게 할당 후 타입과 값을 입력
        ByteBuffer buffer = ByteBuffer.allocate(1+4+40);

        buffer.put(packetType);
        buffer.putInt(totalLen);

        buffer.putInt(iVal1);
        buffer.putInt(iVal2);
        buffer.putLong(lVal1);
        buffer.putLong(lVal2);
        buffer.putLong(lVal3);
        buffer.putLong(lVal4);

        return buffer.array();
    }

    @Override
    public String toString() {
        return
                "protocol Type: " + packetType +
                ", total Length: " + totalLen +
                ", iVal1:" + iVal1 +
                ", iVal2:" + iVal2 +
                ", lVal1:" + lVal1 +
                ", lVal2:" + lVal2 +
                ", lVal3:" + lVal3 +
                ", lVal4:" + lVal4 +
                '\n';
    }

    //Protocol -> Packer, 기존 패킷 값 유지
    public void sendPacket(OutputStream os) throws IOException {
        //packet을 받고 그안의 tobyte로 변환하여, os로 send, write 하자
        byte[] bytes = this.toBytes();
        os.write(bytes);
    }

    public void setPacket(InputStream is) throws IOException{ //널값이 없을 경우 처리할 것

        byte[] bytes = new byte[44];
        while (is.read(bytes) == -1) break; //이게 if문과 같은 건지, while을 탈출하면 뒤가 진행되는지
         totalLen = ByteBuffer.wrap(bytes).getInt(0);
        //값 멤버변수에 넣어주기. 40중 int형의 데이터를 0의 메모리 주소부터 크기만큼 bytes에 덮어씀.
         iVal1 = ByteBuffer.wrap(bytes).getInt(4);
         iVal2 = ByteBuffer.wrap(bytes).getInt(8);
         lVal1 = ByteBuffer.wrap(bytes).getLong(12);
         lVal2 = ByteBuffer.wrap(bytes).getLong(20);
         lVal3 = ByteBuffer.wrap(bytes).getLong(28);
         lVal4 = ByteBuffer.wrap(bytes).getLong(36);
        //값만 전부 입력해둠

    }




}

/*    @Override
    public void send(OutputStream os, Packet packet) throws IOException {
        byte[] bytes = packet.toBytes();
        os.write(bytes);
        os.flush();
    }*/