import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;


public class UnPacker {

    public Packet receive (InputStream is) throws IOException {
        int packetType = is.read(); //가장 처음값 가져오기.
        Packet packet = null;

        switch(packetType){
            case PacketType.INT2LONG4:
                byte[] bytes = new byte[40];
                is.read(bytes);
                ByteBuffer bufferN = ByteBuffer.wrap(bytes);

                if(is.read(bytes)==-1) break;

                int iVal1 = bufferN.getInt();
                int iVal2 = bufferN.getInt();
                long lVal1 = bufferN.getLong();
                long lVal2 = bufferN.getLong();
                long lVal3 = bufferN.getLong();
                long lVal4 = bufferN.getLong();

                packet = new NumPacker(iVal1,iVal2,lVal1,lVal2,lVal3,lVal4);
                break;

            case PacketType.LONG1STRING:
                byte[] lengthRecv = new byte[8]; //추후에 length 추가할 것.
                is.read(lengthRecv, 0,8);
                ByteBuffer bufferL = ByteBuffer.wrap(lengthRecv);
                long len = bufferL.getLong();

                byte[] strRecv = new byte[(int)len];
                if (is.read(strRecv) == -1) break;

                packet = new StrPacker(len, new String(strRecv));

                break;
            default:
                break;
        }

        return packet;
    }


}
