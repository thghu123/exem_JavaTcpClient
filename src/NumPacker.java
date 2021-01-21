import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class NumPacker implements Packet {

    private byte    packetType;
    private int     iVal1,
                    iVal2;
    private long    lVal1,
                    lVal2,
                    lVal3,
                    lVal4;

    //생성자 추가
    public NumPacker(int iVal1,int iVal2, long lVal1, long lVal2, long lVal3,long lVal4){
        this.packetType = PacketType.INT2LONG4;
        this.iVal1 = iVal1;
        this.iVal2 = iVal2;
        this.lVal1 = lVal1;
        this.lVal2 = lVal2;
        this.lVal3 = lVal3;
        this.lVal4 = lVal4;
    } // 멤버 값만 입력

    @Override
    public byte[] toBytes() {
        //요청할 경우에 길이에 맞게 할당을 진행하고, 값을 입력한다.
        byte[] data = {};
        ByteBuffer buffer = ByteBuffer.allocate(1+40);

        buffer.put(packetType);
        buffer.putInt(iVal1);
        buffer.putInt(iVal2);
        buffer.putLong(lVal1);
        buffer.putLong(lVal2);
        buffer.putLong(lVal3);
        buffer.putLong(lVal4);

        data = buffer.array();
        buffer.flip();
        return data;
    }

    @Override
    public void send(OutputStream os, Packet packet) throws IOException {
        byte[] bytes = packet.toBytes();
        os.write(bytes);
        os.flush();
    }

    @Override
    public String toString() {
        return
                "protocol Type: " + packetType +
                ", iVal1:" + iVal1 +
                ", iVal2:" + iVal2 +
                ", lVal1:" + lVal1 +
                ", lVal2:" + lVal2 +
                ", lVal3:" + lVal3 +
                ", lVal4:" + lVal4 +
                '}';
    }


}
