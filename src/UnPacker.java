import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UnPacker {

    public void send(OutputStream os,Packet packet) throws IOException {
        byte[] bytes = packet.toBytes();
        os.write(bytes);
        os.flush();
    }

    public void show (InputStream is) throws IOException {
        int packetType = (int) is.read(); //가장 처음값 가져오기.

        switch(packetType){
            case PacketType.INT2LONG4:
                //bytes
        }




    }


}
