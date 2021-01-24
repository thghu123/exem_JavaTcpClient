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
        this.totalLen = str.getBytes().length+1+4+8;
        this.length = length;
        this.str = str;
    }

    public StrPacker(){
        this.packetType = PacketType.LONG1STRING;
        this.totalLen = 0;
        this.length = 0;
        this.str = "";
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(totalLen);
        byteBuffer.put(packetType);
        byteBuffer.putInt(totalLen);
        byteBuffer.putLong(length);
        byteBuffer.put(str.getBytes());
        return byteBuffer.array();
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

    public void sendPacket(OutputStream os) throws IOException {
        byte[] bytes = this.toBytes();
        os.write(bytes);
    }

    public void setPacket(InputStream is) throws IOException {
        byte[] totalLength = new byte[4];
        while (is.read(totalLength) == -1) break;
        this.totalLen = ByteBuffer.wrap(totalLength).getInt();

        byte[] len = new byte[8];
        while (is.read(len) == -1) break;
        long length = ByteBuffer.wrap(len).getLong(0);


        byte[] str = new byte[(int)length];
        while (is.read(str) == -1) break;

        this.str = new String(str);
        this.length = length;

    }

}
