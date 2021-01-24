import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class NumPacker implements Packet {

    private byte    packetType;
    private int     totalLen;

    private int     iVal1,
                    iVal2;
    private long    lVal1,
                    lVal2,
                    lVal3,
                    lVal4;

    public NumPacker(){
        this.packetType = PacketType.INT2LONG4;
        this.totalLen = 45;
        this.iVal1 = 0;
        this.iVal2 = 0;
        this.lVal1 = 0;
        this.lVal2 = 0;
        this.lVal3 = 0;
        this.lVal4 = 0;
    }

    public NumPacker(int iVal1,int iVal2, long lVal1, long lVal2, long lVal3,long lVal4){
        this.packetType = PacketType.INT2LONG4;
        this.totalLen = 45;
        this.iVal1 = iVal1;
        this.iVal2 = iVal2;
        this.lVal1 = lVal1;
        this.lVal2 = lVal2;
        this.lVal3 = lVal3;
        this.lVal4 = lVal4;
    }

    @Override
    public byte[] toBytes() {
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

    public void sendPacket(OutputStream os) throws IOException {
        byte[] bytes = this.toBytes();
        os.write(bytes);
    }

    public void setPacket(InputStream is) throws IOException{

        byte[] bytes = new byte[44];
        while (is.read(bytes) == -1) break;
         totalLen = ByteBuffer.wrap(bytes).getInt(0);
         iVal1 = ByteBuffer.wrap(bytes).getInt(4);
         iVal2 = ByteBuffer.wrap(bytes).getInt(8);
         lVal1 = ByteBuffer.wrap(bytes).getLong(12);
         lVal2 = ByteBuffer.wrap(bytes).getLong(20);
         lVal3 = ByteBuffer.wrap(bytes).getLong(28);
         lVal4 = ByteBuffer.wrap(bytes).getLong(36);

    }

}