import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Packet {

    public byte[] toBytes();
    //write로 값을 보내주도록 byte로 변환해주는 추상클래스
    public void sendPacket(OutputStream os) throws IOException;
    public void setPacket(InputStream is) throws IOException;

    //void sendPacket(OutputStream os);

    // void sendPacket(OutputStream os);
    //public Packet recvPacket(InputStream is);
    //public void send(OutputStream os, Packet packet) throws IOException;

}
